package ru.bsc.test.autotester.service.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.autotester.mapper.ProjectRoMapper;
import ru.bsc.test.autotester.repository.ProjectRepository;
import ru.bsc.test.autotester.ro.ProjectRo;
import ru.bsc.test.autotester.service.ProjectService;

import java.util.List;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRoMapper projectRoMapper = Mappers.getMapper(ProjectRoMapper.class);

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

    @Override
    public String findOneAsYaml(String projectCode) {
        synchronized (this) {
            return new Yaml().dump(projectRepository.findProject(projectCode));
        }
    }

    @Override
    public ProjectRo updateFromRo(String projectCode, ProjectRo projectRo) {
        synchronized (this) {
            Project project = findOne(projectCode);
            if (project != null) {
                project = projectRoMapper.updateProjectFromRo(projectRo);
                project = projectRepository.saveProject(project);
                return projectRoMapper.projectToProjectRo(project);
            }
            return null;
        }
    }
}
