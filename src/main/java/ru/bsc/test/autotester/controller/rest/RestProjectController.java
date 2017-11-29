package ru.bsc.test.autotester.controller.rest;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.autotester.exception.ResourceNotFoundException;
import ru.bsc.test.autotester.mapper.ProjectRoMapper;
import ru.bsc.test.autotester.ro.ProjectRo;
import ru.bsc.test.autotester.ro.ProjectSearchRo;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.autotester.service.ProjectService;
import ru.bsc.test.autotester.service.ScenarioService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by sdoroshin on 12.09.2017.
 *
 */

@RestController
@RequestMapping("/rest/projects")
public class RestProjectController {

    private ProjectRoMapper projectRoMapper = Mappers.getMapper(ProjectRoMapper.class);
    private final ProjectService projectService;
    private ScenarioService scenarioService;

    @Autowired
    public RestProjectController(ProjectService projectService, ScenarioService scenarioService) {
        this.projectService = projectService;
        this.scenarioService = scenarioService;
    }

    @RequestMapping("")
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
        Project project = projectService.findOne(projectCode);
        if (project != null) {
            return projectRoMapper.convertScenarioListToScenarioRoList(project.getScenarioList());
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{projectCode}/scenarios", method = RequestMethod.POST)
    public ScenarioRo newScenario(@PathVariable String projectCode, @RequestBody ScenarioRo scenarioRo) {
        scenarioRo = projectService.addScenarioToProject(projectCode, scenarioRo);
        if (scenarioRo != null) {
            return scenarioRo;
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{projectCode}/search", method = RequestMethod.POST)
    public List<ScenarioRo> searchByMethod(@PathVariable String projectCode, @RequestBody ProjectSearchRo projectSearchRo) {
        return scenarioService.findScenarioByStepRelativeUrl(projectCode, projectSearchRo);
    }

    @SuppressWarnings("Duplicates")
    @RequestMapping(value = "{projectCode}/export-selected-to-yaml", method = RequestMethod.POST)
    @ResponseBody
    public String exportToYaml(
            @PathVariable String projectCode,
            @RequestBody() List<Long> selectedScenarios,
            HttpServletResponse response
    ) throws IOException {
        Project project = projectService.findOne(projectCode);
        if (project != null) {
            response.setHeader("Content-Disposition", "attachment; filename=\"project-" + project.getProjectCode() + ".yml\"");
            return projectService.getSelectedAsYaml(projectCode, selectedScenarios);
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{projectCode}/get-yaml", method = RequestMethod.GET, produces = "application/x-yaml; charset=utf-8")
    @ResponseBody
    public String getYaml(
            @PathVariable String projectCode,
            HttpServletResponse response
    ) throws IOException {
        Project project = projectService.findOne(projectCode);
        if (project != null) {
            response.setHeader("Content-Disposition", "inline; filename=\"project-" + project.getProjectCode() + ".yml\"");
            return projectService.findOneAsYaml(projectCode);
        }
        throw new ResourceNotFoundException();
    }
}
