package ru.bsc.test.at.executor.step.executor;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.bsc.test.at.executor.helper.NamedParameterStatement;
import ru.bsc.test.at.executor.model.MockServiceResponse;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.mq.IMqManager;
import ru.bsc.test.at.executor.mq.MqManagerFactory;
import ru.bsc.test.at.executor.validation.IgnoringComparator;
import ru.bsc.test.at.executor.validation.MaskComparator;
import ru.bsc.test.at.executor.wiremock.WireMockAdmin;
import ru.bsc.test.at.executor.wiremock.mockdefinition.MockDefinition;
import ru.bsc.test.at.executor.wiremock.mockdefinition.MockRequest;
import ru.bsc.test.at.executor.wiremock.mockdefinition.RequestList;
import ru.bsc.test.at.executor.wiremock.mockdefinition.WireMockRequest;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class AbstractStepExecutor implements IStepExecutor {

    private final static int POLLING_RETRY_TIMEOUT_MS = 1000;

    void sendMessageToQuery(Project project, Step step, Map<String, Object> scenarioVariables) throws Exception {
        if (step.getMqName() != null && step.getMqMessage() != null) {
            if (project.getAmqpBroker() == null) {
                throw new Exception("AMQP broker is not configured in Project settings.");
            }
            IMqManager mqManager = MqManagerFactory.getMqManager(project.getAmqpBroker().getMqService());

            mqManager.setHost(project.getAmqpBroker().getHost());
            mqManager.setPort(project.getAmqpBroker().getPort());
            mqManager.setUsername(project.getAmqpBroker().getUsername());
            mqManager.setPassword(project.getAmqpBroker().getPassword());

            String message = insertSavedValues(step.getMqMessage(), scenarioVariables);
            mqManager.sendTextMessage(step.getMqName(), message);
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
        if (StringUtils.isNotEmpty(responseContent)) {
            if (StringUtils.isNotEmpty(step.getJsonXPath())) {

                String lines[] = step.getJsonXPath().split("\\r?\\n");
                for (String line: lines) {
                    String[] lineParts = line.split("=", 2);
                    String parameterName = lineParts[0].trim();
                    String jsonXPath = lineParts[1];

                    // JsonPath.read(responseData.getContent(), "$.accountPortfolio[0].accountInfo.accountNumber");
                    scenarioVariables.put(parameterName, JsonPath.read(responseContent, jsonXPath).toString());
                }
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
        if (!step.getUsePolling()) {
            return false;
        }
        boolean retry = true;
        try {
            if (JsonPath.read(responseContent, step.getPollingJsonXPath()) != null) {
                retry = false;
            }
        } catch (PathNotFoundException | IllegalArgumentException e) {
            retry = true;
        }
        if (retry) {
            Thread.sleep(POLLING_RETRY_TIMEOUT_MS);
        }
        return retry;
    }

    void executeSql(Connection connection, Step step, Map<String, Object> scenarioVariables) throws SQLException {
        if (StringUtils.isNotEmpty(step.getSql()) && StringUtils.isNotEmpty(step.getSqlSavedParameter()) && connection != null) {
            try (NamedParameterStatement statement = new NamedParameterStatement(connection, step.getSql()) ) {
                // Вставить в запрос параметры из scenarioVariables, если они есть.
                for (Map.Entry<String, Object> scenarioVariable : scenarioVariables.entrySet()) {
                    statement.setString(scenarioVariable.getKey(), String.valueOf(scenarioVariable.getValue()));
                }
                try (ResultSet rs = statement.executeQuery()) {
                    int columnCount = rs.getMetaData().getColumnCount();
                    if (rs.next()) {
                        String[] sqlSavedParameterList = step.getSqlSavedParameter().split(",");
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
                }
            }
        }
    }

    String insertSavedValues(String template, Map<String, Object> scenarioVariables) {
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

    String evaluateExpressions(String template) throws ScriptException {
        if (template != null) {
            Pattern p = Pattern.compile("^.*<f>(.+?)</f>.*$", Pattern.MULTILINE);
            Matcher m = p.matcher(template);
            while (m.find()) {
                ScriptEngineManager manager = new ScriptEngineManager();
                ScriptEngine engine = manager.getEngineByName("js");
                Object result = engine.eval(m.group(1));
                template = template.replace("<f>" + m.group(1) + "</f>", Matcher.quoteReplacement(String.valueOf(result)));
            }
        }
        return template;
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
}
