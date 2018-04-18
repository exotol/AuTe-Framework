package ru.bsc.test.at.executor.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import ru.bsc.test.at.executor.ei.mqmocker.MqMockerAdmin;
import ru.bsc.test.at.executor.ei.wiremock.WireMockAdmin;
import ru.bsc.test.at.executor.exception.ScenarioStopException;
import ru.bsc.test.at.executor.helper.HttpClient;
import ru.bsc.test.at.executor.helper.MqClient;
import ru.bsc.test.at.executor.helper.MqMockHelper;
import ru.bsc.test.at.executor.helper.ServiceRequestsComparatorHelper;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepParameterSet;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.step.executor.AbstractStepExecutor;
import ru.bsc.test.at.executor.step.executor.IStepExecutor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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
        HttpClient httpClient = new HttpClient();
        Map<String, Object> scenarioVariables = new HashMap<>();
        scenarioVariables.put("__random", RandomStringUtils.randomAlphabetic(40));

        try (
                MqClient mqClient = project.getAmqpBroker() != null ? new MqClient(project.getAmqpBroker()) : null
                ) {
            // перед выполнением каждого сценария выполнять предварительный сценарий, заданный в свойствах проекта (например, сценарий авторизации)
            Scenario beforeScenario = scenario.getBeforeScenarioIgnore() ? null : findScenarioByPath(project.getBeforeScenarioPath(), project.getScenarioList());
            if (beforeScenario != null) {
                executeSteps(connection, stand, beforeScenario.getStepList(), project, httpClient, mqClient, scenarioVariables, stepResultList, false, stopObserver);
            }

            executeSteps(connection, stand, scenario.getStepList(), project, httpClient, mqClient, scenarioVariables, stepResultList, true, stopObserver);

            // После выполнения сценария выполнить сценарий, заданный в проекте или в сценарии
            Scenario afterScenario = scenario.getAfterScenarioIgnore() ? null : findScenarioByPath(project.getAfterScenarioPath(), project.getScenarioList());
            if (afterScenario != null) {
                executeSteps(connection, stand, afterScenario.getStepList(), project, httpClient, mqClient, scenarioVariables, stepResultList, false, stopObserver);
            }

        } catch (ScenarioStopException | InterruptedException e) {
            // Stop scenario executing
            log.error("Error during scenario execution", e);
        } catch (Exception e) {
            log.error("Error during MqClient get connection", e);
        }

        httpClient.closeHttpConnection();
    }

    private Scenario findScenarioByPath(String path, List<Scenario> scenarioList) {
        log.debug("findScenarioByPath {}", path);
        if (path == null) {
            log.warn("findScenarioByPath: path is null, return null");
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
            log.warn("findScenarioByPath: NOT FOUND!");
            return null;
        }

        Scenario result = scenarioList.stream()
                .filter(scenario -> Objects.equals(scenario.getCode(), scenarioCode))
                .filter(scenario -> scenarioGroupCode == null || Objects.equals(scenarioGroupCode, scenario.getScenarioGroup()))
                .findAny()
                .orElse(null);
        log.debug("findScenarioByPath result {}", result);
        return result;
    }

  public static long parseLongOrVariable(Map<String, Object> scenarioVariables, String value, long defaultValue) {
        log.debug("parseLongOrVariable {}, {}, {}", scenarioVariables, value, defaultValue);
        long result;
        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            try {
                result = Integer.parseInt(String.valueOf(scenarioVariables.get(value)));
            } catch (NumberFormatException ex) {
                log.warn("parseLongOrVariable: got error! Take the default value", ex);
                result = defaultValue;
            }
        }
        log.debug("parseLongOrVariable result {}", result);
        return result;
    }

    private void executeSteps(Connection connection, Stand stand, List<Step> stepList, Project project, HttpClient httpClient, MqClient mqClient,
                              Map<String, Object> scenarioVariables, List<StepResult> stepResultList,
                              boolean stepEditable, IStopObserver stopObserver) throws ScenarioStopException, InterruptedException {
        log.debug("executeSteps {}, {}, {}, {}, {}, {}, {}", stand, stepList, project, httpClient, scenarioVariables, stepResultList, stepEditable);
        if (stepList == null) {
            log.warn("executeSteps got empty stepList");
            return;
        }
        List<IStepExecutor> stepExecutorList = IStepExecutor.getStepExecutorList();
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
                    StepResult stepResult = new StepResult(project.getCode(), step);
                    stepResult.setStart(new Date().getTime());
                    stepResult.setEditable(stepEditable);
                    stepResultList.add(stepResult);

                    // COM-123 Timeout
                    if (step.getTimeoutMs() != null) {
                        long timeout = AbstractStepExecutor.parseLongOrVariable(scenarioVariables, step.getTimeoutMs(), 0);
                        if (timeout > 0) {
                            Thread.sleep(Math.min(timeout, 60000L));
                        }
                    }

                    if (stepParameterSet.getStepParameterList() != null) {
                        stepParameterSet.getStepParameterList()
                                .forEach(stepParameter -> scenarioVariables.put(stepParameter.getName().trim(), stepParameter.getValue()));
                        stepResult.setDescription(stepParameterSet.getDescription());
                    }
                    try (
                            WireMockAdmin wireMockAdmin = stand != null && isNotEmpty(stand.getWireMockUrl()) ? new WireMockAdmin(stand.getWireMockUrl() + "/__admin") : null;
                            MqMockerAdmin mqMockerAdmin = stand != null && isNotEmpty(stand.getMqMockUrl()) ? new MqMockerAdmin(stand.getMqMockUrl() + "/__admin") : null
                    ) {
                        if (stand == null) {
                            log.error("Stand is not configured");
                            throw new Exception("Stand is not configured.");
                        }
                        String testId = project.getUseRandomTestId() ? UUID.randomUUID().toString() : "-";
                        stepResult.setTestId(testId);

                        for (IStepExecutor stepExecutor : stepExecutorList) {
                            if (stepExecutor.support(step)) {
                                stepResult.setSavedParameters(scenarioVariables.toString());
                                stepExecutor.execute(wireMockAdmin, mqMockerAdmin, connection, stand, httpClient, mqClient, scenarioVariables, testId, project, step, stepResult, projectPath);
                                break;
                            }
                        }

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
}
