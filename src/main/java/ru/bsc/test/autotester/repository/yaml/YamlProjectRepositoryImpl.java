package ru.bsc.test.autotester.repository.yaml;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.autotester.component.ProjectsSource;
import ru.bsc.test.autotester.repository.ProjectRepository;

import java.util.List;
import java.util.Objects;

/**
 * Created by sdoroshin on 27.10.2017.
 *
 */
public class YamlProjectRepositoryImpl implements ProjectRepository {

    private final ProjectsSource projectsSource;

    public YamlProjectRepositoryImpl(ProjectsSource projectsSource) {
        this.projectsSource = projectsSource;
    }

    @Override
    public List<Project> findAllProjects() {
        return projectsSource.getProjectList();
    }

    @Override
    public Project findProject(Long projectId) {
        return projectsSource.getProjectList()
                .stream()
                .filter(project -> Objects.equals(project.getId(), projectId))
                .findAny()
                .orElse(null);
    }

    @Override
    public Project saveProject(Project project, List<Project> projectList) {
        projectsSource.save(projectList);
        return project;
    }
}
