package ru.bsc.test.autotester.repository;

import ru.bsc.test.at.executor.model.Scenario;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by sdoroshin on 27.10.2017.
 *
 */
public interface ScenarioRepository {
    List<Scenario> findScenarios(String projectCode);

    Scenario findScenario(String projectCode, String scenarioPath) throws IOException;

    Scenario saveScenario(String projectCode, String scenarioPath, Scenario scenario, boolean updateDirectoryName) throws IOException;

    Set<Scenario> findByRelativeUrl(String projectCode, String relativeUrl);

    void delete(String projectCode, String scenarioPath) throws IOException;
}
