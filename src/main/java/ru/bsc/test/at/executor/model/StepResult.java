package ru.bsc.test.at.executor.model;

/**
 * Created by sdoroshin on 12.05.2017.
 *
 */
public class StepResult {
    private Step step;
    private String result;
    private String details;
    private String expected;
    private String actual;
    private String requestUrl;
    private String requestBody;
    private Integer pollingRetryCount;

    public StepResult(Step step) {
        this.step = step;
    }
    public Step getStep() {
        return step;
    }
    public void setStep(Step step) {
        this.step = step;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public String getExpected() {
        return expected;
    }
    public void setExpected(String expected) {
        this.expected = expected;
    }
    public String getActual() {
        return actual;
    }
    public void setActual(String actual) {
        this.actual = actual;
    }
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
    public String getRequestUrl() {
        return requestUrl;
    }
    public String getRequestBody() {
        return requestBody;
    }
    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
    public void setPollingRetryCount(Integer pollingRetryCount) {
        this.pollingRetryCount = pollingRetryCount;
    }
    public Integer getPollingRetryCount() {
        return pollingRetryCount;
    }
}