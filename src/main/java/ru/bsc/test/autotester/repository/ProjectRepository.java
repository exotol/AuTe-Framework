package ru.bsc.test.autotester.repository;

import ru.bsc.test.at.executor.model.Project;

import java.util.List;

/**
 * Created by sdoroshin on 27.10.2017.
 *
 */

public interface ProjectRepository {

    List<Project> findAllProjects();

    List<Project> findAllProjectsWithScenarios();

    Project findProject(String projectCode);

    void saveProject(Project project);

    void addNewGroup(String projectCode, String groupName) throws Exception;

    void renameGroup(String projectCode, String oldGroupName, String newGroupName) throws Exception;

}
