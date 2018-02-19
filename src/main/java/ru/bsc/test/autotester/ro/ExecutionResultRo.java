package ru.bsc.test.autotester.ro;

import java.util.List;

public class ExecutionResultRo implements AbstractRo {

    private List<ScenarioResultRo> scenarioResultList;
    private boolean finished;

    public List<ScenarioResultRo> getScenarioResultList() {
        return scenarioResultList;
    }

    public void setScenarioResultList(List<ScenarioResultRo> scenarioResultList) {
        this.scenarioResultList = scenarioResultList;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
