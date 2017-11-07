package ru.bsc.test.autotester.repository.yaml;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.component.ProjectsSource;
import ru.bsc.test.autotester.repository.StepRepository;

import java.util.List;
import java.util.Objects;

/**
 * Created by sdoroshin on 27.10.2017.
 *
 */

public class YamlStepRepositoryImpl implements StepRepository {

    private final ProjectsSource projectsSource;

    public YamlStepRepositoryImpl(ProjectsSource projectsSource) {
        this.projectsSource = projectsSource;
    }

    @Override
    public Step findStep(Long stepId) {
        return projectsSource.getProjectList()
                .stream()
                .flatMap(project -> project.getScenarioList().stream())
                .flatMap(scenario -> scenario.getStepList().stream())
                .filter(scenario -> Objects.equals(scenario.getId(), stepId))
                .findAny()
                .orElse(null);
    }

    @Override
    public Step saveStep(Step step, List<Project> projectList) {
        projectsSource.save(projectList);
        return step;
    }
}
