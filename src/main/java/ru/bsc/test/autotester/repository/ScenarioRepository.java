package ru.bsc.test.autotester.repository;

import ru.bsc.test.at.executor.model.Scenario;

import java.util.Set;

/**
 * Created by sdoroshin on 27.10.2017.
 *
 */
public interface ScenarioRepository {
    Scenario findScenario(String projectCode, String scenarioPath);

    Scenario saveScenario(String projectCode, String scenarioPath, Scenario scenario);

    Set<Scenario> findByRelativeUrl(String projectCode, String relativeUrl);

    void delete(String projectCode, String scenarioPath);
}
