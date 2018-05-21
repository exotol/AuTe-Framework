package ru.bsc.test.at.executor.service;

import java.util.List;

import ru.bsc.test.at.executor.model.ScenarioResult;

@FunctionalInterface
public interface IExecutingFinishObserver {
    void finish(List<ScenarioResult> stepResultList);
}
