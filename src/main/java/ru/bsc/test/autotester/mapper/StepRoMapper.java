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

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 14.09.2017.
 */

@SuppressWarnings("unused")
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class StepRoMapper {

    @Mappings({
            @Mapping(target = "code", source = "code"),
            @Mapping(target = "expectedServiceRequests", source = "expectedServiceRequestList"),
            @Mapping(target = "relativeUrl", source = "relativeUrl"),
            @Mapping(target = "requestMethod", source = "requestMethod"),
            @Mapping(target = "request", source = "request"),
            @Mapping(target = "requestFile", ignore = true),
            @Mapping(target = "requestHeaders", source = "requestHeaders"),
            @Mapping(target = "expectedResponse", source = "expectedResponse"),
            @Mapping(target = "expectedResponseFile", ignore = true),
            @Mapping(target = "expectedResponseIgnore", source = "expectedResponseIgnore"),
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
            @Mapping(target = "mqName", source = "mqName"),
            @Mapping(target = "mqMessage", source = "mqMessage"),
            @Mapping(target = "mqMessageFile", ignore = true),
            @Mapping(target = "responseCompareMode", source = "responseCompareMode"),
            @Mapping(target = "formDataList", source = "formDataList"),
            @Mapping(target = "multipartFormData", source = "multipartFormData"),
            @Mapping(target = "jsonCompareMode", source = "jsonCompareMode"),
            @Mapping(target = "numberRepetitions", source = "numberRepetitions"),
            @Mapping(target = "parseMockRequestUrl", source = "parseMockRequestUrl"),
            @Mapping(target = "parseMockRequestXPath", source = "parseMockRequestXPath"),
            @Mapping(target = "parseMockRequestScenarioVariable", source = "parseMockRequestScenarioVariable"),
            @Mapping(target = "timeoutMs", source = "timeoutMs"),
    })
    public abstract Step updateStep(StepRo stepRo, @MappingTarget Step step);

    public Step convertStepRoToStep(StepRo stepRo) {
        return updateStep(stepRo, new Step());
    }

    abstract List<ExpectedServiceRequest> requestListRoToRequest(List<ExpectedServiceRequestRo> expectedServiceRequestRoList);

    abstract List<MockServiceResponse> responseListRoToResponse(List<MockServiceResponseRo> mockServiceResponseRoList);

    abstract List<FormData> formDataRoListToFormData(List<FormDataRo> formDataRoList);

    @Mappings({
            @Mapping(target = "relativeUrl", source = "relativeUrl"),
            @Mapping(target = "requestMethod", source = "requestMethod"),
            @Mapping(target = "request", source = "request"),
            @Mapping(target = "requestHeaders", source = "requestHeaders"),
            @Mapping(target = "expectedResponse", source = "expectedResponse"),
            @Mapping(target = "expectedResponseIgnore", source = "expectedResponseIgnore"),
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
            @Mapping(target = "formDataList", source = "formDataList"),
            @Mapping(target = "jsonCompareMode", source = "jsonCompareMode"),
    })
    public abstract StepRo stepToStepRo(Step step);

    abstract public List<StepRo> convertStepListToStepRoList(List<Step> stepList);

    @Mappings({
            @Mapping(target = "serviceName", source = "serviceName"),
            @Mapping(target = "expectedServiceRequest", source = "expectedServiceRequest"),
            @Mapping(target = "ignoredTags", source = "ignoredTags"),
    })
    abstract ExpectedServiceRequestRo expectedServiceRequestToRo(ExpectedServiceRequest expectedServiceRequest);

    abstract List<ExpectedServiceRequestRo> convertExpectedServiceRequestListToRoList(List<ExpectedServiceRequest> expectedServiceRequests);

    abstract List<StepParameterSetRo> convertStepParameterSetListToStepParameterSetRoList(List<StepParameterSet> stepParameterSetList);

    @Mappings({
            @Mapping(target = "stepParameterList", source = "stepParameterList"),
            @Mapping(target = "description", source = "description")
    })
    abstract StepParameterSet updateStepParameterSetFromRo(StepParameterSetRo stepParameterSetRo);

    abstract List<StepParameter> stepParameterRoListToStepParameter(List<StepParameterRo> stepParameterRoList);

    abstract StepParameterSetRo stepParameterSetToStepParameterSetRo(StepParameterSet stepParameterSet);

    private StepParameterSet updateStepParameterSet(StepParameterSetRo stepParameterSetRo) {
        return updateStepParameterSetFromRo(stepParameterSetRo);

        /*
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
        */

        // return stepParameterSet;
    }

    abstract List<StepParameterRo> convertStepParameterToStepParameterRo(List<StepParameter> stepParameterList);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "value", source = "value")
    })
    abstract StepParameter updateStepParameterFromRo(StepParameterRo stepParameterRo);

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
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "editable", source = "editable")
    })
    abstract StepResultRo stepResultToStepResultRo(StepResult stepResult);

    abstract List<MockServiceResponseRo> convertMockServiceResponseListToMockServiceResponseRoList(List<MockServiceResponse> mockServiceResponseList);

    @Mappings({
            @Mapping(target = "serviceUrl", source = "serviceUrl"),
            @Mapping(target = "responseBody", source = "responseBody"),
            @Mapping(target = "httpStatus", source = "httpStatus")
    })
    abstract MockServiceResponseRo mockServiceResponseToMockServiceResponseRo(MockServiceResponse mockServiceResponse);

    @Mappings({
            @Mapping(target = "code", source = "code"),
            @Mapping(target = "serviceUrl", source = "serviceUrl"),
            @Mapping(target = "responseBody", source = "responseBody"),
            @Mapping(target = "responseBodyFile", ignore = true),
            @Mapping(target = "httpStatus", source = "httpStatus")
    })
    abstract MockServiceResponse updateMockServiceResponseFromRo(MockServiceResponseRo mockServiceResponseRo);

    @Mappings({
            @Mapping(target = "code", source = "code"),
            @Mapping(target = "serviceName", source = "serviceName"),
            @Mapping(target = "expectedServiceRequest", source = "expectedServiceRequest"),
            @Mapping(target = "expectedServiceRequestFile", ignore = true),
            @Mapping(target = "ignoredTags", source = "ignoredTags")
    })
    abstract ExpectedServiceRequest updateExpectedServiceRequestFromRo(ExpectedServiceRequestRo expectedServiceRequestRo);

    private ExpectedServiceRequest updateExpectedServiceRequest(ExpectedServiceRequestRo expectedServiceRequestRo) {
        return updateExpectedServiceRequestFromRo(expectedServiceRequestRo);
    }

    @Mappings({
            @Mapping(target = "fieldName", source = "fieldName"),
            @Mapping(target = "fieldType", source = "fieldType"),
            @Mapping(target = "value", source = "value"),
            @Mapping(target = "filePath", source = "filePath"),
            @Mapping(target = "mimeType", source = "mimeType")
    })
    abstract FormData updateFormDataFromRo(FormDataRo formDataRo);

    abstract List<FormDataRo> convertFormDataListToRo(List<FormData> formDataList);

    @Mappings({
            @Mapping(target = "fieldName", source = "fieldName"),
            @Mapping(target = "fieldType", source = "fieldType"),
            @Mapping(target = "value", source = "value"),
            @Mapping(target = "filePath", source = "filePath")
    })
    abstract FormDataRo formDataToRo(FormData formData);

    public void updateScenarioStepList(List<StepRo> stepRoList, Scenario scenario) {
        scenario.setStepList(stepRoList.stream().map(this::convertStepRoToStep).collect(Collectors.toList()));
    }
}
