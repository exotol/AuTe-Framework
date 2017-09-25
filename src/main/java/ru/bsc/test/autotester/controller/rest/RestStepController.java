package ru.bsc.test.autotester.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.service.ScenarioService;
import ru.bsc.test.autotester.service.StepService;

import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/steps")
public class RestStepController {

    private final StepService stepService;
    private final ScenarioService scenarioService;

    @Autowired
    public RestStepController(StepService stepService, ScenarioService scenarioService) {
        this.stepService = stepService;
        this.scenarioService = scenarioService;
    }

    @RequestMapping(value = "{stepId}", method = RequestMethod.GET)
    public void findAll(@PathVariable Long stepId) {
        Step step = stepService.findOne(stepId);
        if (step != null) {
            Scenario scenario = step.getScenario();
            scenario.setSteps(scenario.getSteps().stream()
                    .filter(step1 -> !Objects.equals(step1.getId(), stepId)).collect(Collectors.toList())
            );
            scenarioService.save(scenario);
        } else {
            // TODO: return 404
        }
    }
}
