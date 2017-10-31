package ru.bsc.test.at.executor.model;

/**
 * Created by sdoroshin on 27.07.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
public class MockServiceResponse extends AbstractModel {

    private Step step;
    private Long sort;
    private String serviceUrl;
    private String responseBody;
    private Integer httpStatus;

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
