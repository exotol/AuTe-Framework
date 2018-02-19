package ru.bsc.test.autotester.repository.yaml;

import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.component.ProjectsSource;
import ru.bsc.test.autotester.repository.ScenarioRepository;

import java.io.IOException;
import java.util.HashSet;
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
    public Scenario findScenario(String projectCode, String scenarioPath) throws IOException {
        return projectsSource.findScenario(projectCode, scenarioPath);
    }

    @Override
    public Scenario saveScenario(String projectCode, String scenarioPath, Scenario scenario, boolean updateDirectoryName) throws IOException {
        projectsSource.saveScenario(projectCode, scenarioPath, scenario, updateDirectoryName);
        return scenario;
    }

    @Override
    public Set<Scenario> findByRelativeUrl(String projectCode, String relativeUrl) {
        Set<Scenario> result = new HashSet<>();
        projectsSource.getProjectList().stream().filter(project -> projectCode.equals(project.getCode()))
                .forEach(project -> project.getScenarioList().forEach(scenario -> {
                    for(Step step: scenario.getStepList()) {
                        if (step.getRelativeUrl() != null && step.getRelativeUrl().toLowerCase().contains(relativeUrl.toLowerCase())) {
                            result.add(scenario);
                            break;
                        }
                    }
                }));
        return result;
    }

    @Override
    public void delete(String projectCode, String scenarioPath) throws IOException {
        projectsSource.deleteScenario(projectCode, scenarioPath);
    }
}

