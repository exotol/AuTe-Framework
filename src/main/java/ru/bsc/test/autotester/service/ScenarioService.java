package ru.bsc.test.autotester.service;

import ru.bsc.test.autotester.model.Scenario;

import java.io.IOException;
import java.util.List;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface ScenarioService {
    List<Scenario> findAllByProjectId(Long projectId);
    List<Scenario> findAllByProjectIdAndScenarioGroupId(Long projectId, Long scenarioGroupId);

    List<Scenario> executeScenarioList(Long[] scenarios);

    Scenario save(Scenario scenario);
    Scenario findOne(long scenarioId);
    void delete(Scenario scenario);

    void parseExpectedServiceRequestsJmba(String expectedRequestsBaseDir, Scenario scenario);
}
