package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.autotester.ro.ProjectRo;

import java.util.List;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface ProjectService {

    List<Project> findAll();

    Project findOne(String projectCode);

    ProjectRo updateFromRo(String projectCode, ProjectRo projectRo);

    void addNewGroup(String projectCode, String groupName) throws Exception;

    void renameGroup(String projectCode, String oldGroupName, String newGroupName) throws Exception;
}
