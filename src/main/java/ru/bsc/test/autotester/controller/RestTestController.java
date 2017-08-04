package ru.bsc.test.autotester.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;
import ru.bsc.test.at.executor.model.MockServiceResponse;
import ru.bsc.test.at.executor.model.ScenarioGroup;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.dto.CheckSavedValuesDto;
import ru.bsc.test.autotester.service.ExpectedServiceRequestService;
import ru.bsc.test.autotester.service.MockServiceResponseService;
import ru.bsc.test.autotester.service.ScenarioGroupService;
import ru.bsc.test.autotester.service.StandService;
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
    private final MockServiceResponseService mockServiceResponseService;
    private final ScenarioGroupService scenarioGroupService;
    private final StandService standService;

    public RestTestController(StepService stepService, ExpectedServiceRequestService expectedServiceRequestService, MockServiceResponseService mockServiceResponseService, ScenarioGroupService scenarioGroupService, StandService standService) {
        this.stepService = stepService;
        this.expectedServiceRequestService = expectedServiceRequestService;
        this.mockServiceResponseService = mockServiceResponseService;
        this.scenarioGroupService = scenarioGroupService;
        this.standService = standService;
    }

    @RequestMapping(value = "step/save", method = RequestMethod.POST)
    public void saveSteps(@RequestBody List<Step> step) {
        stepService.saveSteps(step);
    }

    @RequestMapping(value = "step/save-expected-service-requests", method = RequestMethod.POST)
    public void saveExpectedServiceRequests(@RequestBody List<ExpectedServiceRequest> expectedRequest) {
        expectedServiceRequestService.save(expectedRequest);
    }

    @RequestMapping(value = "step/save-check-saved-values", method = RequestMethod.POST)
    public void saveCheckSavedValues(@RequestBody CheckSavedValuesDto checkSavedValuesDto) {
        Step step = stepService.findOne(checkSavedValuesDto.getStepId());
        step.getSavedValuesCheck().clear();
        checkSavedValuesDto.getMap().forEach(keyValuePair -> step.getSavedValuesCheck().put(keyValuePair.getKey(), keyValuePair.getValue()));
        stepService.save(step);
    }

    @RequestMapping(value = "step/save-mock-service-response", method = RequestMethod.POST)
    public void saveMockServiceResponse(@RequestBody List<MockServiceResponse> mockServiceResponse) {
        mockServiceResponseService.save(mockServiceResponse);
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

    @RequestMapping(value = "step/delete-mock-service-response", method = RequestMethod.POST)
    public String deleteMockServiceResponse(
            @RequestParam long mockServiceId
    ) {
        MockServiceResponse mockServiceResponse = mockServiceResponseService.findOne(mockServiceId);
        if (mockServiceResponse != null) {
            mockServiceResponseService.delete(mockServiceResponse);
            return "redirect:/step/" + mockServiceResponse.getStep().getId();

        }
        return "redirect:/";
    }

    @RequestMapping(value = "project/save-scenario-groups", method = RequestMethod.POST)
    public List<ScenarioGroup> saveScenarioGroups(@RequestBody List<ScenarioGroup> scenarioGroupList) {
        return scenarioGroupService.save(scenarioGroupList);
    }

    @RequestMapping(value = "project/save-stands", method = RequestMethod.POST)
    public List<Stand> saveStands(@RequestBody List<Stand> standList) {
        return standService.save(standList);
    }
}
