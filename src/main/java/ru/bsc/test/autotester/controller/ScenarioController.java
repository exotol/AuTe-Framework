package ru.bsc.test.autotester.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.autotester.service.ProjectService;
import ru.bsc.test.autotester.service.ScenarioGroupService;
import ru.bsc.test.autotester.service.ScenarioService;
import ru.bsc.test.autotester.service.StepService;

import java.io.IOException;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Controller
@RequestMapping("/scenario")
public class ScenarioController {

    private StepService stepService;
    private ScenarioService scenarioService;
    private final ProjectService projectService;
    private final ScenarioGroupService scenarioGroupService;

    @Autowired
    public ScenarioController(StepService stepService, ScenarioService scenarioService, ProjectService projectService, ScenarioGroupService scenarioGroupService) {
        this.stepService = stepService;
        this.scenarioService = scenarioService;
        this.projectService = projectService;
        this.scenarioGroupService = scenarioGroupService;
    }

    @RequestMapping("{scenarioId}")
    public ModelAndView steps(@PathVariable long scenarioId) {
        Scenario scenario = scenarioService.findOne(scenarioId);
        ModelAndView model = new ModelAndView("scenarioDetail");
        model.addObject("scenario", scenario);
        model.addObject("project", scenario.getProject());

        model.addObject("steps", scenario.getSteps());
        return model;
    }

    @RequestMapping(value = "{scenarioId}/settings", method = RequestMethod.GET)
    public ModelAndView scenarioSettings(@PathVariable long scenarioId) {
        Scenario scenario = scenarioService.findOne(scenarioId);
        Project project = scenario.getProject();

        ModelAndView model = new ModelAndView("scenarioSettings");
        model.addObject("scenario", scenario);
        model.addObject("project", project);
        model.addObject("projectScenarios", project.getScenarios());
        return model;
    }

    @RequestMapping(value = "{scenarioId}/settings", method = RequestMethod.POST)
    public String scenarioSettingsPost(
            @PathVariable long scenarioId,
            @RequestParam String name,
            @RequestParam Long beforeScenarioId,
            @RequestParam Long afterScenarioId,
            @RequestParam(required = false) Long scenarioGroupId
    ) {
        Scenario saveScenario = scenarioService.findOne(scenarioId);
        if (saveScenario != null) {
            saveScenario.setName(name);
            saveScenario.setBeforeScenario(scenarioService.findOne(beforeScenarioId));
            saveScenario.setAfterScenario(scenarioService.findOne(afterScenarioId));
            saveScenario.setScenarioGroup(scenarioGroupService.findOne(scenarioGroupId));
            scenarioService.save(saveScenario);
        }

        return "redirect:/scenario/" + scenarioId + "/settings";
    }

    @RequestMapping(value = "{scenarioId}/import-expected-service-requests", method = RequestMethod.POST)
    public String importExpectedServiceRequests(
            @PathVariable long scenarioId,
            @RequestParam String expectedRequestsBaseDir
    ) {
        Scenario scenario = scenarioService.findOne(scenarioId);
        scenarioService.parseExpectedServiceRequestsJmba(expectedRequestsBaseDir, scenario);
        return "redirect:/scenario/" + scenarioId + "/settings";
    }

    @RequestMapping(value = "{scenarioId}/delete-step", method = RequestMethod.POST)
    public String deleteStep(
            @PathVariable long scenarioId,
            @RequestParam long stepId
    ) {
        stepService.deleteStep(stepId);
        return "redirect:/scenario/" + scenarioId;
    }

    @RequestMapping(value = "{scenarioId}/delete-scenario", method = RequestMethod.POST)
    public String deleteScenario(
            @PathVariable long scenarioId
    ) {
        Scenario scenario = scenarioService.findOne(scenarioId);
        if (scenario != null) {
            scenarioService.delete(scenario);
            return "redirect:/project/" + scenario.getProject().getId();
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "{scenarioId}/clone", method = RequestMethod.POST)
    public String clone(@PathVariable Long scenarioId) throws IOException, CloneNotSupportedException {
        Scenario scenario = scenarioService.findOne(scenarioId);
        Project project = scenario.getProject();

        Scenario cloned = scenario.clone();
        project.getScenarios().add(cloned);
        cloned.setName(cloned.getName() + " (копия)");
        project = projectService.save(project);

        return "redirect:/scenario/" + project.getScenarios().get(project.getScenarios().size() - 1).getId();
    }
}
