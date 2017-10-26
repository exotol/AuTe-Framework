package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.autotester.ro.ProjectRo;

import java.util.List;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface ProjectService {

    List<Project> findAll();

    Project findOne(Long projectId);

    String findOneAsYaml(Long projectId);

    Project save(Project project);

    Scenario addNewScenario(String name, long projectId, Long scenarioGroupId);

    String getSelectedAsYaml(Long projectId, List<Long> selectedScenarios);

    ProjectRo updateFromRo(Long project, ProjectRo projectRo);
}
