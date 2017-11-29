package ru.bsc.test.autotester.repository;

import ru.bsc.test.at.executor.model.Project;

import java.util.List;

/**
 * Created by sdoroshin on 27.10.2017.
 *
 */

public interface ProjectRepository {

    List<Project> findAllProjects();
    Project findProject(String projectCode);

    Project saveProject(Project project, List<Project> projectList);
}
