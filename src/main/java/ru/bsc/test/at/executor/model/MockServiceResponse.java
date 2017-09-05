package ru.bsc.test.at.executor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by sdoroshin on 27.07.2017.
 *
 */
@Entity
@Table(name = "AT_MOCK_SERVICE_RESPONSE")
public class MockServiceResponse {

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_MOCK_SERVICE_RESPONSE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STEP_ID")
    @JsonBackReference
    private Step step;

    private Long sort;
    private String serviceUrl;
    @Column(columnDefinition = "CLOB")
    private String responseBody;
    private Integer httpStatus;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Step getStep() {
        return step;
    }
    public void setStep(Step step) {
        this.step = step;
    }
    public Long getSort() {
        return sort;
    }
    public void setSort(Long sort) {
        this.sort = sort;
    }
    public String getServiceUrl() {
        return serviceUrl;
    }
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
    public String getResponseBody() {
        return responseBody;
    }
    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
    public Integer getHttpStatus() {
        return httpStatus;
    }
    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    protected MockServiceResponse clone() {
        MockServiceResponse cloned = new MockServiceResponse();
        cloned.setSort(getSort());
        cloned.setServiceUrl(getServiceUrl());
        cloned.setResponseBody(getResponseBody());
        cloned.setHttpStatus(getHttpStatus());
        return cloned;
    }
}
