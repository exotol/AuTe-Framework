package ru.bsc.test.at.executor.step.executor;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.bsc.test.at.executor.ei.mqmocker.MqMockerAdmin;
import ru.bsc.test.at.executor.ei.mqmocker.model.MqMockDefinition;
import ru.bsc.test.at.executor.ei.wiremock.WireMockAdmin;
import ru.bsc.test.at.executor.ei.wiremock.model.MockDefinition;
import ru.bsc.test.at.executor.ei.wiremock.model.MockRequest;
import ru.bsc.test.at.executor.ei.wiremock.model.RequestList;
import ru.bsc.test.at.executor.ei.wiremock.model.WireMockRequest;
import ru.bsc.test.at.executor.helper.NamedParameterStatement;
import ru.bsc.test.at.executor.helper.ResponseHelper;
import ru.bsc.test.at.executor.model.MockServiceResponse;
import ru.bsc.test.at.executor.model.MqMockResponse;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.SqlData;
import ru.bsc.test.at.executor.model.SqlResultType;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.mq.IMqManager;
import ru.bsc.test.at.executor.mq.MqManagerFactory;
import ru.bsc.test.at.executor.validation.IgnoringComparator;
import ru.bsc.test.at.executor.validation.MaskComparator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
public abstract class AbstractStepExecutor implements IStepExecutor {

    private final static int POLLING_RETRY_TIMEOUT_MS = 1000;

    void sendMessageToQuery(Project project, Step step, Map<String, Object> scenarioVariables) throws Exception {
        if (step.getMqName() != null && step.getMqMessage() != null) {
            if (project.getAmqpBroker() == null) {
                throw new Exception("AMQP broker is not configured in Project settings.");
            }
            try(IMqManager mqManager = MqManagerFactory.getMqManager(project.getAmqpBroker().getMqService())) {

                mqManager.setHost(project.getAmqpBroker().getHost());
                mqManager.setPort(project.getAmqpBroker().getPort());
                mqManager.setUsername(project.getAmqpBroker().getUsername());
                mqManager.setPassword(project.getAmqpBroker().getPassword());

                mqManager.connect();

                String message = insertSavedValues(step.getMqMessage(), scenarioVariables);
                mqManager.sendTextMessage(step.getMqName(), message);
            }
        }
    }

