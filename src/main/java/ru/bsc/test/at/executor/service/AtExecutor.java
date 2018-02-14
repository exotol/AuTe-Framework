package ru.bsc.test.at.executor.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import ru.bsc.test.at.executor.exception.ScenarioStopException;
import ru.bsc.test.at.executor.helper.HttpHelper;
import ru.bsc.test.at.executor.helper.ServiceRequestsComparatorHelper;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepParameterSet;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.step.executor.IStepExecutor;
import ru.bsc.test.at.executor.wiremock.WireMockAdmin;

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

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@SuppressWarnings({"unused"})
public class AtExecutor {

    private final ServiceRequestsComparatorHelper serviceRequestsComparatorHelper = new ServiceRequestsComparatorHelper();
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
                    e.printStackTrace();
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

        } catch (ScenarioStopException e) {
            // Stop scenario executing
            e.printStackTrace();
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

    private void executeSteps(Connection connection, Stand stand, List<Step> stepList, Project project, HttpHelper httpHelper, Map<String, Object> scenarioVariables, List<StepResult> stepResultList, boolean stepEditable, IStopObserver stopObserver) throws ScenarioStopException {
        if (stepList == null) {
            return;
        }
        List<IStepExecutor> stepExecutorList = IStepExecutor.getStepExecutorList();
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
                    stepResult.setEditable(stepEditable);
                    stepResultList.add(stepResult);

                    if (stepParameterSet.getStepParameterList() != null) {
                        stepParameterSet.getStepParameterList()
                                .forEach(stepParameter -> scenarioVariables.put(stepParameter.getName(), stepParameter.getValue()));
                        stepResult.setDescription(stepParameterSet.getDescription());
                    }
                    try (WireMockAdmin wireMockAdmin = stand != null && StringUtils.isNotEmpty(stand.getWireMockUrl()) ? new WireMockAdmin(stand.getWireMockUrl() + "/__admin") : null) {
                        if (stand == null) {
                            throw new Exception("Stand is not configured.");
                        }
                        String testId = project.getUseRandomTestId() ? UUID.randomUUID().toString() : "-";
                        stepResult.setTestId(testId);

                        for (IStepExecutor stepExecutor : stepExecutorList) {
                            if (stepExecutor.support(step)) {
                                stepResult.setSavedParameters(scenarioVariables.toString());
                                stepExecutor.execute(wireMockAdmin, connection, stand, httpHelper, scenarioVariables, testId, project, step, stepResult, projectPath);
                                break;
                            }
                        }

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

                    if (stopObserver != null && stopObserver.stop()) {
                        throw new ScenarioStopException();
                    }
                }
            }
        }
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