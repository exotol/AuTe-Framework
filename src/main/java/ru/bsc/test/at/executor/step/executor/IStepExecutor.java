package ru.bsc.test.at.executor.step.executor;

import ru.bsc.test.at.executor.helper.HttpHelper;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.wiremock.WireMockAdmin;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface IStepExecutor {

    static List<IStepExecutor> getStepExecutorList() {
        return Arrays.asList(new RestStepExecutor(), new MqStepExecutor());
    }

    void execute(WireMockAdmin wireMockAdmin, Connection connection, Stand stand, HttpHelper httpHelper, Map<String, Object> scenarioVariables, String testId, Project project, Step step, StepResult stepResult, String projectPath) throws Exception;
    boolean support(Step step);
}
