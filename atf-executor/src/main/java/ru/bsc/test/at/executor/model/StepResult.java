package ru.bsc.test.at.executor.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Created by sdoroshin on 12.05.2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class StepResult {

    private String testId;
    private String projectCode;
    private Step step;
    private StepResultType result;
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
    private List<String> sqlQueryList;

    public StepResult(String projectCode, Step step) {
        this.projectCode = projectCode;
        this.step = step;
    }

    public enum StepResultType {
        OK("OK", true),
        FAIL("Fail", false);

        String text;
        boolean isPositive;

        StepResultType(String text, boolean isPositive) {
            this.text = text;
            this.isPositive = isPositive;
        }

        public String getText() {
            return text;
        }

        public boolean isPositive() {
            return isPositive;
        }
    }
}


