package ru.bsc.test.autotester.repository.yaml;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.component.ProjectsSource;
import ru.bsc.test.autotester.repository.ScenarioRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
                .flatMap(project -> project.getScenarioList().stream())
                .filter(scenario -> Objects.equals(scenario.getId(), scenarioId))
                .findAny()
                .orElse(null);
    }

    @Override
    public Scenario saveScenario(Scenario scenario, List<Project> projectList) {
        projectsSource.save(projectList);
        return scenario;
    }

    @Override
    public Set<Scenario> findByRelativeUrl(Long projectId, String relativeUrl) {
        Set<Scenario> result = new HashSet<>();
        projectsSource.getProjectList().stream().filter(project -> projectId.equals(project.getId()))
                .forEach(project -> project.getScenarioList().forEach(scenario -> {
                    for(Step step: scenario.getStepList()) {
                        if (step.getRelativeUrl().contains(relativeUrl)) {
                            result.add(scenario);
                            break;
                        }
                    }
                }));
        return result;
    }
}

