package ru.bsc.test.autotester.ro;

import java.util.List;
import java.util.Map;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
public class StepRo implements AbstractRo {

    private static final long serialVersionUID = -4795596079038167133L;

    private String code;
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
    private String requestBodyType;
    private Boolean usePolling;
    private String pollingJsonXPath;
    private List<MockServiceResponseRo> mockServiceResponseList;
    private Boolean disabled;
    private String stepComment;
    private Map<String, String> savedValuesCheck;
    private List<StepParameterSetRo> stepParameterSetList;
    private List<ExpectedServiceRequestRo> expectedServiceRequestList;
    private String mqName;
    private String mqMessage;
    private String mqMessageFile;
    private String responseCompareMode;
    private List<FormDataRo> formDataList;
    private Boolean multipartFormData;
    private String jsonCompareMode;
    private String script;

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

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
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

    public Boolean getExpectedResponseIgnore() {
        return expectedResponseIgnore;
    }

    public void setExpectedResponseIgnore(Boolean expectedResponseIgnore) {
        this.expectedResponseIgnore = expectedResponseIgnore;
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

    public String getRequestBodyType() {
        return requestBodyType;
    }

    public void setRequestBodyType(String requestBodyType) {
        this.requestBodyType = requestBodyType;
    }

    public Boolean getUsePolling() {
        return usePolling;
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

    public List<MockServiceResponseRo> getMockServiceResponseList() {
        return mockServiceResponseList;
    }

    public void setMockServiceResponseList(List<MockServiceResponseRo> mockServiceResponseList) {
        this.mockServiceResponseList = mockServiceResponseList;
    }

    public Boolean getDisabled() {
        return disabled;
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
        this.savedValuesCheck = savedValuesCheck;
    }

    public List<StepParameterSetRo> getStepParameterSetList() {
        return stepParameterSetList;
    }

    public void setStepParameterSetList(List<StepParameterSetRo> stepParameterSetList) {
        this.stepParameterSetList = stepParameterSetList;
    }

    public List<ExpectedServiceRequestRo> getExpectedServiceRequestList() {
        return expectedServiceRequestList;
    }

    public void setExpectedServiceRequestList(List<ExpectedServiceRequestRo> expectedServiceRequestList) {
        this.expectedServiceRequestList = expectedServiceRequestList;
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

    public String getResponseCompareMode() {
        return responseCompareMode;
    }

    public void setResponseCompareMode(String responseCompareMode) {
        this.responseCompareMode = responseCompareMode;
    }

    public List<FormDataRo> getFormDataList() {
        return formDataList;
    }

    public void setFormDataList(List<FormDataRo> formDataList) {
        this.formDataList = formDataList;
    }

    public Boolean getMultipartFormData() {
        return multipartFormData;
    }

    public void setMultipartFormData(Boolean multipartFormData) {
        this.multipartFormData = multipartFormData;
    }

    public String getJsonCompareMode() {
        return jsonCompareMode;
    }

    public void setJsonCompareMode(String jsonCompareMode) {
        this.jsonCompareMode = jsonCompareMode;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
