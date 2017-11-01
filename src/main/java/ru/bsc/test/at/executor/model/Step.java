package ru.bsc.test.at.executor.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
public class Step extends AbstractModel implements Serializable {

    public enum RequestBodyType {
        @SuppressWarnings("unused")
        JSON,
        FORM
    }

    private Scenario scenario;
    private List<ExpectedServiceRequest> expectedServiceRequests;
    private String relativeUrl;
    private String requestMethod;
    private String request;
    private String requestFile;
    private String requestHeaders;
    private String expectedResponse;
    private String expectedResponseFile;
    private Boolean expectedResponseIgnore;
    private String savingValues;
    private String responses;
    private String dbParams;
    private String tmpServiceRequestsDirectory;
    private Integer expectedStatusCode;
    private String sql;
    private String sqlSavedParameter;
    private String jsonXPath;
    private RequestBodyType requestBodyType;
    private Boolean usePolling;
    private String pollingJsonXPath;
    private List<MockServiceResponse> mockServiceResponseList;
    private Boolean disabled;
    private String stepComment;
    private Map<String, String> savedValuesCheck;
    private List<StepParameterSet> stepParameterSetList;

    public String getRelativeUrl() {
        return relativeUrl;
    }
    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }
    public String getRequestMethod() {
        return requestMethod;
    }
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
    public String getRequestHeaders() {
        return requestHeaders;
    }
    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }
    public String getRequest() {
        return request;
    }
    public void setRequest(String request) {
        this.request = request;
    }
    public String getRequestFile() {
        return requestFile;
    }
    public void setRequestFile(String requestFile) {
        this.requestFile = requestFile;
    }
    public String getExpectedResponse() {
        return expectedResponse;
    }
    public void setExpectedResponse(String expectedResponse) {
        this.expectedResponse = expectedResponse;
    }
    public String getExpectedResponseFile() {
        return expectedResponseFile;
    }
    public void setExpectedResponseFile(String expectedResponseFile) {
        this.expectedResponseFile = expectedResponseFile;
    }
    public String getSavingValues() {
        return savingValues;
    }
    public void setSavingValues(String savingValues) {
        this.savingValues = savingValues;
    }
    public String getResponses() {
        return responses;
    }
    public void setResponses(String responses) {
        this.responses = responses;
    }
    public String getDbParams() {
        return dbParams;
    }
    public void setDbParams(String dbParams) {
        this.dbParams = dbParams;
    }
    public String getTmpServiceRequestsDirectory() {
        return tmpServiceRequestsDirectory;
    }
    public void setTmpServiceRequestsDirectory(String tmpServiceRequestsDirectory) {
        this.tmpServiceRequestsDirectory = tmpServiceRequestsDirectory;
    }
    public Integer getExpectedStatusCode() {
        return expectedStatusCode;
    }
    public void setExpectedStatusCode(Integer expectedStatusCode) {
        this.expectedStatusCode = expectedStatusCode;
    }
    public String getSql() {
        return sql;
    }
    public void setSql(String sql) {
        this.sql = sql;
    }
    public String getSqlSavedParameter() {
        return sqlSavedParameter;
    }
    public void setSqlSavedParameter(String sqlSavedParameter) {
        this.sqlSavedParameter = sqlSavedParameter;
    }
    public String getJsonXPath() {
        return jsonXPath;
    }
    public void setJsonXPath(String jsonXPath) {
        this.jsonXPath = jsonXPath;
    }
    public Scenario getScenario() {
        return scenario;
    }
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public List<ExpectedServiceRequest> getExpectedServiceRequests() {
        if (expectedServiceRequests == null) {
            expectedServiceRequests = new LinkedList<>();
        }
        return expectedServiceRequests;
    }
    public void setExpectedServiceRequests(List<ExpectedServiceRequest> expectedServiceRequests) {
        this.expectedServiceRequests = expectedServiceRequests;
    }
    public RequestBodyType getRequestBodyType() {
        return requestBodyType;
    }
    public void setRequestBodyType(RequestBodyType requestBodyType) {
        this.requestBodyType = requestBodyType;
    }
    public Boolean getExpectedResponseIgnore() {
        return expectedResponseIgnore == null ? false : expectedResponseIgnore;
    }
    public void setExpectedResponseIgnore(Boolean expectedResponseIgnore) {
        this.expectedResponseIgnore = expectedResponseIgnore;
    }
    public Boolean getUsePolling() {
        return usePolling == null ? false : usePolling;
    }
    public void setUsePolling(Boolean usePolling) {
        this.usePolling = usePolling;
    }
    public String getPollingJsonXPath() {
        return pollingJsonXPath;
    }
    public void setPollingJsonXPath(String pollingJsonXPath) {
        this.pollingJsonXPath = pollingJsonXPath;
    }
    public List<MockServiceResponse> getMockServiceResponseList() {
        return mockServiceResponseList;
    }
    public void setMockServiceResponseList(List<MockServiceResponse> mockServiceResponseList) {
        this.mockServiceResponseList = mockServiceResponseList;
    }
    public Boolean getDisabled() {
        return disabled == null ? false : disabled;
    }
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
    public String getStepComment() {
        return stepComment;
    }
    public void setStepComment(String stepComment) {
        this.stepComment = stepComment;
    }
    public Map<String, String> getSavedValuesCheck() {
        return savedValuesCheck;
    }
    public void setSavedValuesCheck(Map<String, String> savedValuesCheck) {
        if (savedValuesCheck == null) {
            savedValuesCheck = new HashMap<>();
        }
        this.savedValuesCheck = savedValuesCheck;
    }
    public List<StepParameterSet> getStepParameterSetList() {
        return stepParameterSetList;
    }
    public void setStepParameterSetList(List<StepParameterSet> stepParameterSetList) {
        this.stepParameterSetList = stepParameterSetList;
    }

    public Step clone() {
        Step cloned = new Step();
        cloned.setId(null);
        cloned.setSort(getSort());
        cloned.setRelativeUrl(getRelativeUrl());
        cloned.setRequestMethod(getRequestMethod());
        cloned.setRequestHeaders(getRequestHeaders());
        cloned.setRequest(getRequest());
        cloned.setExpectedResponse(getExpectedResponse());
        cloned.setSavingValues(getSavingValues());
        cloned.setResponses(getResponses());
        cloned.setDbParams(getDbParams());
        cloned.setTmpServiceRequestsDirectory(getTmpServiceRequestsDirectory());
        cloned.setExpectedStatusCode(getExpectedStatusCode());
        cloned.setSql(getSql());
        cloned.setSqlSavedParameter(getSqlSavedParameter());
        cloned.setJsonXPath(getJsonXPath());
        cloned.setRequestBodyType(getRequestBodyType());
        cloned.setExpectedResponseIgnore(getExpectedResponseIgnore());
        cloned.setUsePolling(getUsePolling());
        cloned.setPollingJsonXPath(getPollingJsonXPath());
        cloned.setDisabled(getDisabled());
        cloned.setStepComment(getStepComment());
        cloned.setSavedValuesCheck(new HashMap<>(getSavedValuesCheck()));

        cloned.setExpectedServiceRequests(new LinkedList<>());
        for (ExpectedServiceRequest expectedServiceRequest: getExpectedServiceRequests()) {
            ExpectedServiceRequest clonedExpectedServiceRequest = expectedServiceRequest.clone();
            clonedExpectedServiceRequest.setStep(cloned);
            cloned.getExpectedServiceRequests().add(clonedExpectedServiceRequest);
        }

        cloned.setMockServiceResponseList(new LinkedList<>());
        for (MockServiceResponse mockServiceResponse: getMockServiceResponseList()) {
            MockServiceResponse clonedMockServiceResponse = mockServiceResponse.clone();
            clonedMockServiceResponse.setStep(cloned);
            cloned.getMockServiceResponseList().add(clonedMockServiceResponse);
        }

        cloned.setStepParameterSetList(new LinkedList<>());
        if (getStepParameterSetList() != null) {
            for (StepParameterSet stepParameterSet : getStepParameterSetList()) {
                StepParameterSet clonedStepParameterSet = stepParameterSet.copy();
                clonedStepParameterSet.setStep(cloned);
                cloned.getStepParameterSetList().add(clonedStepParameterSet);
            }
        }

        return cloned;
    }
}
