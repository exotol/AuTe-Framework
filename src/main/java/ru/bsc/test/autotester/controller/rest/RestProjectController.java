package ru.bsc.test.autotester.controller.rest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.autotester.exception.ResourceNotFoundException;
import ru.bsc.test.autotester.mapper.ProjectRoMapper;
import ru.bsc.test.autotester.ro.ImportProjectRo;
import ru.bsc.test.autotester.ro.ProjectRo;
import ru.bsc.test.autotester.ro.ProjectSearchRo;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.autotester.service.ProjectService;
import ru.bsc.test.autotester.service.ScenarioService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by sdoroshin on 12.09.2017.
 *
 */

@RestController
@RequestMapping("/rest/projects")
public class RestProjectController {
    private final ProjectService projectService;
    private final ScenarioService scenarioService;
    private final ProjectRoMapper projectRoMapper;

    @Autowired
    public RestProjectController(
            ProjectService projectService,
            ScenarioService scenarioService,
            ProjectRoMapper projectRoMapper
    ) {
        this.projectService = projectService;
        this.scenarioService = scenarioService;
        this.projectRoMapper = projectRoMapper;
    }

    @RequestMapping
    public List<ProjectRo> findAll() {
        return projectRoMapper.convertProjectListToProjectRoList(projectService.findAll());
    }

    @RequestMapping(value = "{projectCode}", method = RequestMethod.GET)
    public ProjectRo findOne(@PathVariable String projectCode) {
        Project project = projectService.findOne(projectCode);
        if (project != null) {
            return projectRoMapper.projectToProjectRo(project);
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{projectCode}", method = RequestMethod.PUT)
    public ProjectRo saveOne(@PathVariable String projectCode, @RequestBody ProjectRo projectRo) {
        return projectService.updateFromRo(projectCode, projectRo);
    }

    @RequestMapping(value = "{projectCode}/scenarios", method = RequestMethod.GET)
    public List<ScenarioRo> getScenarios(@PathVariable String projectCode) {
        return projectRoMapper.convertScenarioListToScenarioRoList(scenarioService.findAllByProject(projectCode));
    }

    @RequestMapping(value = "{projectCode}/scenarios", method = RequestMethod.POST)
    public ScenarioRo newScenario(@PathVariable String projectCode, @RequestBody ScenarioRo scenarioRo) throws IOException {
        ScenarioRo savedScenario = scenarioService.addScenarioToProject(projectCode, scenarioRo);
        if (savedScenario == null) {
            throw new ResourceNotFoundException();
        }
        return savedScenario;
    }

    @RequestMapping(value = "{projectCode}/group", method = RequestMethod.POST)
    public List<String> newGroup(@PathVariable String projectCode, @RequestBody String groupName) throws Exception {
        Project project = projectService.findOne(projectCode);
        if (project != null && StringUtils.isNotEmpty(groupName)) {
            projectService.addNewGroup(projectCode, groupName);

            project = projectService.findOne(projectCode);
            if (project != null) {
                return project.getGroupList();
            } else {
                throw new ResourceNotFoundException();
            }
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{projectCode}/group", method = RequestMethod.PUT)
    public List<String> renameGroup(@PathVariable String projectCode, @RequestBody Map<String, String> groupNames) throws Exception {
        Project project = projectService.findOne(projectCode);
        if (project != null && StringUtils.isNotEmpty(groupNames.get("newGroupName"))) {
            projectService.renameGroup(projectCode, groupNames.get("oldGroupName"), groupNames.get("newGroupName"));

            project = projectService.findOne(projectCode);
            if (project != null) {
                return project.getGroupList();
            } else {
                throw new ResourceNotFoundException();
            }
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{projectCode}/search", method = RequestMethod.POST)
    public List<ScenarioRo> searchByMethod(@PathVariable String projectCode, @RequestBody ProjectSearchRo projectSearchRo) {
        return scenarioService.findScenarioByStepRelativeUrl(projectCode, projectSearchRo);
    }

    @RequestMapping(value = "/import-project-from-yaml", method = RequestMethod.POST)
    public void importProjectFromYaml(@RequestBody ImportProjectRo importProjectRo) throws Exception {
        projectService.importProjectFormYaml(importProjectRo);
    }

    @RequestMapping(value = "{projectCode}/get-yaml", method = RequestMethod.GET, produces = "application/x-yaml; charset=utf-8")
    @ResponseBody
    public String getYaml(@PathVariable String projectCode, HttpServletResponse response) {
        Project project = projectService.findOne(projectCode);
        if (project != null) {
            response.setHeader("Content-Disposition", "inline; filename=\"project-" + project.getCode() + ".yml\"");
            return projectService.findOneAsYaml(projectCode);
        }
        throw new ResourceNotFoundException();
    }
}
