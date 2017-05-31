package ru.bsc.test.autotester.service;

import ru.bsc.test.autotester.model.ScenarioGroup;

import java.util.List;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface ScenarioGroupService {
    List<ScenarioGroup> findAllByProjectId(Long projectId);
    ScenarioGroup save(ScenarioGroup scenario);
    ScenarioGroup findOne(long scenarioId);
}
