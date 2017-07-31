package ru.bsc.test.at.executor.wiremock.mockdefinition;

import java.util.Map;

/**
 * Created by sdoroshin on 27.07.2017.
 *
 */
@SuppressWarnings("unused")
public class MockResponse {
    private Integer status = 200;
    private String body;
    private Map<String, String> headers;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
