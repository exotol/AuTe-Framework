package ru.bsc.test.at.executor.model;

/**
 * Created by sdoroshin on 27.07.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
public class MockServiceResponse extends AbstractModel {

    private String serviceUrl;
    private String responseBody;
    private String responseBodyFile;
    private Integer httpStatus;

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

    protected MockServiceResponse copy() {
        MockServiceResponse response = new MockServiceResponse();
        response.setSort(getSort());
        response.setServiceUrl(getServiceUrl());
        response.setResponseBody(getResponseBody());
        // TODO: cloned.setResponseBodyFile(getResponseBodyFile());
        response.setHttpStatus(getHttpStatus());
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MockServiceResponse that = (MockServiceResponse) o;

        if (serviceUrl != null ? !serviceUrl.equals(that.serviceUrl) : that.serviceUrl != null) return false;
        if (responseBody != null ? !responseBody.equals(that.responseBody) : that.responseBody != null) return false;
        if (responseBodyFile != null ? !responseBodyFile.equals(that.responseBodyFile) : that.responseBodyFile != null)
            return false;
        return httpStatus != null ? httpStatus.equals(that.httpStatus) : that.httpStatus == null;
    }

    @Override
    public int hashCode() {
        int result = serviceUrl != null ? serviceUrl.hashCode() : 0;
        result = 31 * result + (responseBody != null ? responseBody.hashCode() : 0);
        result = 31 * result + (responseBodyFile != null ? responseBodyFile.hashCode() : 0);
        result = 31 * result + (httpStatus != null ? httpStatus.hashCode() : 0);
        return result;
    }
}
