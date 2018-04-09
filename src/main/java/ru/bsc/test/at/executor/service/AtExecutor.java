package ru.bsc.test.at.executor.service;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.bsc.test.at.executor.ei.mqmocker.MqMockerAdmin;
import ru.bsc.test.at.executor.ei.mqmocker.model.MqMockDefinition;
import ru.bsc.test.at.executor.ei.wiremock.WireMockAdmin;
import ru.bsc.test.at.executor.ei.wiremock.model.*;
import ru.bsc.test.at.executor.exception.ScenarioStopException;
import ru.bsc.test.at.executor.helper.*;
import ru.bsc.test.at.executor.model.*;
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
import java.io.*;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by sdoroshin on 21.03.2017.
 */
@Slf4j
@SuppressWarnings("unused")
public class AtExecutor {
    private static final int POLLING_RETRY_COUNT = 50;
    private static final int POLLING_RETRY_TIMEOUT_MS = 1000;
    private static final String DEFAULT_CONTENT_TYPE = "text/xml";

    private final ServiceRequestsComparatorHelper serviceRequestsComparatorHelper = new ServiceRequestsComparatorHelper();
    private final MqMockHelper mqMockHelper = new MqMockHelper();

    @Getter
    @Setter
    private String projectPath;

    public void executeScenarioList(Project project, List<Scenario> scenarioExecuteList, Map<Scenario, List<StepResult>> scenarioResultListMap, IStopObserver stopObserver, IExecutingFinishObserver executingFinishObserver) {
        // Создать подключение к БД, которое будет использоваться сценарием для select-запросов.
        Set<Stand> standSet = new LinkedHashSet<>();
        // Собрать список всех используемых стендов выбранными сценариями
        if (project.getStand() != null) {
            standSet.add(project.getStand());
        }
        Map<Stand, Connection> standConnectionMap = new HashMap<>();
        // Создать подключение для каждого используемого стенда
        for (Stand stand : standSet) {
            if (isNotEmpty(stand.getDbUrl())) {
                try {
                    Connection connection = DriverManager.getConnection(stand.getDbUrl(), stand.getDbUser(), stand.getDbPassword());
                    connection.setAutoCommit(false);
                    connection.setReadOnly(true);
                    standConnectionMap.put(stand, connection);
                } catch (SQLException e) {
                    log.warn("sql exception", e);
                    standConnectionMap.put(stand, null);
                }
            }
        }

        try {
            for (Scenario scenario : scenarioExecuteList) {
                List<StepResult> stepResultList = new LinkedList<>();
                scenarioResultListMap.put(scenario, stepResultList);
                executeScenario(project, scenario, project.getStand(), standConnectionMap.get(project.getStand()), stepResultList, stopObserver);
            }
        } finally {
            standConnectionMap.values().stream().filter(Objects::nonNull).forEach(connection -> {
                try {
                    connection.rollback();
                    connection.close();
                } catch (SQLException e) {
                    log.error("Error while rollback", e);
                }
            });
        }
        executingFinishObserver.finish(scenarioResultListMap);
    }

    private void executeScenario(Project project, Scenario scenario, Stand stand, Connection connection, List<StepResult> stepResultList, IStopObserver stopObserver) {
        HttpHelper httpHelper = new HttpHelper();
        Map<String, Object> scenarioVariables = new HashMap<>();
        scenarioVariables.put("__random", RandomStringUtils.randomAlphabetic(40));

        try {
            // перед выполнением каждого сценария выполнять предварительный сценарий, заданный в свойствах проекта (например, сценарий авторизации)
            Scenario beforeScenario = scenario.getBeforeScenarioIgnore() ? null : findScenarioByPath(project.getBeforeScenarioPath(), project.getScenarioList());
            if (beforeScenario != null) {
                executeSteps(connection, stand, beforeScenario.getStepList(), project, httpHelper, scenarioVariables, stepResultList, false, stopObserver);
            }

            executeSteps(connection, stand, scenario.getStepList(), project, httpHelper, scenarioVariables, stepResultList, true, stopObserver);

            // После выполнения сценария выполнить сценарий, заданный в проекте или в сценарии
            Scenario afterScenario = scenario.getAfterScenarioIgnore() ? null : findScenarioByPath(project.getAfterScenarioPath(), project.getScenarioList());
            if (afterScenario != null) {
                executeSteps(connection, stand, afterScenario.getStepList(), project, httpHelper, scenarioVariables, stepResultList, false, stopObserver);
            }

        } catch (ScenarioStopException | InterruptedException e) {
            // Stop scenario executing
            log.error("Error during scenario execution", e);
        }

        httpHelper.closeHttpConnection();
    }

