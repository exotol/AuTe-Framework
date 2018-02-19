package ru.bsc.test.autotester.model;

import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.StepResult;

import java.util.List;
import java.util.Map;

public class ExecutionResult {

    private Map<Scenario, List<StepResult>> scenarioStepResultListMap;
    private boolean finished = false;

    public Map<Scenario, List<StepResult>> getScenarioStepResultListMap() {
        return scenarioStepResultListMap;
    }

    public void setScenarioStepResultListMap(Map<Scenario, List<StepResult>> scenarioStepResultListMap) {
        this.scenarioStepResultListMap = scenarioStepResultListMap;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }
}
