package ru.bsc.test.autotester.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.autotester.repository.ProjectRepository;
import ru.bsc.test.autotester.service.ProjectService;

import java.util.List;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    @Transactional
    public Project findOne(Long projectId) {
        return projectRepository.findOne(projectId);
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }
}
