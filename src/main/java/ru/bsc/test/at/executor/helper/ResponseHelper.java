package ru.bsc.test.at.executor.helper;

import java.util.List;
import java.util.Map;

/**
 * Created by sdoroshin on 22.05.2017.
 *
 */
public class ResponseHelper {
    private final int statusCode;
    private final String content;
    private final Map<String, List<String>> headers;

    ResponseHelper(int statusCode, String content, Map<String, List<String>> responseHeaders) {
        this.statusCode = statusCode;
        this.content = content;
        this.headers = responseHeaders;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getContent() {
        return content;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }
}
