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
import ru.bsc.test.at.executor.ei.wiremock.model.MqMockDefinition;
import ru.bsc.test.at.executor.ei.wiremock.WireMockAdmin;
import ru.bsc.test.at.executor.ei.wiremock.model.BasicAuthCredentials;
import ru.bsc.test.at.executor.ei.wiremock.model.MatchesXPath;
import ru.bsc.test.at.executor.ei.wiremock.model.MockDefinition;
import ru.bsc.test.at.executor.ei.wiremock.model.MockRequest;
import ru.bsc.test.at.executor.ei.wiremock.model.RequestList;
import ru.bsc.test.at.executor.ei.wiremock.model.WireMockRequest;
import ru.bsc.test.at.executor.helper.MqClient;
import ru.bsc.test.at.executor.helper.NamedParameterStatement;
import ru.bsc.test.at.executor.helper.ResponseHelper;
import ru.bsc.test.at.executor.model.MockServiceResponse;
import ru.bsc.test.at.executor.model.MqMockResponse;
import ru.bsc.test.at.executor.model.NameValueProperty;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.SqlData;
import ru.bsc.test.at.executor.model.SqlResultType;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepResult;
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
import java.util.Collections;
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
    private static final String DEFAULT_CONTENT_TYPE = "text/xml";

    void sendMessageToQuery(Project project, Step step, Map<String, Object> scenarioVariables, MqClient mqClient, String testIdHeaderName, String testId) throws Exception {
        log.debug("Send message to query {} {} {}", project, step, scenarioVariables);
        if (step.getMqName() != null && step.getMqMessage() != null) {
            String message = insertSavedValues(step.getMqMessage(), scenarioVariables);

            Map<String, Object> generatedProperties = new HashMap<>();
            if (step.getMqPropertyList() != null) {
                step.getMqPropertyList().forEach(nameValueProperty -> {
                    NameValueProperty pair = new NameValueProperty();
                    pair.setName(nameValueProperty.getName());
                    try {
                        generatedProperties.put(
                                nameValueProperty.getName(),
                                evaluateExpressions(insertSavedValues(nameValueProperty.getValue(), scenarioVariables), scenarioVariables, null)
                        );
                    } catch (ScriptException e) {
                        log.error("{}", e);
                    }
                });
            }

            mqClient.sendMessage(step.getMqName(), message, generatedProperties, testIdHeaderName, testId);
        }
    }

    void parseMockRequests(Project project, Step step, WireMockAdmin wireMockAdmin, Map<String, Object> scenarioVariables, String testId) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        log.debug("Parse mock requests {} {} {} {}", project, step, scenarioVariables, testId);
        if (step.getParseMockRequestUrl() != null) {
            MockRequest mockRequest = new MockRequest();
            mockRequest.getHeaders().put(project.getTestIdHeaderName(), new HashMap<String, String>() {{
                put("equalTo", testId);
            }});
            mockRequest.setUrl(step.getParseMockRequestUrl());
            RequestList list = wireMockAdmin.findRestRequests(mockRequest);
            if (list.getRequests() != null && !list.getRequests().isEmpty()) {

                // Parse request
                WireMockRequest request = list.getRequests().get(0);

                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                Document xmlDocument = builder.parse(new InputSource(new StringReader(request.getBody())));
                XPath xPath = XPathFactory.newInstance().newXPath();
                String valueFromMock = xPath.compile(step.getParseMockRequestXPath()).evaluate(xmlDocument);

                scenarioVariables.put(step.getParseMockRequestScenarioVariable().trim(), valueFromMock);
            }
        }
    }

    private void JSONComparing(String expectedResponse, String responseContent, String jsonCompareMode) throws Exception {
        log.debug("Json comparing {} {} {}", expectedResponse, responseContent, jsonCompareMode);
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
        log.debug("Save values by json xpath {} {} {}", step, responseContent, scenarioVariables);
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
        log.debug("Setting REST-mock responses {} {} {} {}", wireMockAdmin, project, testId, responseList);
        Long priority = 0L;
        if (responseList != null && wireMockAdmin != null) {
            for (MockServiceResponse mockServiceResponse : responseList) {
                MockDefinition mockDefinition = new MockDefinition(priority--, project.getTestIdHeaderName(), testId);
                mockDefinition.getRequest().setUrl(mockServiceResponse.getServiceUrl());
                // SOAP always POST
                mockDefinition.getRequest().setMethod("POST");
                if(isNotEmpty(mockServiceResponse.getPassword()) || isNotEmpty(mockServiceResponse.getUserName())){
                    BasicAuthCredentials credentials = new BasicAuthCredentials();
                    credentials.setPassword(mockServiceResponse.getPassword());
                    credentials.setUsername(mockServiceResponse.getUserName());
                    mockDefinition.getRequest().setBasicAuthCredentials(credentials);
                }
                if(isNotEmpty(mockServiceResponse.getPathFilter())) {
                    MatchesXPath matchesXPath = new MatchesXPath();
                    matchesXPath.setMatchesXPath(mockServiceResponse.getPathFilter());
                    mockDefinition.getRequest().setBodyPatterns(Collections.singletonList(matchesXPath));
                }

                mockDefinition.getResponse().setBody(mockServiceResponse.getResponseBody());
                mockDefinition.getResponse().setStatus(mockServiceResponse.getHttpStatus());

                HashMap<String, String> headers = new HashMap<>();
                mockServiceResponse.getHeaders().forEach(header -> {
                    headers.put(header.getHeaderName(), header.getHeaderValue());
                });
                mockDefinition.getResponse().setHeaders(headers);
                String contentType = StringUtils.isNoneBlank(mockServiceResponse.getContentType()) ?
                        mockServiceResponse.getContentType() :
                        DEFAULT_CONTENT_TYPE;
                mockDefinition.getResponse().getHeaders().put("Content-Type", contentType);

                wireMockAdmin.addRestMapping(mockDefinition);
            }
        }
    }

    boolean tryUsePolling(Step step, String responseContent) throws InterruptedException {
        log.debug("trying use polling {} {}", step, responseContent);
        if (!step.getUsePolling()) {
            return false;
        }
        boolean retry = true;
        try {
            if (JsonPath.read(responseContent, step.getPollingJsonXPath()) != null) {
                retry = false;
            }
        } catch (PathNotFoundException | IllegalArgumentException e) {
            log.info("", e);
            retry = true;
        }
        if (retry) {
            Thread.sleep(POLLING_RETRY_TIMEOUT_MS);
        }
        log.debug("trying use polling? Is - {}", retry);
        return retry;
    }

    void executeSql(Connection connection, Step step, Map<String, Object> scenarioVariables, StepResult stepResult) throws SQLException, ScriptException {
        log.debug("executing sql {}, {}", step, scenarioVariables);
        if (!step.getSqlDataList().isEmpty() && connection != null) {
            List<String> queryList = new LinkedList<>();
            stepResult.setSqlQueryList(queryList);
            for (SqlData sqlData : step.getSqlDataList()) {
                if (StringUtils.isNotEmpty(sqlData.getSql()) && StringUtils.isNotEmpty(sqlData.getSqlSavedParameter())) {
                    String query = evaluateExpressions(sqlData.getSql(), scenarioVariables, null);
                    queryList.add(query);
                    try (NamedParameterStatement statement = new NamedParameterStatement(connection, query)) {
                        SqlResultType sqlResultType = sqlData.getSqlReturnType();
                        // Вставить в запрос параметры из scenarioVariables, если они есть.
                        for (Map.Entry<String, Object> scenarioVariable : scenarioVariables.entrySet()) {
                            statement.setString(scenarioVariable.getKey(), String.valueOf(scenarioVariable.getValue()));
                        }
                        try (ResultSet rs = statement.executeQuery()) {
                            log.debug("Executing query {}", sqlData);
                            if (sqlResultType == SqlResultType.ROW) {
                                int columnCount = rs.getMetaData().getColumnCount();
                                if (rs.next()) {
                                    String[] sqlSavedParameterList = sqlData.getSqlSavedParameter().split(",");
                                    int i = 1;
                                    for (String parameterName: sqlSavedParameterList) {
                                        if (parameterName.trim().isEmpty()) {
                                            continue;
                                        }
                                        if (i > columnCount) {
                                            break;
                                        }
                                        scenarioVariables.put(parameterName.trim(), rs.getString(i));
                                        i++;
                                    }
                                }
                            } else if (sqlResultType == SqlResultType.OBJECT) {
                                log.debug("Reading Object from result set ...");
                                Object result = rs.next() ? rs.getObject(1) : null;
                                scenarioVariables.put(sqlData.getSqlSavedParameter().trim(), result);
                            } else if (sqlResultType == SqlResultType.LIST) {
                                log.debug("Reading List from result set ...");
                                List<Object> columnData = new ArrayList<>();
                                while (rs.next()) {
                                    columnData.add(rs.getObject(1));
                                }
                                scenarioVariables.put(sqlData.getSqlSavedParameter().trim(), columnData);
                            } else {
                                log.debug("Reading custom result set ...");
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
                                scenarioVariables.put(sqlData.getSqlSavedParameter().trim(), resultData);
                            }
                        }
                    }
                }
            }
        }
    }

    public static String insertSavedValues(String template, Map<String, Object> scenarioVariables) {
        log.debug("insert saved values {}, {}", template, scenarioVariables);
        if (template != null) {
            for (Map.Entry<String, Object> value : scenarioVariables.entrySet()) {
                String key = String.format("%%%s%%", value.getKey());
                template = template.replaceAll(key, Matcher.quoteReplacement(value.getValue() == null ? "" : String.valueOf(value.getValue())));
            }
        }
        log.debug("insert saved values result {}", template);
        return template;
    }

    String insertSavedValuesToURL(String template, Map<String, Object> scenarioVariables) throws UnsupportedEncodingException {
        log.debug("insert saved values to URL {}, {}", template, scenarioVariables);
        if (template != null) {
            for (Map.Entry<String, Object> value : scenarioVariables.entrySet()) {
                String key = String.format("%%%s%%", value.getKey());
                template = template.replaceAll(key, Matcher.quoteReplacement(URLEncoder.encode(value.getValue() == null ? "" : String.valueOf(value.getValue()), "UTF-8")));
            }
        }
        log.debug("insert saved values to URL result {}", template);
        return template;
    }

    public static String evaluateExpressions(String template, Map<String, Object> scenarioVariables, ResponseHelper responseData) throws ScriptException {
        log.debug("evaluate expressions {}, {} {}", template, scenarioVariables, responseData);
        String result = template;
        if (result != null) {
            Pattern p = Pattern.compile(".*?<f>(.+?)</f>.*?", Pattern.MULTILINE);
            Matcher m = p.matcher(result);
            while (m.find()) {
                log.debug("regexp matches {}", p.pattern());
                ScriptEngineManager manager = new ScriptEngineManager();
                ScriptEngine scriptEngine = manager.getEngineByName("js");
                scriptEngine.put("scenarioVariables", scenarioVariables);
                scriptEngine.put("response", responseData);
                Object evalResult = scriptEngine.eval(m.group(1));
                result = result.replace(
                        "<f>" + m.group(1) + "</f>",
                        Matcher.quoteReplacement(String.valueOf(evalResult))
                );
                log.debug("evaluating result {}", result);
            }
        }
        log.debug("evaluate expressions result {}", responseData);
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
            log.debug("Response compare mode {}, ", step.getResponseCompareMode());
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

    void setMqMockResponses(WireMockAdmin wireMockAdmin, String testId, List<MqMockResponse> mqMockResponseList, Map<String, Object> scenarioVariables) throws Exception {
        log.debug("Setting MQ mock responses {} {} {} {}", wireMockAdmin, testId, mqMockResponseList, scenarioVariables);
        if (mqMockResponseList != null) {
            if (wireMockAdmin == null) {
                throw new Exception("wireMockAdmin is not configured in env.yml");
            }
            for (MqMockResponse mqMockResponse : mqMockResponseList) {
                MqMockDefinition mockMessage = new MqMockDefinition();
                mockMessage.setSourceQueueName(mqMockResponse.getSourceQueueName());
                mockMessage.setResponseBody(evaluateExpressions(mqMockResponse.getResponseBody(), scenarioVariables, null));
                mockMessage.setHttpUrl(mqMockResponse.getHttpUrl());
                mockMessage.setDestinationQueueName(mqMockResponse.getDestinationQueueName());
                mockMessage.setTestId(testId);
                wireMockAdmin.addMqMapping(mockMessage);
            }
        }
    }
}
