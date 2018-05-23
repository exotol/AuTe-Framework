package ru.bsc.test.at.executor.service.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.service.IStopObserver;

import java.sql.Connection;
import java.util.List;

/**
 * @author Pavel Golovkin
 */
@Data
@AllArgsConstructor
public class ScenarioExecutorRequest implements ExecutorRequest {

    private Project project;
    private Scenario scenario;
    private Stand stand;
    private Connection connection;
    private List<StepResult> stepResultList;
    private String projectPath;
    private IStopObserver stopObserver;
}