    void parseMockRequests(Project project, Step step, WireMockAdmin wireMockAdmin, Map<String, Object> scenarioVariables, String testId) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        if (step.getParseMockRequestUrl() != null) {
            MockRequest mockRequest = new MockRequest();
            mockRequest.getHeaders().put(project.getTestIdHeaderName(), new HashMap<String, String>() {{
                put("equalTo", testId);
            }});
            mockRequest.setUrl(step.getParseMockRequestUrl());
            RequestList list = wireMockAdmin.findRequests(mockRequest);
            if (list.getRequests() != null && !list.getRequests().isEmpty()) {

                // Parse request
                WireMockRequest request = list.getRequests().get(0);

                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                Document xmlDocument = builder.parse(new InputSource(new StringReader(request.getBody())));
                XPath xPath = XPathFactory.newInstance().newXPath();
                String valueFromMock = xPath.compile(step.getParseMockRequestXPath()).evaluate(xmlDocument);

                scenarioVariables.put(step.getParseMockRequestScenarioVariable(), valueFromMock);
            }
        }
    }

    private void JSONComparing(String expectedResponse, String responseContent, String jsonCompareMode) throws Exception {
        if ((StringUtils.isNotEmpty(expectedResponse) || StringUtils.isNotEmpty(responseContent)) &&
                (!responseContent.equals(expectedResponse))) {
            try {
                JSONAssert.assertEquals(
                        expectedResponse == null ? "" : expectedResponse.replaceAll(" ", " "),
                        responseContent.replaceAll(" ", " "), // Fix broken space in response
                        new IgnoringComparator(StringUtils.isEmpty(jsonCompareMode) ? JSONCompareMode.NON_EXTENSIBLE : JSONCompareMode.valueOf(jsonCompareMode))
                );
            } catch (Error assertionError) {
                throw new Exception(assertionError);
            }
        }
    }

    void saveValuesByJsonXPath(Step step, String responseContent, Map<String, Object> scenarioVariables) {
        if (isNotEmpty(responseContent) && isNotEmpty(step.getJsonXPath())) {
            String[] lines = step.getJsonXPath().split("\\r?\\n");
            for (String line : lines) {
                String[] lineParts = line.split("=", 2);
                String parameterName = lineParts[0].trim();
                String jsonXPath = lineParts[1];
                scenarioVariables.put(parameterName, JsonPath.read(responseContent, jsonXPath).toString());
            }
        }
    }

    void setMockResponses(WireMockAdmin wireMockAdmin, Project project, String testId, List<MockServiceResponse> responseList) throws IOException {
        Long priority = 0L;
        if (responseList != null && wireMockAdmin != null) {
            for (MockServiceResponse mockServiceResponse : responseList) {
                MockDefinition mockDefinition = new MockDefinition(priority--, project.getTestIdHeaderName(), testId);
                mockDefinition.getRequest().setUrl(mockServiceResponse.getServiceUrl());
                mockDefinition.getRequest().setMethod("POST"); // SOAP always POST
                mockDefinition.getResponse().setBody(mockServiceResponse.getResponseBody());
                mockDefinition.getResponse().setStatus(mockServiceResponse.getHttpStatus());
                mockDefinition.getResponse().setHeaders(new HashMap<String, String>() {{
                    put("Content-Type", "text/xml");
                }});

                wireMockAdmin.addMapping(mockDefinition);
            }
        }
    }

    boolean tryUsePolling(Step step, String responseContent) throws InterruptedException {
        boolean retry = true;
        try {
            if (JsonPath.read(responseContent, step.getPollingJsonXPath()) != null) {
                retry = false;
            }
        } catch (PathNotFoundException | IllegalArgumentException e) {
            log.error("", e);
            retry = true;
        }
        if (retry) {
            Thread.sleep(POLLING_RETRY_TIMEOUT_MS);
        }
        return retry;
    }

    void executeSql(Connection connection, Step step, Map<String, Object> scenarioVariables) throws SQLException, ScriptException {
        if (!step.getSqlDataList().isEmpty() && connection != null) {
            for (SqlData sqlData : step.getSqlDataList()) {
                if (StringUtils.isNotEmpty(sqlData.getSql()) && StringUtils.isNotEmpty(sqlData.getSqlSavedParameter())) {
                    try (NamedParameterStatement statement = new NamedParameterStatement(connection, evaluateExpressions(sqlData.getSql(), scenarioVariables, null))) {
                        SqlResultType sqlResultType = sqlData.getSqlReturnType();
                        // Вставить в запрос параметры из scenarioVariables, если они есть.
                        for (Map.Entry<String, Object> scenarioVariable : scenarioVariables.entrySet()) {
                            statement.setString(scenarioVariable.getKey(), String.valueOf(scenarioVariable.getValue()));
                        }
                        try (ResultSet rs = statement.executeQuery()) {
                            Object result;
                            if (sqlResultType == SqlResultType.OBJECT) {
                                result = rs.next() ? rs.getObject(1) : null;
                            } else if (sqlResultType == SqlResultType.LIST) {

                                List<Object> columnData = new ArrayList<>();
                                while (rs.next()) {
                                    columnData.add(rs.getObject(1));
                                }
                                result = columnData;
                            } else {
                                List<String> columnNameList = new LinkedList<>();
                                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                                    columnNameList.add(rs.getMetaData().getColumnName(i));
                                }
                                List<Map<String, Object>> resultData = new ArrayList<>();
                                while (rs.next()) {
                                    Map<String, Object> values = new HashMap<>();
                                    for (String columnName : columnNameList) {
                                        values.put(columnName, rs.getObject(columnName));
                                    }
                                    resultData.add(values);
                                }
                                result = resultData;
                            }
                            scenarioVariables.put(sqlData.getSqlSavedParameter(), result);
                        }
                    }
                }
            }
        }
    }

    public static String insertSavedValues(String template, Map<String, Object> scenarioVariables) {
        if (template != null) {
            for (Map.Entry<String, Object> value : scenarioVariables.entrySet()) {
                String key = String.format("%%%s%%", value.getKey());
                template = template.replaceAll(key, Matcher.quoteReplacement(value.getValue() == null ? "" : String.valueOf(value.getValue())));
            }
        }
        return template;
    }

    String insertSavedValuesToURL(String template, Map<String, Object> scenarioVariables) throws UnsupportedEncodingException {
        if (template != null) {
            for (Map.Entry<String, Object> value : scenarioVariables.entrySet()) {
                String key = String.format("%%%s%%", value.getKey());
                template = template.replaceAll(key, Matcher.quoteReplacement(URLEncoder.encode(value.getValue() == null ? "" : String.valueOf(value.getValue()), "UTF-8")));
            }
        }
        return template;
    }

    String evaluateExpressions(String template, Map<String, Object> scenarioVariables, ResponseHelper responseData) throws ScriptException {
        String result = template;
        if (result != null) {
            Pattern p = Pattern.compile("^.*<f>(.+?)</f>.*$", Pattern.MULTILINE);
            Matcher m = p.matcher(result);
            while (m.find()) {
                ScriptEngineManager manager = new ScriptEngineManager();
                ScriptEngine scriptEngine = manager.getEngineByName("js");
                scriptEngine.put("scenarioVariables", scenarioVariables);
                scriptEngine.put("response", responseData);
                Object evalResult = scriptEngine.eval(m.group(1));
                result = result.replace(
                        "<f>" + m.group(1) + "</f>",
                        Matcher.quoteReplacement(String.valueOf(evalResult))
                );
            }
        }
        return result;
    }

    int calculateNumberRepetitions(Step step, Map<String, Object> scenarioVariables) {
        int numberRepetitions;
        try {
            numberRepetitions = Integer.parseInt(step.getNumberRepetitions());
        } catch (NumberFormatException e) {
            try {
                numberRepetitions = Integer.parseInt(String.valueOf(scenarioVariables.get(step.getNumberRepetitions())));
            } catch (NumberFormatException ex) {
                numberRepetitions = 1;
            }
        }
        numberRepetitions = numberRepetitions > 300 ? 300 : numberRepetitions;
        return numberRepetitions;
    }

    void checkScenarioVariables(Step step, Map<String, Object> scenarioVariables) throws Exception {
        if (step.getSavedValuesCheck() != null) {
            for (Map.Entry<String, String> entry : step.getSavedValuesCheck().entrySet()) {
                String valueExpected = entry.getValue() == null ? "" : entry.getValue();
                for (Map.Entry<String, Object> savedVal : scenarioVariables.entrySet()) {
                    String key = String.format("%%%s%%", savedVal.getKey());
                    valueExpected = valueExpected.replaceAll(key, String.valueOf(savedVal.getValue()));
                }
                String valueActual = String.valueOf(scenarioVariables.get(entry.getKey()));
                if (!valueExpected.equals(valueActual)) {
                    throw new Exception("Saved value " + entry.getKey() + " = " + valueActual + ". Expected: " + valueExpected);
                }
            }
        }
    }

    void checkResponseBody(Step step, String expectedResponse, String actualResponse) throws Exception {
        if (!step.getExpectedResponseIgnore()) {
            if (step.getResponseCompareMode() == null) {
                JSONComparing(expectedResponse, actualResponse, step.getJsonCompareMode());
            } else {
                switch (step.getResponseCompareMode()) {
                    case FULL_MATCH:
                        if (!StringUtils.equals(expectedResponse, actualResponse)) {
                            throw new Exception("\nExpected value: " + expectedResponse + ".\nActual value: " + actualResponse);
                        }
                        break;
                    case IGNORE_MASK:
                        if (!MaskComparator.compare(expectedResponse, actualResponse)) {
                            throw new Exception("\nExpected value: " + expectedResponse + ".\nActual value: " + actualResponse);
                        }
                        break;
                    default:
                        JSONComparing(expectedResponse, actualResponse, step.getJsonCompareMode());
                        break;
                }
            }
        }
    }

    void compareResponse(Step step, String expectedResponse, ResponseHelper responseData) throws Exception {
        if (step.getExpectedResponseIgnore()) {
            return;
        }

        if (step.getResponseCompareMode() == null) {
            jsonComparing(expectedResponse, responseData, step.getJsonCompareMode());
        } else {
            switch (step.getResponseCompareMode()) {
                case FULL_MATCH:
                    if (!StringUtils.equals(expectedResponse, responseData.getContent())) {
                        throw new Exception("\nExpected value: " + expectedResponse + ".\nActual value: " + responseData.getContent());
                    }
                    break;
                case IGNORE_MASK:
                    if (!MaskComparator.compare(expectedResponse, responseData.getContent())) {
                        throw new Exception("\nExpected value: " + expectedResponse + ".\nActual value: " + responseData.getContent());
                    }
                    break;
                default:
                    jsonComparing(expectedResponse, responseData, step.getJsonCompareMode());
                    break;
            }
        }
    }

    private void jsonComparing(String expectedResponse, ResponseHelper responseData, String jsonCompareMode) throws Exception {
        if ((isNotEmpty(expectedResponse) || isNotEmpty(responseData.getContent())) &&
                (!responseData.getContent().equals(expectedResponse))) {
            try {
                JSONAssert.assertEquals(
                        expectedResponse == null ? "" : expectedResponse.replaceAll(" ", " "),
                        // Fix broken space in response
                        responseData.getContent().replaceAll(" ", " "),
                        new IgnoringComparator(StringUtils.isEmpty(jsonCompareMode) ?
                                JSONCompareMode.NON_EXTENSIBLE :
                                JSONCompareMode.valueOf(jsonCompareMode))
                );
            } catch (Error assertionError) {
                throw new Exception(assertionError);
            }
        }
    }

    void setMqMockResponses(MqMockerAdmin mqMockerAdmin, String testId, List<MqMockResponse> mqMockResponseList) throws Exception {
        if (mqMockResponseList != null) {
            if (mqMockerAdmin == null) {
                throw new Exception("MqMockerAdmin is not configured in env.yml");
            }
            for (MqMockResponse mqMockResponse : mqMockResponseList) {
                MqMockDefinition mockMessage = new MqMockDefinition();
                mockMessage.setSourceQueueName(mqMockResponse.getSourceQueueName());
                mockMessage.setResponseBody(mqMockResponse.getResponseBody());
                mockMessage.setHttpUrl(mqMockResponse.getHttpUrl());
                mockMessage.setDestinationQueueName(mqMockResponse.getDestinationQueueName());
                mockMessage.setTestId(testId);
                mqMockerAdmin.addMock(mockMessage);
            }
        }
    }

    public static long parseLongOrVariable(Map<String, Object> scenarioVariables, String value, long defaultValue) {
        long result;
        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            try {
                result = Integer.parseInt(String.valueOf(scenarioVariables.get(value)));
            } catch (NumberFormatException ex) {
                result = defaultValue;
            }
        }
        return result;
    }
}
