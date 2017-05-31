package ru.bsc.test.autotester.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@Entity
@Table(name = "AT_STEP")
public class Step implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_STEP", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "SCENARIO_ID")
    private Long scenarioId;

    @Column(name = "SORT")
    private Long sort;

    @Column(name = "RELATIVE_URL")
    private String relativeUrl;

    @Column(name = "REQUEST_METHOD")
    private String requestMethod;

    @Column(name = "REQUEST")
    private String request;

    @Column(name = "REQUEST_HEADERS")
    private String requestHeaders;

    @Column(name = "EXPECTED_RESPONSE")
    private String expectedResponse;

    @Column(name = "SAVING_VALUES")
    private String savingValues;

    @Column(name = "RESPONSES")
    private String responses;

    @Column(name = "DB_PARAMS")
    private String dbParams;

    @Column(name = "TMP_SERVICE_REQUESTS_DIRECTORY")
    private String tmpServiceRequestsDirectory;

    @Column(name = "EXPECTED_STATUS_CODE")
    private Integer expectedStatusCode;

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
    public Long getScenarioId() {
        return scenarioId;
    }
    public void setScenarioId(Long scenarioId) {
        this.scenarioId = scenarioId;
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
}
