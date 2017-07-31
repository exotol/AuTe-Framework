package ru.bsc.test.at.executor.wiremock.mockdefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sdoroshin on 27.07.2017.
 *
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class MockRequest {
    private String method;
    private String url;
    private Map<String, Map<String, String>> headers = new HashMap<>();

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Map<String, String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Map<String, String>> headers) {
        this.headers = headers;
    }
}
