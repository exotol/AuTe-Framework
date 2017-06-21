package ru.bsc.test.autotester.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.autotester.model.ExpectedServiceRequest;
import ru.bsc.test.autotester.model.Step;
import ru.bsc.test.autotester.service.ExpectedServiceRequestService;
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

    public RestTestController(StepService stepService, ExpectedServiceRequestService expectedServiceRequestService) {
        this.stepService = stepService;
        this.expectedServiceRequestService = expectedServiceRequestService;
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
            return "redirect:/step/" + request.getStepId();

        }
        return "redirect:/";
    }

}
