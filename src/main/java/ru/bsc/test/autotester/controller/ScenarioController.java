package ru.bsc.test.autotester.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.bsc.test.autotester.model.Project;
import ru.bsc.test.autotester.model.Scenario;
import ru.bsc.test.autotester.service.ProjectService;
import ru.bsc.test.autotester.service.ScenarioService;
import ru.bsc.test.autotester.service.StepService;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Controller
@RequestMapping("/scenario")
public class ScenarioController {

    private StepService stepService;
    private ScenarioService scenarioService;
    private ProjectService projectService;

    @Autowired
    public ScenarioController(StepService stepService, ScenarioService scenarioService, ProjectService projectService) {
        this.stepService = stepService;
        this.scenarioService = scenarioService;
        this.projectService = projectService;
    }

    @RequestMapping("{scenarioId}")
    public ModelAndView steps(@PathVariable long scenarioId) {
        Scenario scenario = scenarioService.findOne(scenarioId);
        Project project = projectService.findOne(scenario.getProjectId());

        ModelAndView model = new ModelAndView("scenarioDetail");
        model.addObject("scenario", scenario);
        model.addObject("project", project);

        model.addObject("steps", stepService.findAllByScenarioId(scenarioId));
        return model;
    }

    @RequestMapping(value = "{scenarioId}/settings", method = RequestMethod.GET)
    public ModelAndView scenarioSettings(@PathVariable long scenarioId) {
        Scenario scenario = scenarioService.findOne(scenarioId);
        Project project = projectService.findOne(scenario.getProjectId());

        ModelAndView model = new ModelAndView("scenarioSettings");
        model.addObject("scenario", scenario);
        model.addObject("project", project);
        model.addObject("projectScenarios", scenarioService.findAllByProjectId(project.getId()));
        return model;
    }

    @RequestMapping(value = "{scenarioId}/settings", method = RequestMethod.POST)
    public String scenarioSettingsPost(
            @PathVariable long scenarioId,
            @RequestParam String name,
            @RequestParam Long beforeScenarioId,
            @RequestParam Long afterScenarioId
    ) {
        Scenario saveScenario = scenarioService.findOne(scenarioId);
        if (saveScenario != null) {
            saveScenario.setName(name);
            saveScenario.setBeforeScenarioId(beforeScenarioId);
            saveScenario.setAfterScenarioId(afterScenarioId);
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

    @RequestMapping("{scenarioId}/start")
    public String start(@PathVariable long scenarioId) {
        scenarioService.executeScenario(scenarioId);
        return "redirect:/scenario/" + scenarioId;
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
            return "redirect:/project/" + scenario.getProjectId();
        } else {
            return "redirect:/";
        }
    }
}
