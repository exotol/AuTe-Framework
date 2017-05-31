package ru.bsc.test.autotester.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.bsc.test.autotester.model.ExpectedServiceRequest;
import ru.bsc.test.autotester.model.Project;
import ru.bsc.test.autotester.model.Scenario;
import ru.bsc.test.autotester.model.Step;
import ru.bsc.test.autotester.service.ExpectedServiceRequestService;
import ru.bsc.test.autotester.service.ProjectService;
import ru.bsc.test.autotester.service.ScenarioService;
import ru.bsc.test.autotester.service.StepService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Controller
@RequestMapping("/step")
public class StepController {

    private StepService stepService;
    private ScenarioService scenarioService;
    private ProjectService projectService;
    private ExpectedServiceRequestService expectedServiceRequestService;

    @Autowired
    public StepController(StepService stepService, ScenarioService scenarioService, ProjectService projectService, ExpectedServiceRequestService expectedServiceRequestService) {
        this.stepService = stepService;
        this.scenarioService = scenarioService;
        this.projectService = projectService;
        this.expectedServiceRequestService = expectedServiceRequestService;
    }

    @RequestMapping("{stepId}")
    public ModelAndView steps(@PathVariable long stepId) {
        Step step = stepService.findOne(stepId);
        Scenario scenario = scenarioService.findOne(step.getScenarioId());
        Project project = projectService.findOne(scenario.getProjectId());

        ModelAndView model = new ModelAndView("scenarioDetail");
        model.addObject("steps", Collections.singletonList(step));
        model.addObject("stepDetail", step);
        model.addObject("scenario", scenario);
        model.addObject("project", project);

        List<ExpectedServiceRequest> expectedRequestsList = expectedServiceRequestService.findAllByStepIdOrderBySort(stepId);
        model.addObject("expectedRequestsList", expectedRequestsList);

        return model;
    }

    @RequestMapping(value = "{stepId}/save", method = RequestMethod.POST)
    public String save(
            @PathVariable long stepId,
            @RequestParam Step step
    ) {
        stepService.save(step);
        return "redirect:/step/" + stepId;
    }

    @RequestMapping(value = "add-step", method = RequestMethod.POST)
    public String addStep(
            @RequestParam long scenarioId
    ) {

        List<Step> stepList = stepService.findAllByScenarioId(scenarioId);
        Long maxSortStep = stepList.stream().max(Comparator.comparing(Step::getSort)).map(Step::getSort).orElse(0L);

        Step step = new Step();
        step.setScenarioId(scenarioId);
        step.setSort(maxSortStep + 50);
        step = stepService.save(step);
        return "redirect:/step/" + step.getId();
    }

    @RequestMapping(value = "{stepId}/delete-step", method = RequestMethod.POST)
    public String deleteStep(
            @PathVariable long stepId
    ) {
        Step step = stepService.findOne(stepId);
        if (step != null) {
            stepService.deleteStep(step.getId());
            return "redirect:/scenario/" + step.getScenarioId();
        }
        return "redirect:/";
    }
}
