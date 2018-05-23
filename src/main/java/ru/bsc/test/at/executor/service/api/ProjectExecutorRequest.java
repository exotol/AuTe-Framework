package ru.bsc.test.at.executor.service.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.ScenarioResult;
import ru.bsc.test.at.executor.service.IExecutingFinishObserver;
import ru.bsc.test.at.executor.service.IStopObserver;

import java.util.List;

/**
 * @author Pavel Golovkin
 */
@Data
@AllArgsConstructor
public class ProjectExecutorRequest implements ExecutorRequest {
    private Project project; 
    private List<Scenario> scenarioExecuteList;
    List<ScenarioResult> scenarioResultList;
    IStopObserver stopObserver;
    IExecutingFinishObserver finishObserver;
}
