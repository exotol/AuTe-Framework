package ru.bsc.test.at.executor.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Created by sdoroshin on 12.05.2017.
 */
@Getter
@Setter
public class StepResult {

    public static final String RESULT_OK = "OK";
    public static final String RESULT_FAIL = "Fail";

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
    private long start;
    private long stop;
    private String cookies;
    private List<RequestData> requestDataList;
    private Map<String, Object> scenarioVariables;

    public StepResult(Step step) {
        this.step = step;
    }
}
