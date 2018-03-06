package ru.bsc.test.at.executor.model;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * Created by sdoroshin on 10.05.2017.
 */
@Data
public class Step implements Serializable, AbstractModel {
    private static final long serialVersionUID = 1670286319126044952L;

    private String code;
    private List<ExpectedServiceRequest> expectedServiceRequests = new ArrayList<>();
    private String relativeUrl;
    private String requestMethod;
    private String request;
    private String requestFile;
    private String requestHeaders;
    private String expectedResponse;
    private String expectedResponseFile;
    private Boolean expectedResponseIgnore;
    private Integer expectedStatusCode;
    private String sql;
    private String sqlSavedParameter;
    private String jsonXPath;
    private RequestBodyType requestBodyType;
    private Boolean usePolling;
    private String pollingJsonXPath;
    private List<MockServiceResponse> mockServiceResponseList = new ArrayList<>();
    private Boolean disabled;
    private String stepComment;
    private Map<String, String> savedValuesCheck;
    private ResponseCompareMode responseCompareMode;
    private List<StepParameterSet> stepParameterSetList = new ArrayList<>();
    private String mqName;
    private String mqMessage;
    private String mqMessageFile;
    private Boolean multipartFormData;
    private List<FormData> formDataList = new ArrayList<>();
    private String jsonCompareMode;
    private String script;
    private String numberRepetitions;
    private String parseMockRequestUrl;
    private String parseMockRequestXPath;
    private String parseMockRequestScenarioVariable;
    private Long timeoutMs;
    private List<MqMockResponse> mqMockResponseList;
    private List<ExpectedMqRequest> expectedMqRequestList;

    public Step copy() {
        Step step = new Step();
        step.setRelativeUrl(getRelativeUrl());
        step.setRequestMethod(getRequestMethod());
        step.setRequestHeaders(getRequestHeaders());
        step.setRequest(getRequest());
        step.setExpectedResponse(getExpectedResponse());
        step.setExpectedStatusCode(getExpectedStatusCode());
        step.setSql(getSql());
        step.setSqlSavedParameter(getSqlSavedParameter());
        step.setJsonXPath(getJsonXPath());
        step.setRequestBodyType(getRequestBodyType());
        step.setExpectedResponseIgnore(getExpectedResponseIgnore());
        step.setUsePolling(getUsePolling());
        step.setPollingJsonXPath(getPollingJsonXPath());
        step.setDisabled(getDisabled());
        step.setStepComment(getStepComment());
        step.setScript(getScript());
        if (getSavedValuesCheck() != null) {
            step.setSavedValuesCheck(new HashMap<>(getSavedValuesCheck()));
        }
        step.setResponseCompareMode(getResponseCompareMode());
        step.setExpectedServiceRequests(new LinkedList<>());
        for (ExpectedServiceRequest expectedServiceRequest : getExpectedServiceRequests()) {
            step.getExpectedServiceRequests().add(expectedServiceRequest.copy());
        }
        step.setMockServiceResponseList(new LinkedList<>());
        for (MockServiceResponse mockServiceResponse : getMockServiceResponseList()) {
            step.getMockServiceResponseList().add(mockServiceResponse.copy());
        }
        step.setStepParameterSetList(new LinkedList<>());
        if (getStepParameterSetList() != null) {
            for (StepParameterSet stepParameterSet : getStepParameterSetList()) {
                step.getStepParameterSetList().add(stepParameterSet.copy());
            }
        }
        step.setFormDataList(new LinkedList<>());
        if (getFormDataList() != null) {
            for (FormData formData : getFormDataList()) {
                step.getFormDataList().add(formData.copy());
            }
        }
        return step;
    }

    public Boolean getExpectedResponseIgnore() {
        return expectedResponseIgnore != null && expectedResponseIgnore;
    }

    public Boolean getUsePolling() {
        return usePolling != null && usePolling;
    }

    public Boolean getDisabled() {
        return disabled != null && disabled;
    }

    public Boolean getMultipartFormData() {
        return multipartFormData != null && multipartFormData;
    }
}
