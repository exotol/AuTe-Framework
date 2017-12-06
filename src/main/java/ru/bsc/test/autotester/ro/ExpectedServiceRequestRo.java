package ru.bsc.test.autotester.ro;

/**
 * Created by sdoroshin on 28.09.2017.
 *
 */
public class ExpectedServiceRequestRo extends AbstractRo {

    private static final long serialVersionUID = -5748544019274406969L;

    private String code;
    private String serviceName;
    private String expectedServiceRequest;
    private String expectedServiceRequestFile;
    private String ignoredTags;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
