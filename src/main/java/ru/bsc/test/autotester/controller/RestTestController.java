package ru.bsc.test.autotester.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;
import ru.bsc.test.at.executor.model.ScenarioGroup;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.service.ExpectedServiceRequestService;
import ru.bsc.test.autotester.service.ScenarioGroupService;
import ru.bsc.test.autotester.service.StepService;

import java.util.List;

/**
 * Created by sdoroshin on 22.05.2017.
 *
 */
@RestController
@RequestMapping("/rest")
public class RestTestController {

    private StepService stepService;
    private ExpectedServiceRequestService expectedServiceRequestService;
    private final ScenarioGroupService scenarioGroupService;

    public RestTestController(StepService stepService, ExpectedServiceRequestService expectedServiceRequestService, ScenarioGroupService scenarioGroupService) {
        this.stepService = stepService;
        this.expectedServiceRequestService = expectedServiceRequestService;
        this.scenarioGroupService = scenarioGroupService;
    }

    @RequestMapping(value = "step/save", method = RequestMethod.POST)
    public void saveSteps(@RequestBody List<Step> step) {
        stepService.saveSteps(step);
    }

    @RequestMapping(value = "step/save-expected-service-requests", method = RequestMethod.POST)
    public void saveExpectedServiceRequests(@RequestBody List<ExpectedServiceRequest> expectedRequest) {
        expectedServiceRequestService.save(expectedRequest);
    }

    @RequestMapping(value = "step/delete-expected-request", method = RequestMethod.POST)
    public String deleteExpectedRequest(
            @RequestParam long expectedServiceRequestId
    ) {
        ExpectedServiceRequest request = expectedServiceRequestService.findOne(expectedServiceRequestId);
        if (request != null) {
            expectedServiceRequestService.delete(request);
            return "redirect:/step/" + request.getStep().getId();

        }
        return "redirect:/";
    }

    @RequestMapping(value = "project/save-scenario-groups", method = RequestMethod.POST)
    public List<ScenarioGroup> saveScenarioGroups(@RequestBody List<ScenarioGroup> scenarioGroupList) {
        return scenarioGroupService.save(scenarioGroupList);
    }

}
