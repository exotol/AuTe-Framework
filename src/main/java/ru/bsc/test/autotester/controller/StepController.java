package ru.bsc.test.autotester.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.service.ScenarioService;
import ru.bsc.test.autotester.service.StepService;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Controller
@RequestMapping("/step")
public class StepController {

    private StepService stepService;
    private ScenarioService scenarioService;

    @Autowired
    public StepController(StepService stepService, ScenarioService scenarioService) {
        this.stepService = stepService;
        this.scenarioService = scenarioService;
    }

    @RequestMapping("{stepId}")
    public ModelAndView steps(@PathVariable long stepId) {
        Step step = stepService.findOne(stepId);
        Scenario scenario = step.getScenario();

        ModelAndView model = new ModelAndView("scenarioDetail");
        model.addObject("steps", Collections.singletonList(step));
        model.addObject("stepDetail", step);
        model.addObject("scenario", scenario);
        model.addObject("project", scenario.getProject());

        model.addObject("expectedRequestsList", step.getExpectedServiceRequests());

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

    @RequestMapping(value = "{stepId}/save-expected-service-requests", method = RequestMethod.POST)
    public String saveExpectedServiceRequests(
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
        Scenario scenario = scenarioService.findOne(scenarioId);
        if (scenario != null) {
            Long maxSortStep = scenario.getSteps().stream().max(Comparator.comparing(Step::getSort)).map(Step::getSort).orElse(0L);
            Step step = new Step();
            step.setSort(maxSortStep + 50);
            step.setScenario(scenario);

            scenario.getSteps().add(step);
            scenario = scenarioService.save(scenario);
            return "redirect:/scenario/" + scenario.getId();
        }
        return "redirect:/";
    }

    @RequestMapping(value = "{stepId}/delete-step", method = RequestMethod.POST)
    public String deleteStep(
            @PathVariable long stepId
    ) {
        Step step = stepService.findOne(stepId);
        if (step != null) {
            stepService.deleteStep(step.getId());
            return "redirect:/scenario/" + step.getScenario().getId();
        }
        return "redirect:/";
    }

    @RequestMapping(value = "add-expected-request", method = RequestMethod.POST)
    public String addExpectedRequest(
            @RequestParam long stepId,
            @RequestParam String serviceName
    ) {
        Step step = stepService.findOne(stepId);
        if (step != null) {
            Long maxSort = step.getExpectedServiceRequests().stream().max(Comparator.comparing(ExpectedServiceRequest::getSort)).map(ExpectedServiceRequest::getSort).orElse(0L);

            ExpectedServiceRequest expectedServiceRequest = new ExpectedServiceRequest();
            step.getExpectedServiceRequests().add(expectedServiceRequest);
            expectedServiceRequest.setServiceName(serviceName);
            expectedServiceRequest.setStep(step);
            expectedServiceRequest.setSort(maxSort + 50);
            step = stepService.save(step);
            return "redirect:/step/" + step.getId();
        }
        return "redirect:/#notfound";
    }
}
