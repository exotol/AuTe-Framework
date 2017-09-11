package ru.bsc.test.autotester.dto;

import java.util.Map;

/**
 * Created by sdoroshin on 08.09.2017.
 *
 */
public class ParameterSetDto {
    private Long stepId;
    private Map<Long, String> parameterSetList;
    private Map<Long, Map<String, String>> parameterList;

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public Map<Long, String> getParameterSetList() {
        return parameterSetList;
    }

    public void setParameterSetList(Map<Long, String> parameterSetList) {
        this.parameterSetList = parameterSetList;
    }

    public Map<Long, Map<String, String>> getParameterList() {
        return parameterList;
    }

    public void setParameterList(Map<Long, Map<String, String>> parameterList) {
        this.parameterList = parameterList;
    }
}
