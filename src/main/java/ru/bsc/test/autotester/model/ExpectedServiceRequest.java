package ru.bsc.test.autotester.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@Entity
@Table(name = "AT_EXPECTED_SERVICE_REQUEST")
public class ExpectedServiceRequest implements Serializable {

    public ExpectedServiceRequest() {
    }

    public ExpectedServiceRequest(Step step, String serviceName, String expectedServiceRequest, Long sort) {
        this.step = step;
        this.serviceName = serviceName;
        this.expectedServiceRequest = expectedServiceRequest;
        this.sort = sort;
    }

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_EXPECTED_SERVICE_REQUEST", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = Step.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "STEP_ID")
    private Step step;

    @Column(name = "SERVICE_NAME")
    private String serviceName;

    @Column(name = "EXPECTED_SERVICE_REQUEST")
    private String expectedServiceRequest;

    @Column(name = "SORT")
    private Long sort;

    @Column(name = "IGNORED_TAGS")
    private String ignoredTags;

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
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getExpectedServiceRequest() {
        return expectedServiceRequest;
    }
    public void setExpectedServiceRequest(String expectedServiceRequest) {
        this.expectedServiceRequest = expectedServiceRequest;
    }
    public Long getSort() {
        return sort;
    }
    public void setSort(Long sort) {
        this.sort = sort;
    }
    public String getIgnoredTags() {
        return ignoredTags;
    }
    public void setIgnoredTags(String ignoredTags) {
        this.ignoredTags = ignoredTags;
    }
}
