package ru.bsc.test.at.executor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.ScenarioResult;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.service.api.Executor;
import ru.bsc.test.at.executor.service.api.ProjectExecutorRequest;
import ru.bsc.test.at.executor.service.api.ScenarioExecutorRequest;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sdoroshin on 21.03.2017.
 */
@Slf4j
@SuppressWarnings("unused")
public class AtProjectExecutor implements Executor<ProjectExecutorRequest> {

    private static final int POLLING_RETRY_COUNT = 50;
    private static final int POLLING_RETRY_TIMEOUT_MS = 1000;
    private static final String DEFAULT_CONTENT_TYPE = "text/xml";

    private String projectPath;

    public AtProjectExecutor(String projectPath) {
        this.projectPath = projectPath;
    }

    @Override
    public void execute(ProjectExecutorRequest projectExecutorRequest) {
        Assert.notNull(projectExecutorRequest, "projectExecutorRequest must not be null");
        // Создать подключение к БД, которое будет использоваться сценарием для select-запросов.
        Set<Stand> standSet = new LinkedHashSet<>();
        // Собрать список всех используемых стендов выбранными сценариями
        if (projectExecutorRequest.getProject().getStand() != null) {
            standSet.add(projectExecutorRequest.getProject().getStand());
        }
        AtScenarioExecutor atScenarioExecutor = new AtScenarioExecutor();
        List<ScenarioResult> scenarioResultList = projectExecutorRequest.getScenarioResultList();
        try (ExecutorJdbcConnectionHolder executorJdbcConnectionHolder = new ExecutorJdbcConnectionHolder(standSet)) {
            for (Scenario scenario : projectExecutorRequest.getScenarioExecuteList()) {
                List<StepResult> stepResultList = new LinkedList<>();
                scenarioResultList.add(new ScenarioResult(scenario, stepResultList));
                atScenarioExecutor.execute(
                        new ScenarioExecutorRequest(projectExecutorRequest.getProject(), scenario, projectExecutorRequest.getProject().getStand(),
                                executorJdbcConnectionHolder.getConnection(projectExecutorRequest.getProject().getStand()),
                                stepResultList, projectPath, projectExecutorRequest.getStopObserver())
                );
            }
        }
        projectExecutorRequest.getFinishObserver().finish(scenarioResultList);
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
                log.info("parseLongOrVariable: got error! Take the default value: {}", ex.getMessage());
                result = defaultValue;
            }
        }
        log.debug("parseLongOrVariable result {}", result);
        return result;
    }
}
