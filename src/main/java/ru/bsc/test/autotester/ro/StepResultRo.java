package ru.bsc.test.autotester.ro;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
public class StepResultRo {
    private String testId;
    private StepRo step;
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
    private String cookies;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public StepRo getStep() {
        return step;
    }

    public void setStep(StepRo step) {
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

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Integer getPollingRetryCount() {
        return pollingRetryCount;
    }

    public void setPollingRetryCount(Integer pollingRetryCount) {
        this.pollingRetryCount = pollingRetryCount;
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

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }
}
