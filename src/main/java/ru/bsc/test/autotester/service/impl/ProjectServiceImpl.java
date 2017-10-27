package ru.bsc.test.autotester.service.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.Yaml;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.autotester.component.ProjectsSource;
import ru.bsc.test.autotester.mapper.ProjectRoMapper;
import ru.bsc.test.autotester.repository.ProjectRepository;
import ru.bsc.test.autotester.ro.ProjectRo;
import ru.bsc.test.autotester.service.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRoMapper projectRoMapper = Mappers.getMapper(ProjectRoMapper.class);

    private final ProjectRepository projectRepository;
    private final ProjectsSource projectsSource;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectsSource projectsSource) {
        this.projectRepository = projectRepository;
        this.projectsSource = projectsSource;
    }

    @Override
    public List<Project> findAll() {
        return projectsSource.getProjectList();
        // return projectRepository.findAllByOrderByNameAsc();
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
    @Transactional
    public ProjectRo updateFromRo(Long projectId, ProjectRo projectRo) {
        Project project = findOne(projectId);
        if (project != null) {
            projectRoMapper.updateProject(projectRo, project);
            return projectRoMapper.projectToProjectRo(save(project));
        }
        return null;
    }
}
