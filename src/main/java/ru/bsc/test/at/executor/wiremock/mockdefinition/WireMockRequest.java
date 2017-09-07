package ru.bsc.test.at.executor.wiremock.mockdefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sdoroshin on 06.09.2017.
 *
 */
public class WireMockRequest {
    private String method;
    private String url;
    private Map<String, String> headers = new HashMap<>();
    private String body;

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

    public Map<String, String> getHeaders() {
        if (headers == null) {
            headers = new HashMap<>();
        }
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
