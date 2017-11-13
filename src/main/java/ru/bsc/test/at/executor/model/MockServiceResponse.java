package ru.bsc.test.at.executor.model;

/**
 * Created by sdoroshin on 27.07.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
public class MockServiceResponse extends AbstractModel {

    private Step step;
    private String serviceUrl;
    private String responseBody;
    private String responseBodyFile;
    private Integer httpStatus;

    public Step getStep() {
        return step;
    }
    public void setStep(Step step) {
        this.step = step;
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
    public String getResponseBodyFile() {
        return responseBodyFile;
    }
    public void setResponseBodyFile(String responseBodyFile) {
        this.responseBodyFile = responseBodyFile;
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
        cloned.setResponseBodyFile(getResponseBodyFile());
        cloned.setHttpStatus(getHttpStatus());
        return cloned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MockServiceResponse that = (MockServiceResponse) o;

        if (step != null ? !step.equals(that.step) : that.step != null) return false;
        if (serviceUrl != null ? !serviceUrl.equals(that.serviceUrl) : that.serviceUrl != null) return false;
        if (responseBody != null ? !responseBody.equals(that.responseBody) : that.responseBody != null) return false;
        if (responseBodyFile != null ? !responseBodyFile.equals(that.responseBodyFile) : that.responseBodyFile != null)
            return false;
        return httpStatus != null ? httpStatus.equals(that.httpStatus) : that.httpStatus == null;
    }

    @Override
    public int hashCode() {
        int result = step != null ? step.hashCode() : 0;
        result = 31 * result + (serviceUrl != null ? serviceUrl.hashCode() : 0);
        result = 31 * result + (responseBody != null ? responseBody.hashCode() : 0);
        result = 31 * result + (responseBodyFile != null ? responseBodyFile.hashCode() : 0);
        result = 31 * result + (httpStatus != null ? httpStatus.hashCode() : 0);
        return result;
    }
}
