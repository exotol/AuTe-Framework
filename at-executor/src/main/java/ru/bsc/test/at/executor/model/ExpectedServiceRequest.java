package ru.bsc.test.at.executor.model;

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
@SuppressWarnings("WeakerAccess")
@Entity
@Table(name = "AT_EXPECTED_SERVICE_REQUEST")
public class ExpectedServiceRequest implements Serializable, Cloneable {

    public ExpectedServiceRequest() {
    }

    public ExpectedServiceRequest(Long stepId, String serviceName, String expectedServiceRequest, Long sort) {
        this.stepId = stepId;
        this.serviceName = serviceName;
        this.expectedServiceRequest = expectedServiceRequest;
        this.sort = sort;
    }

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_EXPECTED_SERVICE_REQUEST", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "STEP_ID")
    private Long stepId;
    @Column(name = "SERVICE_NAME", length = 100)
    private String serviceName;
    @Column(name = "EXPECTED_SERVICE_REQUEST", columnDefinition = "CLOB")
    private String expectedServiceRequest;
    @Column(name = "SORT")
    private Long sort;
    @Column(name = "IGNORED_TAGS", length = 300)
    private String ignoredTags;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getStepId() {
        return stepId;
    }
    @SuppressWarnings("unused")
    public void setStepId(Long stepId) {
        this.stepId = stepId;
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
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
    public void setIgnoredTags(String ignoredTags) {
        this.ignoredTags = ignoredTags;
    }

    @Override
    protected ExpectedServiceRequest clone() throws CloneNotSupportedException {
        super.clone();

        ExpectedServiceRequest cloned = new ExpectedServiceRequest();
        cloned.setId(null);
        cloned.setServiceName(getServiceName());
        cloned.setExpectedServiceRequest(getExpectedServiceRequest());
        cloned.setSort(getSort());
        cloned.setIgnoredTags(getIgnoredTags());

        return cloned;
    }
}
