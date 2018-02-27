package ru.bsc.test.at.executor.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by sdoroshin on 27.07.2017.
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = "code")
public class MockServiceResponse implements AbstractModel {

    private String code;
    private String serviceUrl;
    private String responseBody;
    private String responseBodyFile;
    private Integer httpStatus;

    protected MockServiceResponse copy() {
        MockServiceResponse response = new MockServiceResponse();
        response.setServiceUrl(getServiceUrl());
        response.setResponseBody(getResponseBody());
        // TODO: cloned.setResponseBodyFile(getResponseBodyFile());
        response.setHttpStatus(getHttpStatus());
        return response;
    }
}
