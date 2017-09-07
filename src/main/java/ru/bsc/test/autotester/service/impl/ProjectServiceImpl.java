package ru.bsc.test.autotester.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.Yaml;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.autotester.repository.ProjectRepository;
import ru.bsc.test.autotester.service.ProjectService;
import ru.bsc.test.autotester.service.ScenarioGroupService;

import java.util.Comparator;
import java.util.List;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ScenarioGroupService scenarioGroupService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ScenarioGroupService scenarioGroupService) {
        this.projectRepository = projectRepository;
        this.scenarioGroupService = scenarioGroupService;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Project findOne(Long projectId) {
        return projectRepository.findOne(projectId);
    }

    @Override
    @Transactional
    public String findOneAsYaml(Long projectId) {
        return new Yaml().dump(projectRepository.findOne(projectId));
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Scenario addNewScenario(String name, long projectId, Long scenarioGroupId) {
        Project project = findOne(projectId);
        if (project != null) {
            Scenario scenario = new Scenario();
            scenario.setName(name);
            scenario.setProject(project);
            if (scenarioGroupId != null) {
                scenario.setScenarioGroup(scenarioGroupService.findOne(scenarioGroupId));
            }

            project.getScenarios().add(scenario);
            project = save(project);
            projectRepository.flush();
            return project.getScenarios().stream().max(Comparator.comparing(Scenario::getId)).orElseGet(null);
        } else {
            return null;
        }
    }
}
