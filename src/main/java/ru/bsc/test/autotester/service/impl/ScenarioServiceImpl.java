package ru.bsc.test.autotester.service.impl;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsc.test.autotester.helper.HttpHelper;
import ru.bsc.test.autotester.helper.NamedParameterStatement;
import ru.bsc.test.autotester.helper.ResponseHelper;
import ru.bsc.test.autotester.helper.ServiceRequestsComparatorHelper;
import ru.bsc.test.autotester.model.ExpectedServiceRequest;
import ru.bsc.test.autotester.model.Project;
import ru.bsc.test.autotester.model.Scenario;
import ru.bsc.test.autotester.model.ServiceResponse;
import ru.bsc.test.autotester.model.Step;
import ru.bsc.test.autotester.model.StepResult;
import ru.bsc.test.autotester.repository.ProjectRepository;
import ru.bsc.test.autotester.repository.ScenarioRepository;
import ru.bsc.test.autotester.repository.ServiceResponseRepository;
import ru.bsc.test.autotester.service.ExpectedServiceRequestService;
import ru.bsc.test.autotester.service.ScenarioService;
import ru.bsc.test.autotester.service.StepService;
import ru.bsc.test.autotester.validation.IgnoringComparator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
public class ScenarioServiceImpl implements ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final StepService stepService;
    private final ServiceResponseRepository serviceResponseRepository;
    private final ProjectRepository projectRepository;
    private final ExpectedServiceRequestService expectedServiceRequestService;
    private final ServiceRequestsComparatorHelper serviceRequestsComparatorHelper;

    @Autowired
    public ScenarioServiceImpl(ScenarioRepository scenarioRepository, StepService stepService, ServiceResponseRepository serviceResponseRepository, ProjectRepository projectRepository, ExpectedServiceRequestService expectedServiceRequestService, ServiceRequestsComparatorHelper serviceRequestsComparatorHelper) {
        this.scenarioRepository = scenarioRepository;
        this.stepService = stepService;
        this.serviceResponseRepository = serviceResponseRepository;
        this.projectRepository = projectRepository;
        this.expectedServiceRequestService = expectedServiceRequestService;
        this.serviceRequestsComparatorHelper = serviceRequestsComparatorHelper;
    }

    @Override
    public List<Scenario> findAllByProjectId(Long projectId) {
        return scenarioRepository.findAllByProjectIdOrderByScenarioGroupIdDescNameAsc(projectId);
    }

    @Override
    public List<Scenario> findAllByProjectIdAndScenarioGroupId(Long projectId, Long scenarioGroupId) {
        return scenarioRepository.findAllByProjectIdAndScenarioGroupIdOrderByScenarioGroupIdDescNameAsc(projectId, scenarioGroupId);
    }

    @Override
    public List<Scenario> executeScenarioList(Long[] scenarios) {
        List<Scenario> scenarioResultList = new LinkedList<>();
        for (long scenarioId: scenarios) {
            scenarioResultList.add(executeScenario(scenarioId));
        }
        return scenarioResultList;
    }

    private Scenario executeScenario(Long scenarioId) {
        Scenario scenario = scenarioRepository.findOne(scenarioId);
        Project project = projectRepository.findOne(scenario.getProjectId());

        HttpHelper httpHelper = new HttpHelper();
        Map<String, String> savedValues = new HashMap<>();
        String sessionUid = UUID.randomUUID().toString();
        // TODO Сделать этот кейс опциональным. Пока хедеры не прокидываются тестируемым порталом, уникальный ID теста не передается.
        sessionUid = "-";


        // TODO Создать подключение к БД, которое будет использоваться сценарием для select-запросов.
        Connection connection = null;
        if (project.getDbUrl() != null) {
            try {
                connection = DriverManager.getConnection(project.getDbUrl(), project.getDbUser(), project.getDbPassword());
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                connection = null;
            }
        }

        try {
            // перед выполнением каждого сценария выполнять предварительный сценарий, заданный в свойствах проекта (например, сценарий авторизации)
            Long beforeScenarioId = scenario.getBeforeScenarioId() == null ? project.getBeforeScenarioId() : (scenario.getBeforeScenarioId() < 0 ? null : scenario.getBeforeScenarioId());
            if (beforeScenarioId != null) {
                Scenario beforeScenario = scenarioRepository.findOne(beforeScenarioId);
                executeSteps(connection, beforeScenario, project, httpHelper, savedValues, sessionUid);
                scenario.getStepResults().addAll(beforeScenario.getStepResults());
            }

            Scenario scenarioResult = executeSteps(connection, scenario, project, httpHelper, savedValues, sessionUid);

            // После выполнения сценария выполнить сценарий, заданный в проекте или в сценарии
            Long afterScenarioId = scenario.getAfterScenarioId() == null ? project.getAfterScenarioId() : (scenario.getAfterScenarioId() < 0 ? null : scenario.getAfterScenarioId());
            if (afterScenarioId != null) {
                Scenario afterScenario = scenarioRepository.findOne(afterScenarioId);
                executeSteps(connection, afterScenario, project, httpHelper, savedValues, sessionUid);
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

    private Scenario executeSteps(Connection connection, Scenario scenario, Project project, HttpHelper httpHelper, Map<String, String> savedValues, String sessionUid) {
        if (scenario != null) {
            int failures = 0;
            for (Step step : stepService.findAllByScenarioId(scenario.getId())) {
                StepResult stepResult = new StepResult(step);
                scenario.getStepResults().add(stepResult);
                try {
                    executeTestStep(connection, httpHelper, savedValues, sessionUid, project, step, stepResult);

                    // TODO После выполнения шага необходимо проверить запросы к веб-сервисам
                    serviceRequestsComparatorHelper.assertTestCaseWSRequests(sessionUid, step);

                    stepResult.setResult("OK");
                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));

                    stepResult.setResult("Fail");
                    stepResult.setDetails(sw.toString());
                    failures++;
                }
            }

            scenario.setLastRunAt(Calendar.getInstance().getTime());
            scenario.setLastRunFailures(failures);
            save(scenario);
        }
        return scenario;
    }

    @Override
    public Scenario save(Scenario scenario) {
        return scenarioRepository.save(scenario);
    }

    @Override
    public Scenario findOne(long scenarioId) {
        return scenarioRepository.findOne(scenarioId);
    }

    @Override
    public void delete(Scenario scenario) {
        scenarioRepository.delete(scenario);
    }

    private void executeTestStep(Connection connection, HttpHelper http, Map<String, String> savedValues, String sessionUid, Project project, Step step, StepResult stepResult) throws Exception {
        setResponses(project, sessionUid, step.getResponses());

        if (step.getSql() != null && connection != null) {
            try (NamedParameterStatement statement = new NamedParameterStatement(connection, step.getSql()) ) {
                // Вставить в запрос параметры из savedValues, если они есть.
                for (Map.Entry<String, String> savedValue : savedValues.entrySet()) {
                    statement.setString(savedValue.getKey(), savedValue.getValue());
                }
                try (ResultSet rs = statement.executeQuery()) {
                    // TODO Предусмотреть возможность сохранения не одного, а нескольких значений из запроса.
                    if (rs.next()) {
                        savedValues.put(step.getSqlSavedParameter(), rs.getString(1));
                    }
                }
            }
        }

        // Подстановка сохраненных параметров в строку запроса
        String requestUrl = insertSavedValues(step.getRelativeUrl(), savedValues);
        stepResult.setRequestUrl(requestUrl);

        ResponseHelper responseData = http.request(
                step.getRequestMethod(),
                project.getServiceUrl() + requestUrl,
                insertSavedValues(step.getRequest(), savedValues),
                step.getRequestHeaders(),
                sessionUid);
        saveValuesFromResponse(step.getSavingValues(), responseData.getContent(), savedValues);
        String expectedResponse = insertSavedValues(step.getExpectedResponse(), savedValues);
        stepResult.setActual(responseData.getContent());
        stepResult.setExpected(expectedResponse);

        // Check status code
        if (step.getExpectedStatusCode() != null) {
            if (step.getExpectedStatusCode() != responseData.getStatusCode()) {
                throw new Exception("Expected status code: " + step.getExpectedStatusCode() + ". Actual status code: " + responseData.getStatusCode());
            }
        }

        // Compare json response with expected response
        if (!emptyString(step.getExpectedResponse()) || !emptyString(responseData.getContent())) {
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

    private boolean emptyString(String string) {
        return string == null || string.isEmpty();
    }

    private void setResponses(Project project, String sessionUid, String responses) {
        Long sort = 0L;
        List<ServiceNameResponsePair> responsesList = parseServiceResponsesString(responses);
        responsesList.stream().map(ServiceNameResponsePair::getServiceName).collect(Collectors.toSet())
                .forEach(item -> serviceResponseRepository.deleteByServiceNameAndProjectCode(item, project.getProjectCode()));

        for (ServiceNameResponsePair pair: responsesList) {
            serviceResponseRepository.save(new ServiceResponse(sessionUid, pair.getServiceName(), pair.getResponse(), sort++, project.getProjectCode()));
        }
    }

    private class ServiceNameResponsePair {
        private String serviceName;
        private String response;

        ServiceNameResponsePair(String serviceName, String response) {
            this.serviceName = serviceName;
            this.response = response;
        }

        String getServiceName() {
            return serviceName;
        }

        String getResponse() {
            return response;
        }
    }

    private List<ServiceNameResponsePair> parseServiceResponsesString(String responses) {
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

    @Override
    public void parseExpectedServiceRequestsJmba(String expectedRequestsBaseDir, Scenario scenario) {
        // Костыли для импорта запросов к сервису из конкретного проекта (jbma)

        List<Step> stepList = stepService.findAllByScenarioId(scenario.getId());
        for (Step step: stepList) {
            Long i = 0L;
            String testCaseDir = step.getTmpServiceRequestsDirectory();
            if (testCaseDir != null && !testCaseDir.isEmpty()) {



                List<ServiceNameResponsePair> servicePairs = parseServiceResponsesString(step.getResponses());
                for (ServiceNameResponsePair pair : servicePairs) {
                    File serviceRequestDirectory = new File(expectedRequestsBaseDir + '\\' + testCaseDir + '\\' + pair.getServiceName());
                    if (serviceRequestDirectory.exists()) {

                        for (File serviceRequestFile : serviceRequestDirectory.listFiles(pathname -> pathname != null && pathname.getName() != null && pathname.getName().endsWith(".xml"))) {
                            if (!serviceRequestFile.isDirectory()) {
                                try {
                                    expectedServiceRequestService.save(new ExpectedServiceRequest(
                                            step.getId(),
                                            pair.getServiceName(),
                                            new String(Files.readAllBytes(Paths.get(serviceRequestFile.getPath())), StandardCharsets.UTF_8),
                                            50 * i++
                                    ));

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    private void saveValuesFromResponse(String values, String response, Map<String, String> savedValues) {
        if (values != null) {
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

    private String insertSavedValues(String template, Map<String, String> savedValues) {
        if (template != null) {
            for (Map.Entry<String, String> value : savedValues.entrySet()) {
                String key = String.format("%%%s%%", value.getKey());
                template = template.replaceAll(key, value.getValue());
            }
        }
        return template;
    }
}
