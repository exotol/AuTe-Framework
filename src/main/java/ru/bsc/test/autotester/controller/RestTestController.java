package ru.bsc.test.autotester.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;
import ru.bsc.test.at.executor.model.MockServiceResponse;
import ru.bsc.test.at.executor.model.ScenarioGroup;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepParameter;
import ru.bsc.test.at.executor.model.StepParameterSet;
import ru.bsc.test.autotester.dto.CheckSavedValuesDto;
import ru.bsc.test.autotester.dto.DeleteParameterSetDto;
import ru.bsc.test.autotester.dto.ParameterSetDto;
import ru.bsc.test.autotester.dto.StepDto;
import ru.bsc.test.autotester.service.ExpectedServiceRequestService;
import ru.bsc.test.autotester.service.MockServiceResponseService;
import ru.bsc.test.autotester.service.ScenarioGroupService;
import ru.bsc.test.autotester.service.ScenarioService;
import ru.bsc.test.autotester.service.StandService;
import ru.bsc.test.autotester.service.StepService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private final ScenarioService scenarioService;

    public RestTestController(StepService stepService, ExpectedServiceRequestService expectedServiceRequestService, MockServiceResponseService mockServiceResponseService, ScenarioGroupService scenarioGroupService, StandService standService, ScenarioService scenarioService) {
        this.stepService = stepService;
        this.expectedServiceRequestService = expectedServiceRequestService;
        this.mockServiceResponseService = mockServiceResponseService;
        this.scenarioGroupService = scenarioGroupService;
        this.standService = standService;
        this.scenarioService = scenarioService;
    }

    @RequestMapping(value = "step/save", method = RequestMethod.POST)
    public void saveSteps(@RequestBody List<StepDto> stepDtoList) {
        if (stepDtoList != null && !stepDtoList.isEmpty()) {
            stepService.saveSteps(stepDtoList.get(0).getScenarioId(), stepDtoList);
        }
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

    @RequestMapping(value = "step/clone", method = RequestMethod.POST)
    @ResponseBody
    public Step cloneStep(@RequestParam Long stepId) {
        Step step = stepService.findOne(stepId);
        if (step != null) {
            return scenarioService.cloneStep(step);
        }
        return null;
    }

    @RequestMapping(value = "project/save-scenario-groups", method = RequestMethod.POST)
    public List<ScenarioGroup> saveScenarioGroups(@RequestBody List<ScenarioGroup> scenarioGroupList) {
        return scenarioGroupService.save(scenarioGroupList);
    }

    @RequestMapping(value = "project/save-stands", method = RequestMethod.POST)
    public List<Stand> saveStands(@RequestBody List<Stand> standList) {
        return standService.save(standList);
    }

    @RequestMapping(value = "save-parameter-set-list", method = RequestMethod.POST)
    public String saveParameterSetList(@RequestBody ParameterSetDto parameterSetDto) {
        Step step = stepService.findOne(parameterSetDto.getStepId());
        step.getStepParameterSetList().forEach(stepParameterSet -> {
            String description = parameterSetDto.getParameterSetList().get(stepParameterSet.getId());
            if (description != null) {
                stepParameterSet.setDescription(description);
            }

            Map<String, String> a = parameterSetDto.getParameterList().get(stepParameterSet.getId());

            if (a != null) {
                a.forEach((s, s2) -> {
                    StepParameter sp = stepParameterSet.getStepParameterList()
                            .stream().filter(stepParameter -> Objects.equals(stepParameter.getName(), s))
                            .findAny().orElse(null);
                    if (sp == null) {
                        sp = new StepParameter();
                        sp.setStepParameterSet(stepParameterSet);
                        sp.setName(s);
                        sp.setValue(s2);
                        stepParameterSet.getStepParameterList().add(sp);
                    }
                });

                stepParameterSet.getStepParameterList().forEach(stepParameter -> {
                    if (a.containsKey(stepParameter.getName())) {
                        stepParameter.setValue(a.get(stepParameter.getName()));
                    }
                });
            }

        });

        stepService.save(step);
        return "";
    }

    @RequestMapping(value = "add-parameter", method = RequestMethod.POST)
    public String addStepParameter(@RequestBody Map<String, String> model) {
        String parameterName = model.get("parameterName");
        Long stepId = Long.valueOf(model.get("stepId"));
        Step step = stepService.findOne(stepId);

        if (StringUtils.isNotEmpty(parameterName) && step != null) {
            step.getStepParameterSetList().forEach(stepParameterSet -> {
                StepParameter stepParameter = new StepParameter();
                stepParameter.setStepParameterSet(stepParameterSet);
                stepParameter.setName(parameterName);
                stepParameter.setValue("");
                stepParameterSet.getStepParameterList().add(stepParameter);
            });
            stepService.save(step);
        }
        return "";
    }

    @RequestMapping(value = "add-parameter-set", method = RequestMethod.POST)
    public String addStepParameterSet(@RequestBody Map<String, String> model) {
        Long stepId = Long.valueOf(model.get("stepId"));
        String description = model.get("description");
        Step step = stepService.findOne(stepId);

        StepParameterSet stepParameterSet = new StepParameterSet();
        stepParameterSet.setStep(step);
        stepParameterSet.setDescription(description);
        step.getStepParameterSetList().add(stepParameterSet);
        stepService.save(step);

        return "";
    }

    @RequestMapping(value = "delete-parameter-set", method = RequestMethod.POST)
    public void delete(@RequestBody DeleteParameterSetDto deleteParameterSetDto) {
        Step step = stepService.findOne(deleteParameterSetDto.getStepId());
        step.setStepParameterSetList(step.getStepParameterSetList().stream()
                .filter(stepParameterSet -> !Objects.equals(stepParameterSet.getId(), deleteParameterSetDto.getStepParameterSetId()))
                .collect(Collectors.toList())
        );
        stepService.save(step);
    }
}
