package ru.bsc.test.autotester.controller.rest;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.autotester.mapper.RoMapper;
import ru.bsc.test.autotester.ro.ProjectRo;
import ru.bsc.test.autotester.service.ProjectService;

import java.util.List;

/**
 * Created by sdoroshin on 12.09.2017.
 *
 */

@RestController
@RequestMapping("/rest/projects")
public class RestProjectController {

    private RoMapper mapper = Mappers.getMapper(RoMapper.class);
    private final ProjectService projectService;

    @Autowired
    public RestProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping("")
    public List<ProjectRo> findAll() {
        return mapper.convertProjectListToProjectRoList(projectService.findAll());
    }

    @RequestMapping(value = "{projectId}", method = RequestMethod.PUT)
    public ProjectRo findOne(@PathVariable Long projectId, @RequestBody ProjectRo projectRo) {

        Project project = projectService.findOne(projectId);
        if (project != null) {
            mapper.updateProjectFromRo(projectRo, project);
            projectService.save(project);
        }

        return mapper.projectToProjectRo(project);
    }
}
