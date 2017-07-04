package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.ScenarioGroup;

import java.util.List;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface ScenarioGroupService {
    ScenarioGroup save(ScenarioGroup scenario);
    List<ScenarioGroup> save(List<ScenarioGroup> scenarioGroupList);
    ScenarioGroup findOne(long scenarioId);
}
