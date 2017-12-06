package ru.bsc.test.autotester.ro;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
public class MockServiceResponseRo extends AbstractRo {

    private static final long serialVersionUID = -7918346254164488513L;

    private String code;
    private Long sort;
    private String serviceUrl;
    private String responseBody;
    private String responseBodyFile;
    private Integer httpStatus;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
