package ru.bsc.test.at.executor.service;

import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.StepResult;

import java.util.List;
import java.util.Map;

public interface IExecutingFinishObserver {
    void finish(Map<Scenario, List<StepResult>> scenarioResultListMap);
}
