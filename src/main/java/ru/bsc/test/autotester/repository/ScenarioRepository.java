package ru.bsc.test.autotester.repository;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;

import java.util.List;
import java.util.Set;

/**
 * Created by sdoroshin on 27.10.2017.
 *
 */
public interface ScenarioRepository {
    Scenario findScenario(Long scenarioId);

    Scenario saveScenario(Scenario scenario, List<Project> projectList);

    Set<Scenario> findByRelativeUrl(String projectCode, String relativeUrl);
}
