package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.autotester.ro.ProjectRo;
import ru.bsc.test.autotester.ro.ScenarioRo;

import java.util.List;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface ProjectService {

    List<Project> findAll();

    Project findOne(String projectCode);

    String findOneAsYaml(String projectCode);

    String getSelectedAsYaml(String projectCode, List<Long> selectedScenarios);

    ProjectRo updateFromRo(String projectCode, ProjectRo projectRo);

    ScenarioRo addScenarioToProject(String projectCode, ScenarioRo scenarioRo);

    Project saveProject(Project project, List<Project> projectList);

    Project findOneByCode(String projectCode);
}