    private Scenario findScenarioByPath(String path, List<Scenario> scenarioList) {
        if (path == null) {
            return null;
        }
        String scenarioCode;
        String scenarioGroupCode;
        String[] scenarioPathParts = path.split("/");
        if (scenarioPathParts.length == 1) {
            scenarioGroupCode = null;
            scenarioCode = scenarioPathParts[0];
        } else if (scenarioPathParts.length == 2) {
            scenarioGroupCode = scenarioPathParts[0];
            scenarioCode = scenarioPathParts[1];
        } else {
            return null;
        }

        return scenarioList.stream()
                .filter(scenario -> Objects.equals(scenario.getCode(), scenarioCode))
                .filter(scenario -> scenarioGroupCode == null || Objects.equals(scenarioGroupCode, scenario.getScenarioGroup()))
                .findAny()
                .orElse(null);
    }

    private long parseLongOrVariable(Map<String, Object> scenarioVariables, String value, long defaultValue) {
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

    private void executeSteps(Connection connection, Stand stand, List<Step> stepList, Project project, HttpHelper httpHelper, Map<String, Object> scenarioVariables, List<StepResult> stepResultList, boolean stepEditable, IStopObserver stopObserver) throws ScenarioStopException, InterruptedException {
        if (stepList == null) {
            return;
        }
        for (Step step : stepList) {
            if (!step.getDisabled()) {
                List<StepParameterSet> parametersEnvironment;
                if (step.getStepParameterSetList() != null && !step.getStepParameterSetList().isEmpty()) {
                    parametersEnvironment = step.getStepParameterSetList();
                } else {
                    parametersEnvironment = new LinkedList<>();
                    parametersEnvironment.add(new StepParameterSet());
                }
                for (StepParameterSet stepParameterSet : parametersEnvironment) {
                    StepResult stepResult = new StepResult(step);
                    stepResult.setStart(new Date().getTime());
                    stepResult.setEditable(stepEditable);
                    stepResultList.add(stepResult);

                    // COM-123 Timeout
                    if (step.getTimeoutMs() != null) {
                        long timeout = parseLongOrVariable(scenarioVariables, step.getTimeoutMs(), 0);
                        if (timeout > 0) {
                            Thread.sleep(Math.min(timeout, 60000L));
                        }
                    }

                    if (stepParameterSet.getStepParameterList() != null) {
                        stepParameterSet.getStepParameterList()
                                .forEach(stepParameter -> scenarioVariables.put(stepParameter.getName(), stepParameter.getValue()));
                        stepResult.setDescription(stepParameterSet.getDescription());
                    }
                    try (
                            WireMockAdmin wireMockAdmin = stand != null && isNotEmpty(stand.getWireMockUrl()) ? new WireMockAdmin(stand.getWireMockUrl() + "/__admin") : null;
                            MqMockerAdmin mqMockerAdmin = stand != null && isNotEmpty(stand.getMqMockUrl()) ? new MqMockerAdmin(stand.getMqMockUrl() + "/__admin") : null
                    ) {
                        if (stand == null) {
                            throw new Exception("Stand is not configured.");
                        }
                        String testId = project.getUseRandomTestId() ? UUID.randomUUID().toString() : "-";
                        stepResult.setTestId(testId);
                        executeTestStep(wireMockAdmin, mqMockerAdmin, connection, stand, httpHelper, scenarioVariables, testId, project, step, stepResult);

                        // После выполнения шага необходимо проверить запросы к веб-сервисам
                        serviceRequestsComparatorHelper.assertTestCaseWSRequests(project, wireMockAdmin, testId, step);

                        mqMockHelper.assertMqRequests(mqMockerAdmin, testId, step, scenarioVariables, project.getMqCheckCount(), project.getMqCheckInterval());

                        stepResult.setSavedParameters(scenarioVariables.toString());
                        stepResult.setResult(StepResult.RESULT_OK);
                    } catch (Exception e) {
                        StringWriter sw = new StringWriter();
                        e.printStackTrace(new PrintWriter(sw));

                        stepResult.setResult(StepResult.RESULT_FAIL);
                        stepResult.setDetails(sw.toString().substring(0, Math.min(sw.toString().length(), 10000)));
                    } finally {
                        stepResult.setStop(new Date().getTime());
                    }

                    stepResult.setScenarioVariables(new HashMap<>(scenarioVariables));

                    if (stopObserver != null && stopObserver.stop()) {
                        throw new ScenarioStopException();
                    }
                }
            }
        }
    }

    private void executeTestStep(WireMockAdmin wireMockAdmin, MqMockerAdmin mqMockerAdmin, Connection connection, Stand stand, HttpHelper http, Map<String, Object> scenarioVariables, String testId, Project project, Step step, StepResult stepResult) throws Exception {

        stepResult.setSavedParameters(scenarioVariables.toString());

        // 0. Установить ответы сервисов, которые будут использоваться в WireMock для определения ответа
        setMockResponses(wireMockAdmin, project, testId, step.getMockServiceResponseList());

        // 0.1 Установить ответы для имитации внешних сервисов, работающих через очереди сообщений
        setMqMockResponses(mqMockerAdmin, project, testId, step.getMqMockResponseList(), scenarioVariables);

        // 1. Выполнить запрос БД и сохранить полученные значения
        executeSql(connection, step, scenarioVariables);
        stepResult.setSavedParameters(scenarioVariables.toString());

        // 1.1 Отправить сообщение в очередь
        sendMessageToQuery(project, step, scenarioVariables);

        // 2. Подстановка сохраненных параметров в строку запроса
        String requestUrl = stand.getServiceUrl() + insertSavedValuesToURL(step.getRelativeUrl(), scenarioVariables);
        stepResult.setRequestUrl(requestUrl);

        // 2.1 Подстановка сохраненных параметров в тело запроса
        String requestBody = insertSavedValues(step.getRequest(), scenarioVariables);

        // 2.2 Вычислить функции в теле запроса
        requestBody = evaluateExpressions(requestBody, scenarioVariables, null);
        stepResult.setRequestBody(requestBody);

        // 2.3 Подстановка переменных сценария в заголовки запроса
        String requestHeaders = insertSavedValues(step.getRequestHeaders(), scenarioVariables);

        // 2.4 Cyclic sending request, COM-84
        long numberRepetitions = parseLongOrVariable(scenarioVariables, step.getNumberRepetitions(), 1);
        numberRepetitions = numberRepetitions > 300 ? 300 : numberRepetitions;

        stepResult.setRequestDataList(new LinkedList<>());
        for (int repetitionCounter = 0; repetitionCounter < numberRepetitions; repetitionCounter++) {

            // Polling
            int retryCounter = 0;
            boolean retry = false;

            ResponseHelper responseData;
            do {
                RequestData requestData = new RequestData();
                stepResult.getRequestDataList().add(requestData);

                retryCounter++;
                // 3. Выполнить запрос
                if (step.getRequestBodyType() == null || RequestBodyType.JSON.equals(step.getRequestBodyType())) {
                    responseData = http.request(
                            step.getRequestMethod(),
                            requestUrl,
                            requestBody,
                            requestHeaders,
                            project.getTestIdHeaderName(),
                            testId
                    );
                } else {
                    if (step.getFormDataList() == null) {
                        step.setFormDataList(Collections.emptyList());
                    }
                    stepResult.setRequestBody(
                            step.getFormDataList()
                                    .stream()
                                    .map(formData -> {
                                        String result = formData.getFieldName() + " = ";
                                        if (FieldType.TEXT.equals(formData.getFieldType()) || formData.getFieldType() == null) {
                                            result += insertSavedValues(formData.getValue(), scenarioVariables);
                                        } else {
                                            result += (projectPath == null ? "" : projectPath) + formData.getFilePath();
                                        }
                                        return result;
                                    })
                                    .collect(Collectors.joining("\r\n")));
                    responseData = http.request(
                            projectPath,
                            step,
                            requestUrl,
                            requestHeaders,
                            project.getTestIdHeaderName(),
                            testId,
                            scenarioVariables
                    );
                }
                requestData.setRequestBody(stepResult.getRequestBody());
                requestData.setResponseBody(responseData.getContent());

                // Выполнить скрипт
                if (isNotEmpty(step.getScript())) {
                    ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("js");
                    scriptEngine.put("stepStatus", new StepStatus());
                    scriptEngine.put("scenarioVariables", scenarioVariables);
                    scriptEngine.put("response", responseData);

                    scriptEngine.eval(step.getScript());

                    StepStatus stepStatus = (StepStatus) scriptEngine.get("stepStatus");
                    if (isNotEmpty(stepStatus.getException())) {
                        throw new Exception(stepStatus.getException());
                    }
                }

                // 3.1. Polling
                if (step.getUsePolling()) {
                    retry = tryUsePolling(step, responseData);
                }
            } while (retry && retryCounter <= POLLING_RETRY_COUNT);

            stepResult.setPollingRetryCount(retryCounter);
            stepResult.setActual(responseData.getContent());
            stepResult.setExpected(step.getExpectedResponse());

            // 4. Сохранить полученные значения
            saveValuesByJsonXPath(step, responseData, scenarioVariables);

            stepResult.setSavedParameters(scenarioVariables.toString());
            stepResult.setCookies(http.getCookies().stream().map(cookie -> cookie.getName() + ": " + cookie.getValue()).collect(Collectors.joining(", ")));

            // 4.1 Проверить сохраненные значения
            if (step.getSavedValuesCheck() != null) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> entry : step.getSavedValuesCheck().entrySet()) {
                    String valueExpected = entry.getValue() == null ? "" : entry.getValue();
                    for (Map.Entry<String, Object> savedVal : scenarioVariables.entrySet()) {
                        String key = String.format("%%%s%%", savedVal.getKey());
                        valueExpected = valueExpected.replaceAll(key, String.valueOf(savedVal.getValue()));
                    }
                    String valueActual = String.valueOf(scenarioVariables.get(entry.getKey()));
                    if (!valueExpected.equals(valueActual)) {
                        sb.append("\nSaved value ").append(entry.getKey()).append(" = ").append(valueActual).append(". Expected: ").append(valueExpected);
                    }
                }
                if (sb.length() > 0) {
                    throw new Exception(sb.toString());
                }
            }

            // 5. Подставить сохраненые значения в ожидаемый результат
            String expectedResponse = insertSavedValues(step.getExpectedResponse(), scenarioVariables);
            // 5.1. Расчитать выражения <f></f>
            expectedResponse = evaluateExpressions(expectedResponse, scenarioVariables, responseData);
            stepResult.setExpected(expectedResponse);

            // 6. Проверить код статуса ответа
            Integer expectedStatusCode = step.getExpectedStatusCode();
            if ((expectedStatusCode != null) && (expectedStatusCode != responseData.getStatusCode())) {
                throw new Exception(String.format(
                        "Expected status code: %d. Actual status code: %d",
                        expectedStatusCode,
                        responseData.getStatusCode()
                ));
            }

            compareResponse(step, expectedResponse, responseData);
        }

