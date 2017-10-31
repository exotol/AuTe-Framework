package ru.bsc.test.autotester.repository.yaml;

import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.autotester.component.ProjectsSource;
import ru.bsc.test.autotester.repository.ScenarioRepository;

import java.util.Objects;

/**
 * Created by sdoroshin on 27.10.2017.
 *
 */

public class YamlScenarioRepositoryImpl implements ScenarioRepository {

    private final ProjectsSource projectsSource;

    public YamlScenarioRepositoryImpl(ProjectsSource projectsSource) {
        this.projectsSource = projectsSource;
    }

    @Override
    public Scenario findScenario(Long scenarioId) {
        return projectsSource.getProjectList()
                .stream()
                .flatMap(project -> project.getScenarios().stream())
                .filter(scenario -> Objects.equals(scenario.getId(), scenarioId))
                .findAny()
                .orElse(null);
    }

    @Override
    public Scenario saveScenario(Scenario scenario) {
        projectsSource.save();
        return scenario;
    }
}

