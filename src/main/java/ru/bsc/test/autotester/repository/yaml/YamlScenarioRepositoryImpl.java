package ru.bsc.test.autotester.repository.yaml;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.autotester.component.ProjectsSource;
import ru.bsc.test.autotester.repository.ScenarioRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

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
        return projectsSource.getProjectList().stream()
                .filter(p -> projectCode.equals(p.getCode()))
                .map(Project::getScenarioList)
                .flatMap(Collection::stream)
                .filter(scenario -> checkSteps(scenario, relativeUrl))
                .collect(Collectors.toSet());
    }

    @Override
    public void delete(String projectCode, String scenarioPath) throws IOException {
        projectsSource.deleteScenario(projectCode, scenarioPath);
    }

    private boolean checkSteps(Scenario scenario, String relativeUrl) {
        return scenario.getStepList()
                .stream()
                .anyMatch(s -> containsIgnoreCase(s.getRelativeUrl(), relativeUrl));
    }
}
