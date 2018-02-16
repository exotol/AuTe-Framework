package ru.bsc.test.at.executor.service;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bsc.test.at.executor.helper.HttpHelper;
import ru.bsc.test.at.executor.helper.NamedParameterStatement;
import ru.bsc.test.at.executor.helper.ResponseHelper;
import ru.bsc.test.at.executor.helper.ServiceRequestsComparatorHelper;
import ru.bsc.test.at.executor.model.FieldType;
import ru.bsc.test.at.executor.model.MockServiceResponse;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.RequestBodyType;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepParameterSet;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.model.StepStatus;
import ru.bsc.test.at.executor.mq.IMqManager;
import ru.bsc.test.at.executor.mq.MqManagerFactory;
import ru.bsc.test.at.executor.validation.IgnoringComparator;
import ru.bsc.test.at.executor.validation.MaskComparator;
import ru.bsc.test.at.executor.wiremock.WireMockAdmin;
import ru.bsc.test.at.executor.wiremock.mockdefinition.MockDefinition;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class AtExecutor {
    private static final int POLLING_RETRY_COUNT = 50;
    private static final int POLLING_RETRY_TIMEOUT_MS = 1000;

    private final Logger logger = LoggerFactory.getLogger(AtExecutor.class);

    private final ServiceRequestsComparatorHelper serviceRequestsComparatorHelper = new ServiceRequestsComparatorHelper();
    private String projectPath;

    public Map<Scenario, List<StepResult>> executeScenarioList(Project project, List<Scenario> scenarioExecuteList) {
        // Создать подключение к БД, которое будет использоваться сценарием для select-запросов.
        Set<Stand> standSet = new LinkedHashSet<>();
        // Собрать список всех используемых стендов выбранными сценариями
        if (project.getStand() != null) {
            standSet.add(project.getStand());
        }
        Map<Stand, Connection> standConnectionMap = new HashMap<>();
        // Создать подключение для каждого используемого стенда
        for (Stand stand: standSet) {
            if (StringUtils.isNotEmpty(stand.getDbUrl())) {
                try {
                    Connection connection = DriverManager.getConnection(stand.getDbUrl(), stand.getDbUser(), stand.getDbPassword());
                    connection.setAutoCommit(false);
                    connection.setReadOnly(true);
                    standConnectionMap.put(stand, connection);
                } catch (SQLException e) {
                    logger.warn("sql exception", e);
                    standConnectionMap.put(stand, null);
                }
            }
        }

        Map<Scenario, List<StepResult>> scenarioResultList = new HashMap<>();
        try {
            for (Scenario scenario : scenarioExecuteList) {
                scenarioResultList.put(
                        scenario,
                        executeScenario(project, scenario, project.getStand(), standConnectionMap.get(project.getStand()))
                );
            }
        } finally {
            standConnectionMap.values().stream().filter(Objects::nonNull).forEach(connection -> {
                try {
                    connection.rollback();
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Error while rollback", e);
                }
            });
        }
        return scenarioResultList;
    }

    private List<StepResult> executeScenario(Project project, Scenario scenario, Stand stand, Connection connection) {
        List<StepResult> stepResultList = new LinkedList<>();
        HttpHelper httpHelper = new HttpHelper();
        Map<String, String> savedValues = new HashMap<>();
        savedValues.put("__random", RandomStringUtils.randomAlphabetic(40));

        // перед выполнением каждого сценария выполнять предварительный сценарий, заданный в свойствах проекта (например, сценарий авторизации)
        Scenario beforeScenario = scenario.getBeforeScenarioIgnore() ? null : findScenarioByPath(project.getBeforeScenarioPath(), project.getScenarioList());
        if (beforeScenario != null) {
            stepResultList.addAll(
                    executeSteps(connection, stand, beforeScenario.getStepList(), project, httpHelper, savedValues)
                            .stream()
                            .peek(stepResult -> stepResult.setEditable(false))
                            .collect(Collectors.toList()));
        }

        stepResultList.addAll(
                executeSteps(connection, stand, scenario.getStepList(), project, httpHelper, savedValues)
                        .stream()
                        .peek(stepResult -> stepResult.setEditable(true))
                        .collect(Collectors.toList()));

        // После выполнения сценария выполнить сценарий, заданный в проекте или в сценарии
        Scenario afterScenario = scenario.getAfterScenarioIgnore() ? null : findScenarioByPath(project.getAfterScenarioPath(), project.getScenarioList());
        if (afterScenario != null) {
            stepResultList.addAll(
                    executeSteps(connection, stand, afterScenario.getStepList(), project, httpHelper, savedValues)
                            .stream()
                            .peek(stepResult -> stepResult.setEditable(false))
                            .collect(Collectors.toList()));
        }

        httpHelper.closeHttpConnection();

        return stepResultList;
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

    private List<StepResult> executeSteps(Connection connection, Stand stand, List<Step> stepList, Project project, HttpHelper httpHelper, Map<String, String> savedValues) {
        List<StepResult> stepResultList = new LinkedList<>();
        if (stepList == null) {
            return null;
        }
        for (Step step: stepList) {
            if (!step.getDisabled()) {
                List<StepParameterSet> parametersEnvironment;
                if (step.getStepParameterSetList() != null && !step.getStepParameterSetList().isEmpty()) {
                    parametersEnvironment = step.getStepParameterSetList();
                } else {
                    parametersEnvironment = new LinkedList<>();
                    parametersEnvironment.add(new StepParameterSet());
                }
                for (StepParameterSet stepParameterSet: parametersEnvironment) {
                    StepResult stepResult = new StepResult(step);
                    stepResult.setStart(new Date().getTime());
                    stepResultList.add(stepResult);

                    if (stepParameterSet.getStepParameterList() != null) {
                        stepParameterSet.getStepParameterList()
                                .forEach(stepParameter -> savedValues.put(stepParameter.getName(), stepParameter.getValue()));
                        stepResult.setDescription(stepParameterSet.getDescription());
                    }
                    try (WireMockAdmin wireMockAdmin = stand != null && StringUtils.isNotEmpty(stand.getWireMockUrl()) ? new WireMockAdmin(stand.getWireMockUrl() + "/__admin") : null) {
                        if (stand == null) {
                            throw new Exception("Stand is not configured.");
                        }
                        String testId = project.getUseRandomTestId() ? UUID.randomUUID().toString() : "-";
                        stepResult.setTestId(testId);
                        executeTestStep(wireMockAdmin, connection, stand, httpHelper, savedValues, testId, project, step, stepResult);

                        // После выполнения шага необходимо проверить запросы к веб-сервисам
                        serviceRequestsComparatorHelper.assertTestCaseWSRequests(project, wireMockAdmin, testId, step);

                        stepResult.setResult(StepResult.RESULT_OK);
                    } catch (Exception e) {
                        StringWriter sw = new StringWriter();
                        e.printStackTrace(new PrintWriter(sw));

                        stepResult.setResult(StepResult.RESULT_FAIL);
                        stepResult.setDetails(sw.toString().substring(0, Math.min(sw.toString().length(), 10000)));
                    } finally {
                        stepResult.setStop(new Date().getTime());
                    }
                }
            }
        }
        return stepResultList;
    }

    private void executeTestStep(WireMockAdmin wireMockAdmin, Connection connection, Stand stand, HttpHelper http, Map<String, String> savedValues, String testId, Project project, Step step, StepResult stepResult) throws Exception {

        stepResult.setSavedParameters(savedValues.toString());

        // 0. Установить ответы сервисов, которые будут использоваться в SoapUI для определения ответа
        setMockResponses(wireMockAdmin, project, testId, step.getMockServiceResponseList());

        // 1. Выполнить запрос БД и сохранить полученные значения
        executeSql(connection, step, savedValues);
        stepResult.setSavedParameters(savedValues.toString());

        // 1.1 Отправить сообщение в очередь
        sendMessageToQuery(project, step, savedValues);

        // 2. Подстановка сохраненных параметров в строку запроса
        String requestUrl = stand.getServiceUrl() + insertSavedValuesToURL(step.getRelativeUrl(), savedValues);
        stepResult.setRequestUrl(requestUrl);

        // 2.1 Подстановка сохраненных параметров в тело запроса
        String requestBody = insertSavedValues(step.getRequest(), savedValues);

        // 2.2 Вычислить функции в теле запроса
        requestBody = evaluateExpressions(requestBody);
        stepResult.setRequestBody(requestBody);

        // 2.3 Подстановка переменных сценария в заголовки запроса
        String requestHeaders = insertSavedValues(step.getRequestHeaders(), savedValues);

        int retryCounter = 0;
        boolean retry = false;
        ResponseHelper responseData;
        do {
            retryCounter++;
            // 3. Выполнить запрос
            if (step.getRequestBodyType() == null || RequestBodyType.JSON.equals(step.getRequestBodyType())) {
                responseData = http.request(
                        step.getRequestMethod(),
                        requestUrl,
                        requestBody,
                        requestHeaders,
                        project.getTestIdHeaderName(),
                        testId);
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
                                        result += formData.getValue();
                                    } else {
                                        result += (projectPath == null ? "" : projectPath) + formData.getFilePath();
                                    }
                                    return result;
                                })
                                .collect(Collectors.joining("\r\n")));
                responseData = http.request(
                        step.getRequestMethod(),
                        projectPath,
                        requestUrl,
                        step.getMultipartFormData(),
                        step.getFormDataList(),
                        requestHeaders,
                        project.getTestIdHeaderName(),
                        testId);
            }

            // Выполнить скрипт
            if (StringUtils.isNotEmpty(step.getScript())) {
                ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("js");
                scriptEngine.put("stepStatus", new StepStatus());
                scriptEngine.put("scenarioVariables", savedValues);
                scriptEngine.put("response", responseData);

                scriptEngine.eval(step.getScript());

                StepStatus stepStatus = (StepStatus) scriptEngine.get("stepStatus");
                if (StringUtils.isNotEmpty(stepStatus.getException())) {
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
        saveValuesByJsonXPath(step, responseData, savedValues);

        stepResult.setSavedParameters(savedValues.toString());

        // 4.1 Проверить сохраненные значения
        if (step.getSavedValuesCheck() != null) {
            for (Map.Entry<String, String> entry : step.getSavedValuesCheck().entrySet()) {
                String valueExpected = entry.getValue() == null ? "" : entry.getValue();
                for (Map.Entry<String, String> savedVal : savedValues.entrySet()) {
                    String key = String.format("%%%s%%", savedVal.getKey());
                    valueExpected = valueExpected.replaceAll(key, savedVal.getValue());
                }
                String valueActual = savedValues.get(entry.getKey());
                if (!valueExpected.equals(valueActual)) {
                    throw new Exception("Saved value " + entry.getKey() + " = " + valueActual + ". Expected: " + valueExpected);
                }
            }
        }

        // 5. Подставить сохраненые значения в ожидаемый результат
        String expectedResponse = insertSavedValues(step.getExpectedResponse(), savedValues);
        // 5.1. Расчитать выражения <f></f>
        expectedResponse = evaluateExpressions(expectedResponse);
        stepResult.setExpected(expectedResponse);

        // 6. Проверить код статуса ответа
        if ((step.getExpectedStatusCode() != null)
                && (step.getExpectedStatusCode() != responseData.getStatusCode())) {
            throw new Exception("Expected status code: " + step.getExpectedStatusCode() + ". Actual status code: " + responseData.getStatusCode());

        }

        if (!step.getExpectedResponseIgnore()) {
            if (step.getResponseCompareMode() == null) {
                JSONComparing(expectedResponse, responseData, step.getJsonCompareMode());
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
                        JSONComparing(expectedResponse, responseData, step.getJsonCompareMode());
                        break;
                }
            }
        }
    }

    private void JSONComparing(String expectedResponse, ResponseHelper responseData, String jsonCompareMode) throws Exception {
        if ((StringUtils.isNotEmpty(expectedResponse) || StringUtils.isNotEmpty(responseData.getContent())) &&
                (!responseData.getContent().equals(expectedResponse))) {
            try {
                JSONAssert.assertEquals(
                        expectedResponse == null ? "" : expectedResponse.replaceAll(" ", " "),
                        responseData.getContent().replaceAll(" ", " "), // Fix broken space in response
                        new IgnoringComparator(StringUtils.isEmpty(jsonCompareMode) ? JSONCompareMode.NON_EXTENSIBLE : JSONCompareMode.valueOf(jsonCompareMode))
                );
            } catch (Error assertionError) {
                throw new Exception(assertionError);
            }
        }
    }

    private void sendMessageToQuery(Project project, Step step, Map<String, String> savedValues) throws Exception {
        if (step.getMqName() != null && step.getMqMessage() != null) {
            if (project.getAmqpBroker() == null) {
                throw new Exception("AMQP broker is not configured in Project settings.");
            }
            IMqManager mqManager = MqManagerFactory.getMqManager(project.getAmqpBroker().getMqService());

            mqManager.setHost(project.getAmqpBroker().getHost());
            mqManager.setPort(project.getAmqpBroker().getPort());
            mqManager.setUsername(project.getAmqpBroker().getUsername());
            mqManager.setPassword(project.getAmqpBroker().getPassword());

            String message = insertSavedValues(step.getMqMessage(), savedValues);
            mqManager.sendTextMessage(step.getMqName(), message);
        }
    }

    private void saveValuesByJsonXPath(Step step, ResponseHelper responseData, Map<String, String> savedValues) {
        if (StringUtils.isNotEmpty(responseData.getContent())) {
            if (StringUtils.isNotEmpty(step.getJsonXPath())) {

                String lines[] = step.getJsonXPath().split("\\r?\\n");
                for (String line: lines) {
                    String[] lineParts = line.split("=", 2);
                    String parameterName = lineParts[0].trim();
                    String jsonXPath = lineParts[1];

                    // JsonPath.read(responseData.getContent(), "$.accountPortfolio[0].accountInfo.accountNumber");
                    savedValues.put(parameterName, JsonPath.read(responseData.getContent(), jsonXPath).toString());
                }
            }
        }
    }

    private void setMockResponses(WireMockAdmin wireMockAdmin, Project project, String testId, List<MockServiceResponse> responseList) throws IOException {
        Long priority = 0L;
        if (responseList != null && wireMockAdmin != null) {
            for (MockServiceResponse mockServiceResponse : responseList) {
                MockDefinition mockDefinition = new MockDefinition(priority--, project.getTestIdHeaderName(), testId);
                mockDefinition.getRequest().setUrl(mockServiceResponse.getServiceUrl());
                mockDefinition.getRequest().setMethod("POST"); // SOAP always POST
                mockDefinition.getResponse().setBody(mockServiceResponse.getResponseBody());
                mockDefinition.getResponse().setStatus(mockServiceResponse.getHttpStatus());
                mockDefinition.getResponse().getHeaders().put("Content-Type", "text/xml");

                wireMockAdmin.addMapping(mockDefinition);
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
            retry = true;
        }
        if (retry) {
            Thread.sleep(POLLING_RETRY_TIMEOUT_MS);
        }
        return retry;
    }

    private void executeSql(Connection connection, Step step, Map<String, String> savedValues) throws SQLException {
        if (StringUtils.isNotEmpty(step.getSql()) && StringUtils.isNotEmpty(step.getSqlSavedParameter()) && connection != null) {
            try (NamedParameterStatement statement = new NamedParameterStatement(connection, step.getSql()) ) {
                // Вставить в запрос параметры из savedValues, если они есть.
                for (Map.Entry<String, String> savedValue : savedValues.entrySet()) {
                    statement.setString(savedValue.getKey(), savedValue.getValue());
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
                            savedValues.put(parameterName.trim(), rs.getString(i));
                            i++;
                        }
                    }
                }
            }
        }
    }

    private String insertSavedValues(String template, Map<String, String> savedValues) {
        if (template != null) {
            for (Map.Entry<String, String> value : savedValues.entrySet()) {
                String key = String.format("%%%s%%", value.getKey());
                template = template.replaceAll(key, Matcher.quoteReplacement(value.getValue() == null ? "" : value.getValue()));
            }
        }
        return template;
    }

    private String insertSavedValuesToURL(String template, Map<String, String> savedValues) throws UnsupportedEncodingException {
        if (template != null) {
            for (Map.Entry<String, String> value : savedValues.entrySet()) {
                String key = String.format("%%%s%%", value.getKey());
                template = template.replaceAll(key, Matcher.quoteReplacement(URLEncoder.encode(value.getValue() == null ? "" : value.getValue(), "UTF-8")));
            }
        }
        return template;
    }

    private String evaluateExpressions(String template) throws ScriptException {
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

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    @Override
    public String toString() {
        return "AtExecutor{}";
    }
}
