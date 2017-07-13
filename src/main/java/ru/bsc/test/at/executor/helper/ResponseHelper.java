package ru.bsc.test.at.executor.helper;

/**
 * Created by sdoroshin on 22.05.2017.
 *
 */
public class ResponseHelper {
    private int statusCode;
    private String content;

    ResponseHelper(int statusCode, String content) {
        this.statusCode = statusCode;
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getContent() {
        return content;
    }
}
