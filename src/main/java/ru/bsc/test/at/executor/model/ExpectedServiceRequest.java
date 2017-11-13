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
    private String expectedServiceRequestFile;
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
    public String getExpectedServiceRequestFile() {
        return expectedServiceRequestFile;
    }
    public void setExpectedServiceRequestFile(String expectedServiceRequestFile) {
        this.expectedServiceRequestFile = expectedServiceRequestFile;
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
        cloned.setExpectedServiceRequestFile(getExpectedServiceRequestFile());
        cloned.setSort(getSort());
        cloned.setIgnoredTags(getIgnoredTags());

        return cloned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpectedServiceRequest that = (ExpectedServiceRequest) o;

        if (step != null ? !step.equals(that.step) : that.step != null) return false;
        if (serviceName != null ? !serviceName.equals(that.serviceName) : that.serviceName != null) return false;
        if (expectedServiceRequest != null ? !expectedServiceRequest.equals(that.expectedServiceRequest) : that.expectedServiceRequest != null)
            return false;
        if (expectedServiceRequestFile != null ? !expectedServiceRequestFile.equals(that.expectedServiceRequestFile) : that.expectedServiceRequestFile != null)
            return false;
        return ignoredTags != null ? ignoredTags.equals(that.ignoredTags) : that.ignoredTags == null;
    }

    @Override
    public int hashCode() {
        int result = step != null ? step.hashCode() : 0;
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        result = 31 * result + (expectedServiceRequest != null ? expectedServiceRequest.hashCode() : 0);
        result = 31 * result + (expectedServiceRequestFile != null ? expectedServiceRequestFile.hashCode() : 0);
        result = 31 * result + (ignoredTags != null ? ignoredTags.hashCode() : 0);
        return result;
    }
}
