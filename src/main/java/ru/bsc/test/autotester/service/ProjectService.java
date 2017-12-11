package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.autotester.ro.ImportProjectRo;
import ru.bsc.test.autotester.ro.ProjectRo;

import java.util.List;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface ProjectService {

    List<Project> findAll();

    Project findOne(String projectCode);

    String findOneAsYaml(String projectCode);

    ProjectRo updateFromRo(String projectCode, ProjectRo projectRo);

    void importProjectFormYaml(ImportProjectRo importProjectRo) throws Exception;
}
