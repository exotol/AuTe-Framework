package ru.bsc.test.at.executor.service;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import ru.bsc.test.at.executor.helper.HttpHelper;
import ru.bsc.test.at.executor.helper.NamedParameterStatement;
import ru.bsc.test.at.executor.helper.ResponseHelper;
import ru.bsc.test.at.executor.helper.ServiceRequestsComparatorHelper;
import ru.bsc.test.at.executor.model.MockServiceResponse;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.RequestBodyType;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepParameterSet;
import ru.bsc.test.at.executor.model.StepResult;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@SuppressWarnings("unused")
public class AtExecutor {

    private final static int POLLING_RETRY_COUNT = 50;
    private final static int POLLING_RETRY_TIMEOUT_MS = 1000;

    private ScenarioRepository scenarioRepository;
    private final ServiceRequestsComparatorHelper serviceRequestsComparatorHelper = new ServiceRequestsComparatorHelper();

    public AtExecutor() {
    }

    public AtExecutor(ScenarioRepository scenarioRepository) {
        this.scenarioRepository = scenarioRepository;
    }

    public List<Scenario> executeScenarioList(Project project, List<Scenario> scenarioExecuteList) {
        // Создать подключение к БД, которое будет использоваться сценарием для select-запросов.
        Set<Stand> standSet = new LinkedHashSet<>();
        // Собрать список всех используемых стендов выбранными сценариями
        standSet.addAll(scenarioExecuteList.stream()
                .filter(scenario -> scenario.getStand() != null)
                .map(Scenario::getStand)
                .collect(Collectors.toList()));
        if (project.getStand() != null) {
            standSet.add(project.getStand());
        }
        Map<Stand, Connection> standConnectionMap = new HashMap<>();
        // Создать подключение для каждого используемого стенда
        for (Stand stand: standSet) {
            Connection connection = null;
            if (StringUtils.isNotEmpty(stand.getDbUrl())) {
                try {
                    connection = DriverManager.getConnection(stand.getDbUrl(), stand.getDbUser(), stand.getDbPassword());
                    connection.setAutoCommit(false);
                    connection.setReadOnly(true);
                } catch (SQLException e) {
                    connection = null;
                }
            }
            standConnectionMap.put(stand, connection);
        }

        List<Scenario> scenarioResultList = new LinkedList<>();
        try {
            for (Scenario scenario : scenarioExecuteList) {
                Stand currentStand = scenario.getStand() != null ? scenario.getStand() : project.getStand();
                scenarioResultList.add(executeScenario(project, scenario, currentStand, standConnectionMap.get(currentStand)));
            }
        } finally {
            standConnectionMap.values().stream().filter(Objects::nonNull).forEach(connection -> {
                try {
                    connection.rollback();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
        return scenarioResultList;
    }

    private Scenario executeScenario(Project project, Scenario scenario, Stand stand, Connection connection) {
        HttpHelper httpHelper = new HttpHelper();
        Map<String, String> savedValues = new HashMap<>();
        savedValues.put("__random", RandomStringUtils.randomAlphabetic(40));

        scenario.setStepResults(new LinkedList<>());
        // перед выполнением каждого сценария выполнять предварительный сценарий, заданный в свойствах проекта (например, сценарий авторизации)
        Scenario beforeScenario = scenario.getBeforeScenarioIgnore() ? null : scenario.getBeforeScenario() == null ? project.getBeforeScenario() : scenario.getBeforeScenario();
        if (beforeScenario != null) {
            beforeScenario.setStepResults(new LinkedList<>());
            executeSteps(connection, stand, beforeScenario, project, httpHelper, savedValues);
            scenario.getStepResults().addAll(beforeScenario.getStepResults());
        }

        Scenario scenarioResult = executeSteps(connection, stand, scenario, project, httpHelper, savedValues);

        // После выполнения сценария выполнить сценарий, заданный в проекте или в сценарии
        Scenario afterScenario = scenario.getAfterScenarioIgnore() ? null : scenario.getAfterScenario() == null ? project.getAfterScenario() : scenario.getAfterScenario();
        if (afterScenario != null) {
            afterScenario.setStepResults(new LinkedList<>());
            executeSteps(connection, stand, afterScenario, project, httpHelper, savedValues);
            scenario.getStepResults().addAll(afterScenario.getStepResults());
        }

        httpHelper.closeHttpConnection();

        return scenarioResult;
    }

    private Scenario executeSteps(Connection connection, Stand stand, Scenario scenario, Project project, HttpHelper httpHelper, Map<String, String> savedValues) {
        if (scenario != null) {
            int failures = 0;
            for (Step step: scenario.getSteps()) {
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
                        scenario.getStepResults().add(stepResult);

                        if (stepParameterSet.getStepParameterList() != null) {
                            stepParameterSet.getStepParameterList()
                                    .forEach(stepParameter -> savedValues.put(stepParameter.getName(), stepParameter.getValue()));
                            stepResult.setDescription(stepParameterSet.getDescription());
                        }
                        try (WireMockAdmin wireMockAdmin = StringUtils.isNotEmpty(stand.getWireMockUrl()) ? new WireMockAdmin(stand.getWireMockUrl() + "/__admin") : null) {
                            String testId = project.getUseRandomTestId() ? UUID.randomUUID().toString() : "-";
                            stepResult.setTestId(testId);
                            executeTestStep(wireMockAdmin, connection, stand, httpHelper, savedValues, testId, project, step, stepResult);

                            // После выполнения шага необходимо проверить запросы к веб-сервисам
                            serviceRequestsComparatorHelper.assertTestCaseWSRequests(project, wireMockAdmin, testId, step);

                            stepResult.setResult("OK");
                        } catch (Exception e) {
                            StringWriter sw = new StringWriter();
                            e.printStackTrace(new PrintWriter(sw));

                            stepResult.setResult("Fail");
                            stepResult.setDetails(sw.toString().substring(0, Math.min(sw.toString().length(), 10000)));
                            failures++;
                        }
                    }
                }
            }

            scenario.setLastRunAt(Calendar.getInstance().getTime());
            scenario.setLastRunFailures(failures);
            if (scenarioRepository != null) {
                scenarioRepository.save(scenario);
            }
        }
        return scenario;
    }

    private void executeTestStep(WireMockAdmin wireMockAdmin, Connection connection, Stand stand, HttpHelper http, Map<String, String> savedValues, String testId, Project project, Step step, StepResult stepResult) throws Exception {

        stepResult.setSavedParameters(savedValues.toString());

        // 0. Установить ответы сервисов, которые будут использоваться в SoapUI для определения ответа
        setMockResponses(wireMockAdmin, project, testId, step.getMockServiceResponseList());

        // 1. Выполнить запрос БД и сохранить полученные значения
        executeSql(connection, step, savedValues);
        stepResult.setSavedParameters(savedValues.toString());

        // 2. Подстановка сохраненных параметров в строку запроса
        String requestUrl = stand.getServiceUrl() + insertSavedValuesToURL(step.getRelativeUrl(), savedValues);
        stepResult.setRequestUrl(requestUrl);

        // 2.1 Подстановка сохраненных параметров в тело запроса
        String requestBody = insertSavedValues(step.getRequest(), savedValues);

        // 2.2 Вычислить функции в теле запроса
        requestBody = evaluateExpressions(requestBody);
        stepResult.setRequestBody(requestBody);

        int retryCounter = 0;
        boolean retry;
        ResponseHelper responseData;
        do {
            retryCounter++;
            responseData = http.request(
                    step.getRequestMethod(),
                    requestUrl,
                    RequestBodyType.FORM.equals(step.getRequestBodyType()) ? null : requestBody,
                    RequestBodyType.FORM.equals(step.getRequestBodyType()) ? parseFormData(requestBody) : null,
                    step.getRequestHeaders(),
                    project.getTestIdHeaderName(),
                    testId);
            retry = tryUsePolling(step, responseData);
        } while (retry && retryCounter <= POLLING_RETRY_COUNT);

        stepResult.setPollingRetryCount(retryCounter);
        stepResult.setActual(responseData.getContent());
        stepResult.setExpected(step.getExpectedResponse());

        // 4. Сохранить полученные значения
        saveValuesFromResponse(step.getSavingValues(), responseData.getContent(), savedValues);
        saveValuesByJsonXPath(step, responseData, savedValues);

        stepResult.setSavedParameters(savedValues.toString());

        // 4.1 Проверить сохраненные значения
        for(Map.Entry<String, String> entry: step.getSavedValuesCheck().entrySet()) {
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
                JSONcomparing(step, expectedResponse, responseData);
            } else {
                switch (step.getResponseCompareMode()) {
                    case FULL_MATCH:
                        Assert.assertEquals(expectedResponse, responseData.getContent());
                        break;
                    case IGNORE_MASK:
                        if (!MaskComparator.compare(expectedResponse, responseData.getContent())) {
                            throw new Exception("\nExpected value: " + expectedResponse + ".\nActual value: " + responseData.getContent());
                        }
                        break;
                    default:
                        JSONcomparing(step, expectedResponse, responseData);
                        break;
                }
            }
        }
    }

    private void JSONcomparing(Step step, String expectedResponse, ResponseHelper responseData) throws Exception {
        if ((StringUtils.isNotEmpty(expectedResponse) || StringUtils.isNotEmpty(responseData.getContent())) &&
                (!responseData.getContent().equals(expectedResponse))) {
            try {
                JSONAssert.assertEquals(
                        expectedResponse.replaceAll(" ", " "),
                        responseData.getContent().replaceAll(" ", " "), // Fix broken space in response
                        new IgnoringComparator(JSONCompareMode.LENIENT)
                );
            } catch (Error assertionError) {
                throw new Exception(assertionError);
            }
        }
    }

    private Map<String, String> parseFormData(String formDataString) {
        Map<String, String> formDataMap = new HashMap<>();
        if (StringUtils.isNotEmpty(formDataString)) {
            for (String line : formDataString.split("\\r?\\n")) {
                String[] lineParts = line.split("=", 2);
                formDataMap.put(lineParts[0].trim(), lineParts[1].trim());
            }
        }
        return formDataMap;
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
                mockDefinition.getResponse().setHeaders(new HashMap<String, String>() {{
                    put("Content-Type", "text/xml");
                }});

                wireMockAdmin.addMapping(mockDefinition);
            }
        }
    }

    private boolean tryUsePolling(Step step, ResponseHelper responseData) throws InterruptedException {
        boolean retry = true;
        if (step.getUsePolling()) {
            try {
                Object pollingParameter = JsonPath.read(responseData.getContent(), step.getPollingJsonXPath());
                if (pollingParameter == null
                        || pollingParameter instanceof String && StringUtils.isEmpty((String) pollingParameter)
                        || pollingParameter instanceof Map && ((Map) pollingParameter).isEmpty()
                        || pollingParameter instanceof JSONArray && ((JSONArray) pollingParameter).isEmpty()) {
                    retry = false;
                }
            } catch (PathNotFoundException e) {
                retry = true;
            }
            if (retry) {
                Thread.sleep(POLLING_RETRY_TIMEOUT_MS);
            }
        }
        return retry;
    }

    @SuppressWarnings("WeakerAccess")
    protected class ServiceNameResponsePair {
        private String serviceName;
        private String response;

        ServiceNameResponsePair(String serviceName, String response) {
            this.serviceName = serviceName;
            this.response = response;
        }

        public String getServiceName() {
            return serviceName;
        }

        String getResponse() {
            return response;
        }
    }

    @SuppressWarnings("WeakerAccess")
    protected List<ServiceNameResponsePair> parseServiceResponsesString(String responses) {
        List<ServiceNameResponsePair> result = new LinkedList<>();
        if (responses != null) {
            for (String service : responses.split(";")) {
                String[] serviceData = service.split(":");
                // TODO что-то сделать с неправильным форматом
                if (serviceData.length == 2) {
                    String serviceName = serviceData[0].trim();
                    for (String response : serviceData[1].split(",")) {
                        result.add(new ServiceNameResponsePair(serviceName, response.trim()));
                    }
                }
            }
        }
        return result;
    }

    private void saveValuesFromResponse(String values, String response, Map<String, String> savedValues) {
        if (StringUtils.isNotEmpty(values)) {
            List<String> valuesList = Arrays.asList(values.split(","));
            for (String value : valuesList) {
                if (!value.isEmpty()) {
                    Pattern p = Pattern.compile(String.format(".*%s.*", value.trim()), Pattern.DOTALL);
                    Matcher m = p.matcher(response);

                    if (m.matches()) {
                        String savedValue = response.split(String.format("%s\":", value.trim()), 2)[1].trim().split(",|}", 2)[0];
                        savedValues.put(value.trim(), savedValue);
                    }
                }
            }
        }
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
}