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
    private List<ExpectedServiceRequest> expectedServiceRequests = new LinkedList<>();
    private String relativeUrl;
    private String requestMethod;
    private String request;
    private String requestFile;
    private String requestHeaders;
    private String expectedResponse;
    private String expectedResponseFile;
    private Boolean expectedResponseIgnore;
    private Integer expectedStatusCode;
    private String jsonXPath;
    private RequestBodyType requestBodyType = RequestBodyType.JSON;
    private Boolean usePolling;
    private String pollingJsonXPath;
    private List<MockServiceResponse> mockServiceResponseList = new LinkedList<>();
    private Boolean disabled;
    private String stepComment;
    private Map<String, String> savedValuesCheck = new HashMap<>();
    private ResponseCompareMode responseCompareMode = ResponseCompareMode.JSON;
    private List<StepParameterSet> stepParameterSetList = new LinkedList<>();
    private String mqName;
    private String mqMessage;
    private String mqMessageFile;
    private List<NameValueProperty> mqPropertyList = new LinkedList<>();
    private Boolean multipartFormData;
    private List<FormData> formDataList = new LinkedList<>();
    private String jsonCompareMode = "NON_EXTENSIBLE";
    private String script;
    private String numberRepetitions;
    private String parseMockRequestUrl;
    private String parseMockRequestXPath;
    private String parseMockRequestScenarioVariable;
    private String timeoutMs;
    private List<MqMockResponse> mqMockResponseList;
    private List<ExpectedMqRequest> expectedMqRequestList;
    private List<SqlData> sqlDataList = new LinkedList<>();
    @Deprecated
    private String sql;
    @Deprecated
    private String sqlSavedParameter;
    private List<ScenarioVariableFromMqRequest> scenarioVariableFromMqRequestList;
    private StepMode stepMode;

    // JMS step mode
    private String mqOutputQueueName;
    private String mqInputQueueName;
    private String mqTimeoutMs;

    public Step copy() {
        Step step = new Step();
        step.setRelativeUrl(getRelativeUrl());
        step.setRequestMethod(getRequestMethod());
        step.setRequestHeaders(getRequestHeaders());
        step.setRequest(getRequest());
        step.setExpectedResponse(getExpectedResponse());
        step.setExpectedStatusCode(getExpectedStatusCode());
        step.setJsonXPath(getJsonXPath());
        step.setRequestBodyType(getRequestBodyType());
        step.setExpectedResponseIgnore(getExpectedResponseIgnore());
        step.setUsePolling(getUsePolling());
        step.setPollingJsonXPath(getPollingJsonXPath());
        step.setDisabled(getDisabled());
        step.setStepComment(getStepComment());
        step.setScript(getScript());
        step.setSavedValuesCheck(new HashMap<>(getSavedValuesCheck()));
        step.setResponseCompareMode(getResponseCompareMode());
        if (getExpectedServiceRequests() != null) {
            step.setExpectedServiceRequests(new LinkedList<>());
            for (ExpectedServiceRequest expectedServiceRequest : getExpectedServiceRequests()) {
                step.getExpectedServiceRequests().add(expectedServiceRequest.copy());
            }
        }
        if (getMockServiceResponseList() != null) {
            step.setMockServiceResponseList(new LinkedList<>());
            for (MockServiceResponse mockServiceResponse : getMockServiceResponseList()) {
                step.getMockServiceResponseList().add(mockServiceResponse.copy());
            }
        }
        if (getStepParameterSetList() != null) {
            step.setStepParameterSetList(new LinkedList<>());
            for (StepParameterSet stepParameterSet : getStepParameterSetList()) {
                step.getStepParameterSetList().add(stepParameterSet.copy());
            }
        }
        if (getFormDataList() != null) {
            step.setFormDataList(new LinkedList<>());
            for (FormData formData : getFormDataList()) {
                step.getFormDataList().add(formData.copy());
            }
        }
        if (getMqPropertyList() != null) {
            step.setMqPropertyList(new LinkedList<>());
            for (NameValueProperty property : getMqPropertyList()) {
                step.getMqPropertyList().add(property.copy());
            }
        }
        if (getSqlDataList() != null) {
            step.setSqlDataList(new LinkedList<>());
            for (SqlData sqlData : getSqlDataList()) {
                step.getSqlDataList().add(sqlData.copy());
            }
        }

        step.setMqName(getMqName());
        step.setMqMessage(getMqMessage());
        step.setMqMessageFile(getMqMessageFile());
        step.setMultipartFormData(getMultipartFormData());
        step.setJsonCompareMode(getJsonCompareMode());
        step.setNumberRepetitions(getNumberRepetitions());
        step.setParseMockRequestUrl(getParseMockRequestUrl());
        step.setParseMockRequestXPath(getParseMockRequestXPath());
        step.setParseMockRequestScenarioVariable(getParseMockRequestScenarioVariable());
        step.setTimeoutMs(getTimeoutMs());
        step.setStepMode(getStepMode());

        if (step.getMqMockResponseList() == null) {
            step.setMqMockResponseList(new LinkedList<>());
        }
        step.getMqMockResponseList().clear();
        if (getMqMockResponseList() != null) {
            for (MqMockResponse mqMockResponse : getMqMockResponseList()) {
                step.getMqMockResponseList().add(mqMockResponse.copy());
            }
        }

        if (getExpectedMqRequestList() != null) {
            step.setExpectedMqRequestList(new LinkedList<>());
            for (ExpectedMqRequest expectedMqRequest : getExpectedMqRequestList()) {
                step.getExpectedMqRequestList().add(expectedMqRequest.copy());
            }
        }

        if (getSqlDataList() != null) {
            step.setSqlDataList(new LinkedList<>());
            for (SqlData sqlData : getSqlDataList()) {
                step.getSqlDataList().add(sqlData.copy());
            }
        }

        step.setSql(getSql());
        step.setSqlSavedParameter(getSqlSavedParameter());

        if (getScenarioVariableFromMqRequestList() != null) {
            step.setScenarioVariableFromMqRequestList(new LinkedList<>());
            for (ScenarioVariableFromMqRequest variable : getScenarioVariableFromMqRequestList()) {
                step.getScenarioVariableFromMqRequestList().add(variable.copy());
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