        // 7. Прочитать, что тестируемый сервис отправлял в REST-заглушку.
        parseMockRequests(project, step, wireMockAdmin, scenarioVariables, testId);
    }

    private void compareResponse(Step step, String expectedResponse, ResponseHelper responseData) throws Exception {
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

    private void parseMockRequests(Project project, Step step, WireMockAdmin wireMockAdmin, Map<String, Object> scenarioVariables, String testId) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        if (step.getParseMockRequestUrl() != null) {
            MockRequest mockRequest = new MockRequest();
            HashMap<String, String> headerValue = new HashMap<>();
            headerValue.put("equalTo", testId);
            mockRequest.getHeaders().put(project.getTestIdHeaderName(), headerValue);
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

    private void sendMessageToQuery(Project project, Step step, Map<String, Object> scenarioVariables) throws Exception {
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

            List<NameValueProperty> generatedProperties = new LinkedList<>();
            if (step.getMqPropertyList() != null) {
                step.getMqPropertyList().forEach(nameValueProperty -> {
                    NameValueProperty pair = new NameValueProperty();
                    pair.setName(nameValueProperty.getName());
                    try {
                        pair.setValue(evaluateExpressions(insertSavedValues(nameValueProperty.getValue(), scenarioVariables), scenarioVariables, null));
                    } catch (ScriptException e) {
                        log.error("{}", e);
                    }
                    generatedProperties.add(pair);
                });
            }
            mqManager.sendTextMessage(step.getMqName(), message, generatedProperties);
        }
    }

    private void saveValuesByJsonXPath(Step step, ResponseHelper responseData, Map<String, Object> scenarioVariables) {
        if (isNotEmpty(responseData.getContent()) && isNotEmpty(step.getJsonXPath())) {
            String[] lines = step.getJsonXPath().split("\\r?\\n");
            for (String line : lines) {
                String[] lineParts = line.split("=", 2);
                String parameterName = lineParts[0].trim();
                String jsonXPath = lineParts[1];
                scenarioVariables.put(parameterName, JsonPath.read(responseData.getContent(), jsonXPath).toString());
            }
        }
    }

    private void setMockResponses(WireMockAdmin wireMockAdmin, Project project, String testId, List<MockServiceResponse> responseList) throws IOException {
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
                mockDefinition.getResponse().setHeaders(new HashMap<>());
                String contentType = StringUtils.isNoneBlank(mockServiceResponse.getContentType()) ?
                        mockServiceResponse.getContentType() :
                        DEFAULT_CONTENT_TYPE;
                mockDefinition.getResponse().getHeaders().put("Content-Type", contentType);

                wireMockAdmin.addMapping(mockDefinition);
            }
        }
    }

    private void setMqMockResponses(MqMockerAdmin mqMockerAdmin, Project project, String testId, List<MqMockResponse> mqMockResponseList, Map<String, Object> scenarioVariables) throws Exception {
        if (mqMockResponseList != null) {
            if (mqMockerAdmin == null) {
                throw new Exception("MqMockerAdmin is not configured in env.yml");
            }
            for (MqMockResponse mqMockResponse : mqMockResponseList) {
                MqMockDefinition mockMessage = new MqMockDefinition();
                mockMessage.setSourceQueueName(mqMockResponse.getSourceQueueName());
                mockMessage.setResponseBody(evaluateExpressions(mqMockResponse.getResponseBody(), scenarioVariables, null));
                mockMessage.setHttpUrl(mqMockResponse.getHttpUrl());
                mockMessage.setDestinationQueueName(mqMockResponse.getDestinationQueueName());
                mockMessage.setTestId(testId);
                mqMockerAdmin.addMock(mockMessage);
            }
        }
    }

    private boolean tryUsePolling(Step step, ResponseHelper responseData) throws InterruptedException {
        boolean retry = true;
        try {
            if (JsonPath.read(responseData.getContent(), step.getPollingJsonXPath()) != null) {
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

    private void executeSql(Connection connection, Step step, Map<String, Object> scenarioVariables) throws SQLException, ScriptException {
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
                                Object result = rs.next() ? rs.getObject(1) : null;
                                scenarioVariables.put(sqlData.getSqlSavedParameter(), result);
                            } else if (sqlResultType == SqlResultType.LIST) {

                                List<Object> columnData = new ArrayList<>();
                                while (rs.next()) {
                                    columnData.add(rs.getObject(1));
                                }
                                scenarioVariables.put(sqlData.getSqlSavedParameter(), columnData);
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
                                scenarioVariables.put(sqlData.getSqlSavedParameter(), resultData);
                            }
                        }
                    }
                }
            }
        }
    }

    public static String insertSavedValues(String template, Map<String, Object> scenarioVariables) {
        String result = template;
        if (result != null) {
            for (Map.Entry<String, Object> value : scenarioVariables.entrySet()) {
                String key = String.format("%%%s%%", value.getKey());
                result = result.replaceAll(
                        key,
                        Matcher.quoteReplacement(value.getValue() == null ? "" : String.valueOf(value.getValue()))
                );
            }
        }
        return result;
    }

    private String insertSavedValuesToURL(String template, Map<String, Object> scenarioVariables) throws UnsupportedEncodingException {
        String result = template;
        if (result != null) {
            for (Map.Entry<String, Object> value : scenarioVariables.entrySet()) {
                String key = String.format("%%%s%%", value.getKey());
                result = result.replaceAll(key,
                        Matcher.quoteReplacement(URLEncoder.encode(
                                value.getValue() == null ? "" : String.valueOf(value.getValue()),
                                "UTF-8"
                        ))
                );
            }
        }
        return result;
    }

    public static String evaluateExpressions(String template, Map<String, Object> scenarioVariables, ResponseHelper responseData) throws ScriptException {
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

    @Override
    public String toString() {
        return "AtExecutor{}";
    }
}
