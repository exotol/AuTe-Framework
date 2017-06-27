package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.ScenarioGroup;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface ScenarioGroupService {
    ScenarioGroup save(ScenarioGroup scenario);
    ScenarioGroup findOne(long scenarioId);
}
