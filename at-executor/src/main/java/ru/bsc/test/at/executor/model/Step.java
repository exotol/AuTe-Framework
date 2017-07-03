package ru.bsc.test.at.executor.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
@Entity
@Table(name = "AT_STEP")
public class Step implements Serializable, Cloneable {

    public enum RequestBodyType {
        @SuppressWarnings("unused")
        JSON,
        FORM
    }

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_STEP", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "SCENARIO_ID")
    private Scenario scenario;
    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL)
    @OrderBy("SORT ASC")
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
    private RequestBodyType requestBodyType;

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

    @Override
    protected Step clone() throws CloneNotSupportedException {
        super.clone();
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

        cloned.setExpectedServiceRequests(new LinkedList<>());
        for (ExpectedServiceRequest expectedServiceRequest: getExpectedServiceRequests()) {
            ExpectedServiceRequest clonedExpectedServiceRequest = expectedServiceRequest.clone();
            clonedExpectedServiceRequest.setStep(cloned);
            cloned.getExpectedServiceRequests().add(clonedExpectedServiceRequest);
        }

        return cloned;
    }
}
