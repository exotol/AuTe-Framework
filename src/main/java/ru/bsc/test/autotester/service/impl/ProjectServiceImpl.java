package ru.bsc.test.autotester.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    private final ProjectRepository projectRepository;
    private final ProjectRoMapper projectRoMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectRoMapper projectRoMapper) {
        this.projectRepository = projectRepository;
        this.projectRoMapper = projectRoMapper;
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
    public ProjectRo updateFromRo(String projectCode, ProjectRo projectRo) {
        synchronized (this) {
            boolean existsProject = findOne(projectCode) != null;
            Project projectFromRo = projectRoMapper.updateProjectFromRo(projectRo);
            if (!existsProject) {
                projectRepository.saveProject(projectFromRo);
            }
            Project project = projectFromRo;
            projectRepository.saveProject(project);
            project = projectRepository.findProject(project.getCode());
            return projectRoMapper.projectToProjectRo(project);
        }
    }

    @Override
    public void addNewGroup(String projectCode, String groupName) throws Exception {
        projectRepository.addNewGroup(projectCode, groupName);
    }

    @Override
    public void renameGroup(String projectCode, String oldGroupName, String newGroupName) throws Exception {
        projectRepository.renameGroup(projectCode, oldGroupName, newGroupName);
    }

    @Override
    public void updateBeforeAfterScenariosSettings(String projectCode, String oldPath, String newPath) {
        Project project = projectRepository.findProject(projectCode);
        if (StringUtils.equals(oldPath, newPath)) {
            return;
        }
        boolean updated = false;
        if (StringUtils.equals(project.getBeforeScenarioPath(), oldPath)) {
            project.setBeforeScenarioPath(newPath);
            updated = true;
        }
        if (StringUtils.equals(project.getAfterScenarioPath(), oldPath)) {
            project.setAfterScenarioPath(newPath);
            updated = true;
        }
        if (updated) {
            projectRepository.saveProject(project);
        }
    }
}
