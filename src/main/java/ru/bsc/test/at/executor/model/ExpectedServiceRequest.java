package ru.bsc.test.at.executor.model;

import java.io.Serializable;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
public class ExpectedServiceRequest extends AbstractModel implements Serializable {

    private Step step;
    private String serviceName;
    private String expectedServiceRequest;
    private String ignoredTags;

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
    public String getIgnoredTags() {
        return ignoredTags;
    }
    public void setIgnoredTags(String ignoredTags) {
        this.ignoredTags = ignoredTags;
    }

    protected ExpectedServiceRequest clone() {
        ExpectedServiceRequest cloned = new ExpectedServiceRequest();
        cloned.setId(null);
        cloned.setServiceName(getServiceName());
        cloned.setExpectedServiceRequest(getExpectedServiceRequest());
        cloned.setSort(getSort());
        cloned.setIgnoredTags(getIgnoredTags());

        return cloned;
    }
}
