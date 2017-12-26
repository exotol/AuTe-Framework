package ru.bsc.test.at.executor.model;

/**
 * Created by sdoroshin on 12.05.2017.
 *
 */
public class StepResult {

    public final static String RESULT_OK = "OK";
    public final static String RESULT_FAIL = "Fail";

    private String testId;
    private Step step;
    private String result;
    private String details;
    private String expected;
    private String actual;
    private String requestUrl;
    private String requestBody;
    private Integer pollingRetryCount;
    private String savedParameters;
    private String description;
    private boolean editable;

    public StepResult(Step step) {
        this.step = step;
    }

    public String getTestId() {
        return testId;
    }
    public void setTestId(String testId) {
        this.testId = testId;
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
    public String getSavedParameters() {
        return savedParameters;
    }
    public void setSavedParameters(String savedParameters) {
        this.savedParameters = savedParameters;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }
}