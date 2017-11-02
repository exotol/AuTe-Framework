package ru.bsc.test.autotester.ro;

import ru.bsc.test.autotester.dto.AbstractRo;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
public class MockServiceResponseRo extends AbstractRo {

    private static final long serialVersionUID = -7918346254164488513L;

    private Long id;
    private Long sort;
    private String serviceUrl;
    private String responseBody;
    private Integer httpStatus;

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
}
