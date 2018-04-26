package ru.bsc.test.at.executor.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

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
    private String contentType;
    private String userName;
    private String password;
    private String pathFilter;
    private List<HeaderItem> headers;

    protected MockServiceResponse copy() {
        MockServiceResponse response = new MockServiceResponse();
        response.setServiceUrl(getServiceUrl());
        response.setResponseBody(getResponseBody());
        // TODO: cloned.setResponseBodyFile(getResponseBodyFile());
        response.setHttpStatus(getHttpStatus());
        response.setContentType(getContentType());
        response.setUserName(getUserName());
        response.setPassword(getPassword());
        response.setPathFilter(getPathFilter());
        if(headers != null) {
            response.setHeaders(headers.stream().map(h -> h.copy()).collect(Collectors.toList()));
        }
        return response;
    }
}
