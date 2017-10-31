package ru.bsc.test.autotester.ro;

/**
 * Created by sdoroshin on 28.09.2017.
 *
 */
public class ExpectedServiceRequestRo extends AbstractRo {

    private Long id;
    private Long sort;
    private String serviceName;
    private String expectedServiceRequest;
    private String ignoredTags;

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
}
