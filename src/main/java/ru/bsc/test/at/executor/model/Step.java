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
@SuppressWarnings({"WeakerAccess", "unused"})
public class Step extends AbstractModel implements Serializable {

    private String code;
    private List<ExpectedServiceRequest> expectedServiceRequests;
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
    private List<MockServiceResponse> mockServiceResponseList;
    private Boolean disabled;
    private String stepComment;
    private Map<String, String> savedValuesCheck;
    private ResponseCompareMode responseCompareMode;
    private List<StepParameterSet> stepParameterSetList;
    private String mqName;
    private String mqMessage;
    private String mqMessageFile;
    private List<FormData> formDataList;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
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
        if (mockServiceResponseList == null) {
            mockServiceResponseList = new LinkedList<>();
        }
        return mockServiceResponseList;
    }
    public void setMockServiceResponseList(List<MockServiceResponse> mockServiceResponseList) {
        if (mockServiceResponseList == null) {
            mockServiceResponseList = new LinkedList<>();
        }
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
        if (stepParameterSetList == null) {
            stepParameterSetList = new LinkedList<>();
        }
        return stepParameterSetList;
    }
    public void setStepParameterSetList(List<StepParameterSet> stepParameterSetList) {
        this.stepParameterSetList = stepParameterSetList;
    }
    public String getMqName() {
        return mqName;
    }
    public void setMqName(String mqName) {
        this.mqName = mqName;
    }
    public String getMqMessage() {
        return mqMessage;
    }
    public void setMqMessage(String mqMessage) {
        this.mqMessage = mqMessage;
    }
    public String getMqMessageFile() {
        return mqMessageFile;
    }
    public void setMqMessageFile(String mqMessageFile) {
        this.mqMessageFile = mqMessageFile;
    }

    public ResponseCompareMode getResponseCompareMode() {
        return responseCompareMode;
    }

    public void setResponseCompareMode(ResponseCompareMode responseCompareMode) {
        this.responseCompareMode = responseCompareMode;
    }

    public List<FormData> getFormDataList() {
        return formDataList;
    }

    public void setFormDataList(List<FormData> formDataList) {
        this.formDataList = formDataList;
    }

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
        if (getSavedValuesCheck() != null) {
            step.setSavedValuesCheck(new HashMap<>(getSavedValuesCheck()));
        }
        step.setResponseCompareMode(getResponseCompareMode());
        step.setExpectedServiceRequests(new LinkedList<>());
        for (ExpectedServiceRequest expectedServiceRequest: getExpectedServiceRequests()) {
            step.getExpectedServiceRequests().add(expectedServiceRequest.copy());
        }
        step.setMockServiceResponseList(new LinkedList<>());
        for (MockServiceResponse mockServiceResponse: getMockServiceResponseList()) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Step step = (Step) o;

        if (relativeUrl != null ? !relativeUrl.equals(step.relativeUrl) : step.relativeUrl != null) return false;
        if (requestMethod != null ? !requestMethod.equals(step.requestMethod) : step.requestMethod != null)
            return false;
        if (request != null ? !request.equals(step.request) : step.request != null) return false;
        if (requestFile != null ? !requestFile.equals(step.requestFile) : step.requestFile != null) return false;
        if (requestHeaders != null ? !requestHeaders.equals(step.requestHeaders) : step.requestHeaders != null)
            return false;
        if (expectedResponse != null ? !expectedResponse.equals(step.expectedResponse) : step.expectedResponse != null)
            return false;
        if (expectedResponseFile != null ? !expectedResponseFile.equals(step.expectedResponseFile) : step.expectedResponseFile != null)
            return false;
        if (expectedResponseIgnore != null ? !expectedResponseIgnore.equals(step.expectedResponseIgnore) : step.expectedResponseIgnore != null)
            return false;
        if (expectedStatusCode != null ? !expectedStatusCode.equals(step.expectedStatusCode) : step.expectedStatusCode != null)
            return false;
        if (sql != null ? !sql.equals(step.sql) : step.sql != null) return false;
        if (sqlSavedParameter != null ? !sqlSavedParameter.equals(step.sqlSavedParameter) : step.sqlSavedParameter != null)
            return false;
        if (jsonXPath != null ? !jsonXPath.equals(step.jsonXPath) : step.jsonXPath != null) return false;
        if (requestBodyType != step.requestBodyType) return false;
        if (usePolling != null ? !usePolling.equals(step.usePolling) : step.usePolling != null) return false;
        if (pollingJsonXPath != null ? !pollingJsonXPath.equals(step.pollingJsonXPath) : step.pollingJsonXPath != null)
            return false;
        if (disabled != null ? !disabled.equals(step.disabled) : step.disabled != null) return false;
        if (stepComment != null ? !stepComment.equals(step.stepComment) : step.stepComment != null) return false;
        return savedValuesCheck != null ? savedValuesCheck.equals(step.savedValuesCheck) : step.savedValuesCheck == null;
    }

    @Override
    public int hashCode() {
        int result = relativeUrl != null ? relativeUrl.hashCode() : 0;
        result = 31 * result + (requestMethod != null ? requestMethod.hashCode() : 0);
        result = 31 * result + (request != null ? request.hashCode() : 0);
        result = 31 * result + (requestFile != null ? requestFile.hashCode() : 0);
        result = 31 * result + (requestHeaders != null ? requestHeaders.hashCode() : 0);
        result = 31 * result + (expectedResponse != null ? expectedResponse.hashCode() : 0);
        result = 31 * result + (expectedResponseFile != null ? expectedResponseFile.hashCode() : 0);
        result = 31 * result + (expectedResponseIgnore != null ? expectedResponseIgnore.hashCode() : 0);
        result = 31 * result + (expectedStatusCode != null ? expectedStatusCode.hashCode() : 0);
        result = 31 * result + (sql != null ? sql.hashCode() : 0);
        result = 31 * result + (sqlSavedParameter != null ? sqlSavedParameter.hashCode() : 0);
        result = 31 * result + (jsonXPath != null ? jsonXPath.hashCode() : 0);
        result = 31 * result + (requestBodyType != null ? requestBodyType.hashCode() : 0);
        result = 31 * result + (usePolling != null ? usePolling.hashCode() : 0);
        result = 31 * result + (pollingJsonXPath != null ? pollingJsonXPath.hashCode() : 0);
        result = 31 * result + (disabled != null ? disabled.hashCode() : 0);
        result = 31 * result + (stepComment != null ? stepComment.hashCode() : 0);
        result = 31 * result + (savedValuesCheck != null ? savedValuesCheck.hashCode() : 0);
        return result;
    }
}
