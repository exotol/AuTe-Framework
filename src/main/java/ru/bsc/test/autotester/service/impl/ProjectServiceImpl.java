package ru.bsc.test.autotester.service.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.autotester.mapper.ProjectRoMapper;
import ru.bsc.test.autotester.mapper.ScenarioRoMapper;
import ru.bsc.test.autotester.repository.ProjectRepository;
import ru.bsc.test.autotester.ro.ProjectRo;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.autotester.service.ProjectService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRoMapper projectRoMapper = Mappers.getMapper(ProjectRoMapper.class);
    private ScenarioRoMapper scenarioRoMapper = Mappers.getMapper(ScenarioRoMapper.class);

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAllProjects();
    }

    @Override
    public Project findOne(Long projectId) {
        return projectRepository.findProject(projectId);
    }

    @Override
    public String findOneAsYaml(Long projectId) {
        return new Yaml().dump(projectRepository.findProject(projectId));
    }

    @Override
    public String getSelectedAsYaml(Long projectId, List<Long> selectedScenarios) {
        Project project = findOne(projectId);
        if (project != null) {
            project.setScenarios(project.getScenarios().stream()
                    .filter(scenario -> selectedScenarios.contains(scenario.getId()))
                    .collect(Collectors.toList()));
            return new Yaml().dump(project);
        }
        return null;
    }

    @Override
    public ProjectRo updateFromRo(Long projectId, ProjectRo projectRo) {
        List<Project> projectList = findAll();
        Project project = findOne(projectList, projectId);
        if (project != null) {
            projectRoMapper.updateProject(projectRo, project);
            project = projectRepository.saveProject(project, projectList);
            return projectRoMapper.projectToProjectRo(project);
        }
        return null;
    }

    @Override
    public ScenarioRo addScenarioToProject(Long projectId, ScenarioRo scenarioRo) {
        List<Project> projectList = findAll();
        Project project = findOne(projectList, projectId);
        if (project != null) {
            Scenario newScenario = new Scenario();
            newScenario.setProject(project);
            project.getScenarios().add(newScenario);

            scenarioRoMapper.updateScenario(scenarioRo, newScenario);

            projectRepository.saveProject(project, projectList);
            return projectRoMapper.scenarioToScenarioRo(newScenario);
        }
        return null;
    }

    @Override
    public Project saveProject(Project project, List<Project> projectList) {
        projectRepository.saveProject(project, projectList);
        return project;
    }

    private Project findOne(List<Project> projectList, Long projectId) {
        return projectList.stream()
                .filter(project -> Objects.equals(project.getId(), projectId))
                .findAny()
                .orElse(null);
    }
}
