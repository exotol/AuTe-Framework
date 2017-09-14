package ru.bsc.test.autotester.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.bsc.test.at.executor.model.MockServiceResponse;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepParameter;
import ru.bsc.test.at.executor.model.StepParameterSet;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.ro.MockServiceResponseRo;
import ru.bsc.test.autotester.ro.StepParameterRo;
import ru.bsc.test.autotester.ro.StepParameterSetRo;
import ru.bsc.test.autotester.ro.StepResultRo;
import ru.bsc.test.autotester.ro.StepRo;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class StepRoMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
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
            @Mapping(target = "mockServiceResponseList", source = "mockServiceResponseList"),
            @Mapping(target = "disabled", source = "disabled"),
            @Mapping(target = "stepComment", source = "stepComment"),
            @Mapping(target = "savedValuesCheck", source = "savedValuesCheck"),
            @Mapping(target = "stepParameterSetList", source = "stepParameterSetList")
    })
    abstract Step stepRoToStep(StepRo stepRo);

    @Mappings({
            @Mapping(target = "id", ignore = true),
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
            @Mapping(target = "stepParameterSetList", source = "stepParameterSetList")
    })
    abstract StepRo stepToStepRo(Step step);

    abstract List<Step> convertStepListToStepRoList(List<StepRo> stepRoList);
    abstract public List<StepRo> convertStepRoListToStepList(List<Step> stepList);

    abstract List<StepParameterSet> convertStepParameterSetRoListToStepParameterSetList(List<StepParameterSetRo> stepParameterSetRoList);
    abstract List<StepParameterSetRo> convertStepParameterSetListToStepParameterSetRoList(List<StepParameterSet> stepParameterSetList);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "step", ignore = true),
            @Mapping(target = "sort", source = "sort"),
            @Mapping(target = "stepParameterList", source = "stepParameterList"),
            @Mapping(target = "description", source = "description")
    })
    abstract StepParameterSet stepParameterSetRoToStepParameterSet(StepParameterSetRo stepParameterSetRo);
    abstract StepParameterSetRo stepParameterSetToStepParameterSetRo(StepParameterSet stepParameterSet);

    abstract List<StepParameterRo> convertStepParameterToStepParameterRo(List<StepParameter> stepParameterList);
    abstract List<StepParameter> convertStepParameterRoToStepParameter(List<StepParameterRo> stepParameterListRo);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "stepParameterSet", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "value", source = "value")
    })
    abstract StepParameter stepParameterRoToStepParameter(StepParameterRo stepParameterRo);
    abstract StepParameterRo stepParameterToStepParameterRo(StepParameter stepParameter);

    abstract List<StepResultRo> convertStepResultListToStepResultRo(List<StepResult> stepResultList);
    @Mappings({
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
            @Mapping(target = "stepParameterSetList", source = "stepParameterSetList")
    })
    abstract void stepToStepRo(Step step, @MappingTarget StepRo stepRo);

    abstract List<MockServiceResponse> convertMockServiceResponseRoListToMockServiceResponseList(List<MockServiceResponseRo> mockServiceResponseRoList);
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
    abstract MockServiceResponse mockServiceResponseRoToMockServiceResponse(MockServiceResponseRo mockServiceResponseRo);

    @BeforeMapping
    void mapBefore(StepRo stepRo, @MappingTarget Step step) {
        stepRo.getId();
    }
}
