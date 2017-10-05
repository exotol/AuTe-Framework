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
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.autotester.mapper.ProjectRoMapper;
import ru.bsc.test.autotester.mapper.ScenarioRoMapper;
import ru.bsc.test.autotester.ro.ProjectRo;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.autotester.service.ProjectService;

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
    private ScenarioRoMapper scenarioRoMapper = Mappers.getMapper(ScenarioRoMapper.class);
    private final ProjectService projectService;

    @Autowired
    public RestProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping("")
    public List<ProjectRo> findAll() {
        return projectRoMapper.convertProjectListToProjectRoList(projectService.findAll());
    }

    @RequestMapping(value = "{projectId}", method = RequestMethod.GET)
    public ProjectRo findOne(@PathVariable Long projectId) {
        Project project = projectService.findOne(projectId);
        if (project != null) {
            return projectRoMapper.projectToProjectRo(project);
        } else {
            // TODO: Return 404 error
            return null;
        }
    }

    @RequestMapping(value = "{projectId}", method = RequestMethod.PUT)
    public ProjectRo saveOne(@PathVariable Long projectId, @RequestBody ProjectRo projectRo) {

        Project project = projectService.findOne(projectId);
        if (project != null) {
            projectRoMapper.updateProject(projectRo, project);
            project = projectService.save(project);
        }

        return projectRoMapper.projectToProjectRo(project);
    }

    @RequestMapping(value = "{projectId}/scenarios", method = RequestMethod.GET)
    public List<ScenarioRo> getScenarios(@PathVariable Long projectId) {
        Project project = projectService.findOne(projectId);
        if (project != null) {
            return projectRoMapper.convertScenarioListToScenarioRoList(project.getScenarios());
        } else {
            // TODO: Return 404 error
            return null;
        }
    }

    @RequestMapping(value = "{projectId}/scenarios", method = RequestMethod.POST)
    public ScenarioRo newScenario(@PathVariable Long projectId, @RequestBody ScenarioRo scenarioRo) {
        Project project = projectService.findOne(projectId);
        if (project != null) {
            Scenario newScenario = new Scenario();
            newScenario.setProject(project);
            newScenario = scenarioRoMapper.updateScenario(scenarioRo, newScenario);
            project.getScenarios().add(newScenario);
            project = projectService.save(project);

            return projectRoMapper.scenarioToScenarioRo(
                    project.getScenarios().stream().max((o1, o2) -> o1.getId() > o2.getId() ? 1 : -1).orElse(null)
            );
        } else {
            // TODO: Return 404 error
            return null;
        }
    }

    @SuppressWarnings("Duplicates")
    @RequestMapping(value = "{projectId}/export-selected-to-yaml", method = RequestMethod.POST)
    @ResponseBody
    public String exportToYaml(
            @PathVariable long projectId,
            @RequestBody() List<Long> selectedScenarios,
            HttpServletResponse response
    ) throws IOException {
        Project project = projectService.findOne(projectId);
        if (project != null) {
            response.setHeader("Content-Disposition", "attachment; filename=\"project-" + project.getProjectCode() + ".yml\"");
            return projectService.getSelectedAsYaml(projectId, selectedScenarios);
        }
        // TODO: return 404
        return null;
    }

    @RequestMapping(value = "{projectId}/get-yaml", method = RequestMethod.GET, produces = "application/x-yaml; charset=utf-8")
    @ResponseBody
    public String getYaml(
            @PathVariable long projectId,
            HttpServletResponse response
    ) throws IOException {
        Project project = projectService.findOne(projectId);
        response.setHeader("Content-Disposition", "inline; filename=\"project-" + project.getProjectCode() +".yml\"");
        return projectService.findOneAsYaml(projectId);
    }
}
