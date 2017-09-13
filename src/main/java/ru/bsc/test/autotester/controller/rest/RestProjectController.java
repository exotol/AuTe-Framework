package ru.bsc.test.autotester.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.autotester.dto.ProjectDto;
import ru.bsc.test.autotester.service.ProjectService;

import java.util.List;

/**
 * Created by sdoroshin on 12.09.2017.
 *
 */

@RestController
@RequestMapping("/rest/project")
public class RestProjectController {

    private final ProjectService projectService;

    @Autowired
    public RestProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping("")
    public List<Project> findAll() {
        return projectService.findAll();
    }

    @RequestMapping(value = "{projectId}", method = RequestMethod.PUT)
    public Project findOne(@PathVariable Long projectId, @RequestBody ProjectDto projectDto) {

        Project project = projectService.findOne(projectId);
        if (project != null) {
            projectDto.mergeTo(project);
            projectService.save(project);
        }

        return project;
    }
}
