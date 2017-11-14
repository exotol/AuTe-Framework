package ru.bsc.test.at.executor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
@Entity
@Table(name = "AT_STEP")
public class Step {

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_STEP", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "SCENARIO_ID")
    @JsonBackReference
    private Scenario scenario;
    @OneToMany(mappedBy = "step", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    @OrderBy("SORT ASC")
    @JsonManagedReference
    private List<ExpectedServiceRequest> expectedServiceRequests;
    @Column(name = "SORT")
    private Long sort;
    @Column(name = "RELATIVE_URL", length = 500)
    private String relativeUrl;
    @Column(name = "REQUEST_METHOD", length = 6)
    private String requestMethod;
    @Column(name = "REQUEST", columnDefinition = "CLOB")
    private String request;
    @Column(name = "REQUEST_HEADERS", length = 500)
    private String requestHeaders;
    @Column(name = "EXPECTED_RESPONSE", columnDefinition = "CLOB")
    private String expectedResponse;
    @Column(name = "EXPECTED_RESPONSE_IGNORE")
    private Boolean expectedResponseIgnore;
    @Column(name = "SAVING_VALUES", length = 100)
    private String savingValues;
    @Column(name = "RESPONSES", length = 500)
    private String responses;
    @Column(name = "DB_PARAMS", length = 500)
    private String dbParams;
    @Column(name = "TMP_SERVICE_REQUESTS_DIRECTORY", length = 150)
    private String tmpServiceRequestsDirectory;
    @Column(name = "EXPECTED_STATUS_CODE")
    private Integer expectedStatusCode;
    @Column(name = "SQL", length = 300)
    private String sql;
    @Column(name = "SQL_SAVED_PARAMETER")
    private String sqlSavedParameter;
    @Column(name = "JSON_XPATH", length = 500)
    private String jsonXPath;
    @Column(name = "REQUEST_BODY_TYPE")
    @Enumerated
    private RequestBodyType requestBodyType;
    @Column(name = "USE_POLLING")
    private Boolean usePolling;
    @Column(name = "POLLING_JSON_XPATH")
    private String pollingJsonXPath;
    @OneToMany(mappedBy = "step", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    @OrderBy("SORT ASC")
    @JsonManagedReference
    private List<MockServiceResponse> mockServiceResponseList;
    private Boolean disabled;
    private String stepComment;
    @ElementCollection
    @MapKeyColumn(name = "savedValueName")
    @CollectionTable(name = "AT_STEP_SAVED_VALUES_CHECK", joinColumns = @JoinColumn(name = "STEP_ID"))
    private Map<String, String> savedValuesCheck;
    @Column(name = "RESPONSE_COMPARE_MODE")
    @Enumerated
    private ResponseCompareMode responseCompareMode;
    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ID ASC")
    @JsonManagedReference
    private List<StepParameterSet> stepParameterSetList;
    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ID ASC")
    @JsonManagedReference
    private List<FormData> formDataList;

    public Step() {
    }

    public Step(String relativeUrl, String requestMethod, String request, String requestHeaders, String expectedResponse, String savingValues, String responses, String dbParams, String tmpServiceRequestsDirectory) {
        this.relativeUrl = relativeUrl;
        this.requestMethod = requestMethod;
        this.request = request;
        this.requestHeaders = requestHeaders;
        this.expectedResponse = expectedResponse;
        this.savingValues = savingValues;
        this.responses = responses;
        this.dbParams = dbParams;
        this.tmpServiceRequestsDirectory = tmpServiceRequestsDirectory;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSort() {
        return sort;
    }
    public void setSort(Long sort) {
        this.sort = sort;
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
    public String getExpectedResponse() {
        return expectedResponse;
    }
    public void setExpectedResponse(String expectedResponse) {
        this.expectedResponse = expectedResponse;
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
        step.setId(null);
        step.setSort(getSort());
        step.setRelativeUrl(getRelativeUrl());
        step.setRequestMethod(getRequestMethod());
        step.setRequestHeaders(getRequestHeaders());
        step.setRequest(getRequest());
        step.setExpectedResponse(getExpectedResponse());
        step.setSavingValues(getSavingValues());
        step.setResponses(getResponses());
        step.setDbParams(getDbParams());
        step.setTmpServiceRequestsDirectory(getTmpServiceRequestsDirectory());
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
        step.setSavedValuesCheck(new HashMap<>(getSavedValuesCheck()));
        step.setResponseCompareMode(getResponseCompareMode());
        step.setExpectedServiceRequests(new LinkedList<>());
        for (ExpectedServiceRequest expectedServiceRequest: getExpectedServiceRequests()) {
            ExpectedServiceRequest stepExpectedServiceRequest = expectedServiceRequest.copy();
            stepExpectedServiceRequest.setStep(step);
            step.getExpectedServiceRequests().add(stepExpectedServiceRequest);
        }
        step.setMockServiceResponseList(new LinkedList<>());
        for (MockServiceResponse mockServiceResponse: getMockServiceResponseList()) {
            MockServiceResponse stepMockServiceResponse = mockServiceResponse.copy();
            stepMockServiceResponse.setStep(step);
            step.getMockServiceResponseList().add(stepMockServiceResponse);
        }
        step.setStepParameterSetList(new LinkedList<>());
        if (getStepParameterSetList() != null) {
            for (StepParameterSet stepParameterSet : getStepParameterSetList()) {
                StepParameterSet stepStepParameterSet = stepParameterSet.copy();
                stepStepParameterSet.setStep(step);
                step.getStepParameterSetList().add(stepStepParameterSet);
            }
        }
        step.setFormDataList(new LinkedList<>());
        for (FormData formData : getFormDataList()) {
            FormData stepFormData = formData.copy();
            stepFormData.setStep(step);
            step.getFormDataList().add(stepFormData);
        }
        return step;
    }
}
