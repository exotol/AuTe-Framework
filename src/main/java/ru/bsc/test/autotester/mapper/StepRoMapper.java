package ru.bsc.test.autotester.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;
import ru.bsc.test.at.executor.model.FormData;
import ru.bsc.test.at.executor.model.MockServiceResponse;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepParameter;
import ru.bsc.test.at.executor.model.StepParameterSet;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.ro.ExpectedServiceRequestRo;
import ru.bsc.test.autotester.ro.FormDataRo;
import ru.bsc.test.autotester.ro.MockServiceResponseRo;
import ru.bsc.test.autotester.ro.StepParameterRo;
import ru.bsc.test.autotester.ro.StepParameterSetRo;
import ru.bsc.test.autotester.ro.StepResultRo;
import ru.bsc.test.autotester.ro.StepRo;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class StepRoMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "scenario", ignore = true),
            @Mapping(target = "expectedServiceRequests", ignore = true),
            @Mapping(target = "sort", source = "sort"),
            @Mapping(target = "relativeUrl", source = "relativeUrl"),
            @Mapping(target = "requestMethod", source = "requestMethod"),
            @Mapping(target = "request", source = "request"),
            @Mapping(target = "requestHeaders", source = "requestHeaders"),
            @Mapping(target = "expectedResponse", source = "expectedResponse"),
            @Mapping(target = "expectedResponseIgnore", source = "expectedResponseIgnore"),
            @Mapping(target = "savingValues", source = "savingValues"),
            @Mapping(target = "responses", source = "responses"),
            @Mapping(target = "dbParams", source = "dbParams"),
            @Mapping(target = "tmpServiceRequestsDirectory", source = "tmpServiceRequestsDirectory"),
            @Mapping(target = "expectedStatusCode", source = "expectedStatusCode"),
            @Mapping(target = "sql", source = "sql"),
            @Mapping(target = "sqlSavedParameter", source = "sqlSavedParameter"),
            @Mapping(target = "jsonXPath", source = "jsonXPath"),
            @Mapping(target = "requestBodyType", source = "requestBodyType"),
            @Mapping(target = "usePolling", source = "usePolling"),
            @Mapping(target = "pollingJsonXPath", source = "pollingJsonXPath"),
            @Mapping(target = "mockServiceResponseList", ignore = true),
            @Mapping(target = "disabled", source = "disabled"),
            @Mapping(target = "stepComment", source = "stepComment"),
            @Mapping(target = "savedValuesCheck", source = "savedValuesCheck"),
            @Mapping(target = "stepParameterSetList", ignore = true),
            @Mapping(target = "responseCompareMode", source = "responseCompareMode"),
            @Mapping(target = "formDataList", ignore = true)
    })
    abstract void updateStepFromRo(StepRo stepRo, @MappingTarget Step step);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "sort", source = "sort"),
            @Mapping(target = "relativeUrl", source = "relativeUrl"),
            @Mapping(target = "requestMethod", source = "requestMethod"),
            @Mapping(target = "request", source = "request"),
            @Mapping(target = "requestHeaders", source = "requestHeaders"),
            @Mapping(target = "expectedResponse", source = "expectedResponse"),
            @Mapping(target = "expectedResponseIgnore", source = "expectedResponseIgnore"),
            @Mapping(target = "savingValues", source = "savingValues"),
            @Mapping(target = "responses", source = "responses"),
            @Mapping(target = "dbParams", source = "dbParams"),
            @Mapping(target = "tmpServiceRequestsDirectory", source = "tmpServiceRequestsDirectory"),
            @Mapping(target = "expectedStatusCode", source = "expectedStatusCode"),
            @Mapping(target = "sql", source = "sql"),
            @Mapping(target = "sqlSavedParameter", source = "sqlSavedParameter"),
            @Mapping(target = "jsonXPath", source = "jsonXPath"),
            @Mapping(target = "requestBodyType", source = "requestBodyType"),
            @Mapping(target = "usePolling", source = "usePolling"),
            @Mapping(target = "pollingJsonXPath", source = "pollingJsonXPath"),
            @Mapping(target = "mockServiceResponseList", source = "mockServiceResponseList"),
            @Mapping(target = "disabled", source = "disabled"),
            @Mapping(target = "stepComment", source = "stepComment"),
            @Mapping(target = "savedValuesCheck", source = "savedValuesCheck"),
            @Mapping(target = "stepParameterSetList", source = "stepParameterSetList"),
            @Mapping(target = "expectedServiceRequestList", source = "expectedServiceRequests"),
            @Mapping(target = "responseCompareMode", source = "responseCompareMode"),
            @Mapping(target = "formDataRoList", source = "formDataList")
    })
    public abstract StepRo stepToStepRo(Step step);

    abstract public List<StepRo> convertStepRoListToStepList(List<Step> stepList);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "sort", source = "sort"),
        @Mapping(target = "serviceName", source = "serviceName"),
        @Mapping(target = "expectedServiceRequest", source = "expectedServiceRequest"),
        @Mapping(target = "ignoredTags", source = "ignoredTags"),
    })
    abstract ExpectedServiceRequestRo expectedServiceRequestToRo(ExpectedServiceRequest expectedServiceRequest);
    abstract List<ExpectedServiceRequestRo> convertExpectedServiceRequestListToRoList(List<ExpectedServiceRequest> expectedServiceRequests);

    abstract List<StepParameterSetRo> convertStepParameterSetListToStepParameterSetRoList(List<StepParameterSet> stepParameterSetList);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "step", ignore = true),
            @Mapping(target = "sort", source = "sort"),
            @Mapping(target = "stepParameterList", ignore = true),
            @Mapping(target = "description", source = "description")
    })
    abstract void updateStepParameterSetFromRo(StepParameterSetRo stepParameterSetRo, @MappingTarget StepParameterSet stepParameterSet);
    abstract StepParameterSetRo stepParameterSetToStepParameterSetRo(StepParameterSet stepParameterSet);

    private StepParameterSet updateStepParameterSet(StepParameterSetRo stepParameterSetRo, @MappingTarget StepParameterSet stepParameterSet) {
        updateStepParameterSetFromRo(stepParameterSetRo, stepParameterSet);

        if (stepParameterSet.getStepParameterList() == null) {
            stepParameterSet.setStepParameterList(new LinkedList<>());
        }
        if (stepParameterSetRo.getStepParameterList() == null) {
            stepParameterSetRo.setStepParameterList(new LinkedList<>());
        }
        List<StepParameter> setStepParameterList = new LinkedList<>(stepParameterSet.getStepParameterList());
        stepParameterSet.getStepParameterList().clear();
        stepParameterSet.getStepParameterList().addAll(stepParameterSetRo.getStepParameterList().stream()
                .map(stepParameterRo -> setStepParameterList.stream()
                        .filter(stepParameter -> Objects.equals(stepParameter.getId(), stepParameterRo.getId()))
                        .map(stepParameter -> updateStepParameterFromRo(stepParameterRo, stepParameter))
                        .findAny()
                        .orElseGet(() -> {
                            StepParameter newParameter = new StepParameter();
                            updateStepParameterFromRo(stepParameterRo, newParameter);
                            newParameter.setStepParameterSet(stepParameterSet);
                            return newParameter;
                        })
                )
                .collect(Collectors.toList())
        );

        return stepParameterSet;
    }

    abstract List<StepParameterRo> convertStepParameterToStepParameterRo(List<StepParameter> stepParameterList);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "stepParameterSet", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "value", source = "value")
    })
    abstract StepParameter updateStepParameterFromRo(StepParameterRo stepParameterRo, @MappingTarget StepParameter stepParameter);
    abstract StepParameterRo stepParameterToStepParameterRo(StepParameter stepParameter);

    public abstract List<StepResultRo> convertStepResultListToStepResultRo(List<StepResult> stepResultList);
    @Mappings({
            @Mapping(target = "testId", source = "testId"),
            @Mapping(target = "step", source = "step"),
            @Mapping(target = "result", source = "result"),
            @Mapping(target = "details", source = "details"),
            @Mapping(target = "expected", source = "expected"),
            @Mapping(target = "actual", source = "actual"),
            @Mapping(target = "requestUrl", source = "requestUrl"),
            @Mapping(target = "requestBody", source = "requestBody"),
            @Mapping(target = "pollingRetryCount", source = "pollingRetryCount"),
            @Mapping(target = "savedParameters", source = "savedParameters"),
            @Mapping(target = "description", source = "description")
    })
    abstract StepResultRo stepResultToStepResultRo(StepResult stepResult);

    abstract List<MockServiceResponseRo> convertMockServiceResponseListToMockServiceResponseRoList(List<MockServiceResponse> mockServiceResponseList);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "sort", source = "sort"),
            @Mapping(target = "serviceUrl", source = "serviceUrl"),
            @Mapping(target = "responseBody", source = "responseBody"),
            @Mapping(target = "httpStatus", source = "httpStatus")
    })
    abstract MockServiceResponseRo mockServiceResponseToMockServiceResponseRo(MockServiceResponse mockServiceResponse);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "step", ignore = true),
            @Mapping(target = "sort", source = "sort"),
            @Mapping(target = "serviceUrl", source = "serviceUrl"),
            @Mapping(target = "responseBody", source = "responseBody"),
            @Mapping(target = "httpStatus", source = "httpStatus")
    })
    abstract MockServiceResponse updateMockServiceResponseFromRo(MockServiceResponseRo mockServiceResponseRo, @MappingTarget MockServiceResponse mockServiceResponse);

    public Step updateStep(StepRo stepRo, @MappingTarget Step step) {
        updateStepFromRo(stepRo, step);

        if (step.getMockServiceResponseList() == null) {
            step.setMockServiceResponseList(new LinkedList<>());
        }
        if (stepRo.getMockServiceResponseList() == null) {
            stepRo.setMockServiceResponseList(new LinkedList<>());
        }
        List<MockServiceResponse> stepMockServiceResponseList = new LinkedList<>(step.getMockServiceResponseList());
        step.getMockServiceResponseList().clear();
        step.getMockServiceResponseList().addAll(stepRo.getMockServiceResponseList().stream()
                .map(mockServiceResponseRo -> stepMockServiceResponseList.stream()
                        .filter(mockServiceResponse -> Objects.equals(mockServiceResponse.getId(), mockServiceResponseRo.getId()))
                        .map(mockServiceResponse -> updateMockServiceResponseFromRo(mockServiceResponseRo, mockServiceResponse))
                        .findAny()
                        .orElseGet(() -> {
                            MockServiceResponse newResponse = new MockServiceResponse();
                            updateMockServiceResponseFromRo(mockServiceResponseRo, newResponse);
                            newResponse.setStep(step);
                            return newResponse;
                        }))
                .collect(Collectors.toList())
        );

        if (step.getStepParameterSetList() == null) {
            step.setStepParameterSetList(new LinkedList<>());
        }
        if (stepRo.getStepParameterSetList() == null) {
            stepRo.setStepParameterSetList(new LinkedList<>());
        }
        List<StepParameterSet> stepParameterSetList = new LinkedList<>(step.getStepParameterSetList());
        step.getStepParameterSetList().clear();
        step.getStepParameterSetList().addAll(stepRo.getStepParameterSetList().stream()
                .map(stepParameterSetRo -> stepParameterSetList.stream()
                        .filter(stepParameterSet -> Objects.equals(stepParameterSet.getId(), stepParameterSetRo.getId()))
                        .map(stepParameterSet -> updateStepParameterSet(stepParameterSetRo, stepParameterSet))
                        .findAny()
                        .orElseGet(() -> {
                            StepParameterSet newSet = new StepParameterSet();
                            updateStepParameterSet(stepParameterSetRo, newSet);
                            newSet.setStep(step);
                            return newSet;
                        }))
                .collect(Collectors.toList())
        );

        if (step.getExpectedServiceRequests() == null) {
            step.setExpectedServiceRequests(new LinkedList<>());
        }
        if (stepRo.getExpectedServiceRequestList() == null) {
            stepRo.setExpectedServiceRequestList(new LinkedList<>());
        }
        List<ExpectedServiceRequest> stepExpectedServiceRequestList = new LinkedList<>(step.getExpectedServiceRequests());
        step.getExpectedServiceRequests().clear();
        step.getExpectedServiceRequests().addAll(stepRo.getExpectedServiceRequestList().stream()
                .map(expectedServiceRequestRo -> stepExpectedServiceRequestList.stream()
                        .filter(expectedServiceRequest -> Objects.equals(expectedServiceRequest.getId(), expectedServiceRequestRo.getId()))
                        .map(expectedServiceRequest -> updateExpectedServiceRequest(expectedServiceRequestRo, expectedServiceRequest))
                        .findAny()
                        .orElseGet(() -> {
                            ExpectedServiceRequest newRequest = new ExpectedServiceRequest();
                            updateExpectedServiceRequest(expectedServiceRequestRo, newRequest);
                            newRequest.setStep(step);
                            return newRequest;
                        }))
                .collect(Collectors.toList())
        );
        if (step.getFormDataList() == null) {
            step.setFormDataList(new LinkedList<>());
        }
        if (stepRo.getFormDataRoList() == null) {
            stepRo.setFormDataRoList(new LinkedList<>());
        }
        List<FormData> formDataList = new LinkedList<>(step.getFormDataList());
        step.getFormDataList().clear();
        step.getFormDataList().addAll(stepRo.getFormDataRoList().stream()
                .map(formDataRo -> formDataList.stream()
                        .filter(formData -> Objects.equals(formData.getId(), formDataRo.getId()))
                        .map(formData -> updateFormData(formDataRo, formData))
                        .findAny()
                        .orElseGet(() -> {
                            FormData formData = new FormData();
                            updateFormData(formDataRo, formData);
                            formData.setStep(step);
                            return formData;
                        })
                ).collect(Collectors.toList()));
        return step;
    }

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "step", ignore = true),
            @Mapping(target = "sort", source = "sort"),
            @Mapping(target = "serviceName", source = "serviceName"),
            @Mapping(target = "expectedServiceRequest", source = "expectedServiceRequest"),
            @Mapping(target = "ignoredTags", source = "ignoredTags")
    })
    abstract void updateExpectedServiceRequestFromRo(ExpectedServiceRequestRo expectedServiceRequestRo, @MappingTarget ExpectedServiceRequest expectedServiceRequest);
    private ExpectedServiceRequest updateExpectedServiceRequest(ExpectedServiceRequestRo expectedServiceRequestRo, @MappingTarget ExpectedServiceRequest expectedServiceRequest) {
        updateExpectedServiceRequestFromRo(expectedServiceRequestRo, expectedServiceRequest);
        return expectedServiceRequest;
    }

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "step", ignore = true),
            @Mapping(target = "fieldName", source = "fieldName"),
            @Mapping(target = "fieldType", source = "fieldType"),
            @Mapping(target = "value", source = "value"),
            @Mapping(target = "filePath", source = "filePath")
    })
    abstract void updateFormDataFromRo(FormDataRo formDataRo, @MappingTarget FormData formData);

    abstract FormDataRo FormDataToFormDataRo(FormData formData);

    private FormData updateFormData(FormDataRo formDataRo, @MappingTarget FormData formData) {
        updateFormDataFromRo(formDataRo, formData);
        return formData;
    }
    abstract List<FormDataRo> convertFormDataListToFormDataRoList(List<FormData> formDataList);

    public Scenario updateScenarioStepList(List<StepRo> stepRoList, Scenario scenario) {
        List<Step> scenarioSteps = new LinkedList<>(scenario.getSteps());
        scenario.getSteps().clear();
        scenario.getSteps().addAll(stepRoList.stream()
                .map(stepRo -> scenarioSteps.stream()
                        .filter(step -> Objects.equals(step.getId(), stepRo.getId()))
                        .map(step -> updateStep(stepRo, step))
                        .findAny()
                        .orElseGet(() -> {
                            Step newStep = new Step();
                            updateStep(stepRo, newStep);
                            newStep.setScenario(scenario);
                            return newStep;
                        }))
                .collect(Collectors.toList()));
        return scenario;
    }
}
