package ru.bsc.test.at.executor.service;

import ru.bsc.test.at.executor.model.ScenarioResult;

import java.util.List;

@FunctionalInterface
public interface IExecutingFinishObserver {
    void finish(List<ScenarioResult> stepResultList);
}
