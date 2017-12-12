package ru.bsc.test.autotester.service.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.autotester.mapper.ProjectRoMapper;
import ru.bsc.test.autotester.repository.ProjectRepository;
import ru.bsc.test.autotester.ro.ImportProjectRo;
import ru.bsc.test.autotester.ro.ProjectRo;
import ru.bsc.test.autotester.service.ProjectService;
import ru.bsc.test.autotester.yaml.YamlUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    @Override
    public void importProjectFormYaml(ImportProjectRo importProjectRo) throws Exception {
        Project project = YamlUtils.loadAsFromString(filterYAML(importProjectRo.getYamlContent()), Project.class);
        project.setCode(importProjectRo.getProjectCode());

        projectRepository.saveFullProject(project);
    }

    private String filterYAML(String yaml) {
        List<String> list = new LinkedList<>();

        Scanner scanner = new Scanner(yaml);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.contains("File: ") || line.contains("project: ")) {
                continue;
            }
            if (line.contains("afterScenario: ")) {
                line = line.replace("afterScenario: ", "afterScenarioOld: ");
            }
            if (line.contains("beforeScenario: ")) {
                line = line.replace("beforeScenario: ", "beforeScenarioOld: ");
            }
            if (line.contains("scenarioGroup: ")) {
                line = line.replace("scenarioGroup: ", "scenarioGroupOld: ");
            }

            list.add(line);
        }

        return list.stream().collect(Collectors.joining("\n"));
    }
}
