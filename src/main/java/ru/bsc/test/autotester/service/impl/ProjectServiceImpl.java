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
        synchronized (this) {
            return projectRepository.findAllProjects();
        }
    }

    @Override
    public Project findOne(String projectCode) {
        synchronized (this) {
            return projectRepository.findProject(projectCode);
        }
    }

    private Project findOne(List<Project> projectList, String projectCode) {
        return projectList.stream()
                .filter(project -> Objects.equals(project.getCode(), projectCode))
                .findAny()
                .orElse(null);
    }

    @Override
    public String findOneAsYaml(String projectCode) {
        synchronized (this) {
            return new Yaml().dump(projectRepository.findProject(projectCode));
        }
    }

    @Override
    public ProjectRo updateFromRo(String projectCode, ProjectRo projectRo) {
        synchronized (this) {
            List<Project> projectList = findAll();
            Project project = findOne(projectList, projectCode);
            if (project != null) {
                projectRoMapper.updateProject(projectRo, project);
                project = projectRepository.saveProject(project, projectList);
                return projectRoMapper.projectToProjectRo(project);
            }
            return null;
        }
    }

    @Override
    public ScenarioRo addScenarioToProject(String projectCode, ScenarioRo scenarioRo) {
        synchronized (this) {
            List<Project> projectList = findAll();
            Project project = findOne(projectList, projectCode);
            if (project != null) {
                Scenario newScenario = new Scenario();
                project.getScenarioList().add(newScenario);

                scenarioRoMapper.updateScenario(scenarioRo, newScenario);

                projectRepository.saveProject(project, projectList);
                return projectRoMapper.scenarioToScenarioRo(project.getCode(), project.getName(), newScenario);
            }
            return null;
        }
    }

    @Override
    public Project saveProject(Project project, List<Project> projectList) {
        synchronized (this) {
            projectRepository.saveProject(project, projectList);
            return project;
        }
    }
}
