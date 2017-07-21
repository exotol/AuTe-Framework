package ru.bsc.test.at.executor.service;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import ru.bsc.test.at.executor.helper.HttpHelper;
import ru.bsc.test.at.executor.helper.NamedParameterStatement;
import ru.bsc.test.at.executor.helper.ResponseHelper;
import ru.bsc.test.at.executor.helper.ServiceRequestsComparatorHelper;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.ServiceResponse;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.validation.IgnoringComparator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
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

    private final ScenarioRepository scenarioRepository;
    private final ServiceResponseRepository serviceResponseRepository;

    private final ServiceRequestsComparatorHelper serviceRequestsComparatorHelper;

    public AtExecutor(ScenarioRepository scenarioRepository, ServiceResponseRepository serviceResponseRepository) {
        this.scenarioRepository = scenarioRepository;
        this.serviceResponseRepository = serviceResponseRepository;
        this.serviceRequestsComparatorHelper = new ServiceRequestsComparatorHelper(serviceResponseRepository);
    }

    public List<Scenario> executeScenarioList(Project project, List<Scenario> scenarioExecuteList) {
        // Создать подключение к БД, которое будет использоваться сценарием для select-запросов.
        Set<Stand> standSet = new LinkedHashSet<>();
        // Собрать список всех используемых стендов выбранными сценариями
        for (Scenario scenario: scenarioExecuteList) {
            if (scenario.getStand() != null) {
                standSet.add(scenario.getStand());
            }
        }
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
                } catch (SQLException e) {
                    connection = null;
                }
            }
            standConnectionMap.put(stand, connection);
        }

        List<Scenario> scenarioResultList = new LinkedList<>();
        for (Scenario scenario: scenarioExecuteList) {
            Stand currentStand = scenario.getStand() != null ? scenario.getStand() : project.getStand();
            scenarioResultList.add(executeScenario(project, scenario, currentStand, standConnectionMap.get(currentStand)));
        }
        return scenarioResultList;
    }

    private Scenario executeScenario(Project project, Scenario scenario, Stand stand, Connection connection) {
        HttpHelper httpHelper = new HttpHelper();
        Map<String, String> savedValues = new HashMap<>();
        String testId = project.getUseRandomTestId() ? UUID.randomUUID().toString() : "-";

        try {
            scenario.setStepResults(new LinkedList<>());
            // перед выполнением каждого сценария выполнять предварительный сценарий, заданный в свойствах проекта (например, сценарий авторизации)
            Scenario beforeScenario = scenario.getBeforeScenarioIgnore() ? null : scenario.getBeforeScenario() == null ? project.getBeforeScenario() : scenario.getBeforeScenario();
            if (beforeScenario != null) {
                beforeScenario.setStepResults(new LinkedList<>());
                executeSteps(connection, stand, beforeScenario, project, httpHelper, savedValues, testId);
                scenario.getStepResults().addAll(beforeScenario.getStepResults());
            }

            Scenario scenarioResult = executeSteps(connection, stand, scenario, project, httpHelper, savedValues, testId);

            // После выполнения сценария выполнить сценарий, заданный в проекте или в сценарии
            Scenario afterScenario = scenario.getAfterScenarioIgnore() ? null : scenario.getAfterScenario() == null ? project.getAfterScenario() : scenario.getAfterScenario();
            if (afterScenario != null) {
                afterScenario.setStepResults(new LinkedList<>());
                executeSteps(connection, stand, afterScenario, project, httpHelper, savedValues, testId);
                scenario.getStepResults().addAll(afterScenario.getStepResults());
            }

            httpHelper.closeHttpConnection();

            return scenarioResult;

        } finally {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Scenario executeSteps(Connection connection, Stand stand, Scenario scenario, Project project, HttpHelper httpHelper, Map<String, String> savedValues, String testId) {
        if (scenario != null) {
            int failures = 0;
            for (Step step: scenario.getSteps()) {
                StepResult stepResult = new StepResult(step);
                scenario.getStepResults().add(stepResult);
                try {
                    executeTestStep(connection, stand, httpHelper, savedValues, testId, project, step, stepResult);

                    // После выполнения шага необходимо проверить запросы к веб-сервисам
                    serviceRequestsComparatorHelper.assertTestCaseWSRequests(testId, step);

                    stepResult.setResult("OK");
                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));

                    stepResult.setResult("Fail");
                    stepResult.setDetails(sw.toString().substring(0, Math.min(sw.toString().length(), 10000)));
                    failures++;
                }
            }

            scenario.setLastRunAt(Calendar.getInstance().getTime());
            scenario.setLastRunFailures(failures);
            scenarioRepository.save(scenario);
        }
        return scenario;
    }

    private void executeTestStep(Connection connection, Stand stand, HttpHelper http, Map<String, String> savedValues, String testId, Project project, Step step, StepResult stepResult) throws Exception {

        // 0. Установить ответы сервисов, которые будут использоваться в SoapUI для определения ответа
        setResponses(project, testId, step.getResponses());

        // 1. Выполнить запрос БД и сохранить полученные значения
        executeSql(connection, step, savedValues);

        // 2. Подстановка сохраненных параметров в строку запроса
        String requestUrl = stand.getServiceUrl() + insertSavedValuesToURL(step.getRelativeUrl(), savedValues);
        stepResult.setRequestUrl(requestUrl);

        // 2.1 Подстановка сохраненных параметров в тело запроса
        String requestBody = insertSavedValues(step.getRequest(), savedValues);
        stepResult.setRequestBody(requestBody);

        int retryCounter = 0;
        boolean retry;
        ResponseHelper responseData;
        do {
            retryCounter++;
            // 3. Выполнить запрос
            responseData = http.request(
                    step.getRequestMethod(),
                    requestUrl,
                    Step.RequestBodyType.FORM.equals(step.getRequestBodyType()) ? null : requestBody,
                    Step.RequestBodyType.FORM.equals(step.getRequestBodyType()) ? parseFormData(requestBody) : null,
                    step.getRequestHeaders(),
                    project.getTestIdHeaderName(),
                    testId);

            // 3.1. Polling
            retry = false;
            if (step.getUsePolling()) {
                try {
                    Object pollingParameter = JsonPath.read(responseData.getContent(), step.getPollingJsonXPath());
                    if (pollingParameter == null) {
                        retry = true;
                    } else if (pollingParameter instanceof String && StringUtils.isEmpty((String)pollingParameter)) {
                        retry = true;
                    } else if (pollingParameter instanceof Map && ((Map) pollingParameter).size() == 0) {
                        retry = true;
                    } else if (pollingParameter instanceof JSONArray && ((JSONArray) pollingParameter).size() == 0) {
                        retry = true;
                    }
                } catch (PathNotFoundException e) {
                    retry = true;
                }
                if (retry) {
                    Thread.sleep(POLLING_RETRY_TIMEOUT_MS);
                }
            }
        } while (retry && retryCounter <= POLLING_RETRY_COUNT);

        stepResult.setPollingRetryCount(retryCounter);
        stepResult.setActual(responseData.getContent());
        stepResult.setExpected(step.getExpectedResponse());

        // 4. Сохранить полученные значения
        saveValuesFromResponse(step.getSavingValues(), responseData.getContent(), savedValues);
        saveValuesByJsonXPath(step, responseData, savedValues);

        stepResult.setSavedParameters(savedValues.toString());

        // 5. Подставить сохраненые значения в ожидаемый результат
        String expectedResponse = insertSavedValues(step.getExpectedResponse(), savedValues);
        // 5.1. Расчитать выражения <f></f>
        expectedResponse = evaluateExpressions(expectedResponse);
        stepResult.setExpected(expectedResponse);

        // 6. Проверить код статуса ответа
        if (step.getExpectedStatusCode() != null) {
            if (step.getExpectedStatusCode() != responseData.getStatusCode()) {
                throw new Exception("Expected status code: " + step.getExpectedStatusCode() + ". Actual status code: " + responseData.getStatusCode());
            }
        }

        // 7. Сравнить JSON ответ с ожидаемым
        if (!step.getExpectedResponseIgnore()) {
            if (StringUtils.isNotEmpty(expectedResponse) || StringUtils.isNotEmpty(responseData.getContent())) {
                // Если содержимое ответов не совпадает, то выявить разницу с помощью JSONAssert
                if (!responseData.getContent().equals(expectedResponse)) {
                    try {
                        JSONAssert.assertEquals(
                                expectedResponse,
                                responseData.getContent().replaceAll(" ", " "), // Fix broken space in response
                                new IgnoringComparator(JSONCompareMode.LENIENT)
                        );
                    } catch (Error assertionError) {
                        throw new Exception(assertionError);
                    }
                }
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

    private void setResponses(Project project, String testId, String responses) {
        Long sort = 0L;
        List<ServiceNameResponsePair> responsesList = parseServiceResponsesString(responses);
        responsesList.stream().map(ServiceNameResponsePair::getServiceName).collect(Collectors.toSet())
                .forEach(item -> serviceResponseRepository.deleteByServiceNameAndProjectCode(item, project.getProjectCode()));

        for (ServiceNameResponsePair pair: responsesList) {
            serviceResponseRepository.save(new ServiceResponse(testId, pair.getServiceName(), pair.getResponse(), sort++, project.getProjectCode()));
        }
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
                System.out.println(m.group(1));
                ScriptEngineManager manager = new ScriptEngineManager();
                ScriptEngine engine = manager.getEngineByName("js");
                Object result = engine.eval(m.group(1));
                template = template.replace("<f>" + m.group(1) + "</f>", Matcher.quoteReplacement(String.valueOf(result)));
            }
        }
        return template;
    }
}
