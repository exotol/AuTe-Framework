package ru.bsc.test.autotester.dto;

import ru.bsc.test.at.executor.model.StepParameter;
import ru.bsc.test.at.executor.model.StepParameterSet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sdoroshin on 11.09.2017.
 *
 */
public class ParameterSetJspDto {

    private Map<String, StepParameter> parameterMap;
    private StepParameterSet stepParameterSet;

    public Map<String, StepParameter> getParameterMap() {
        if (parameterMap == null) {
            parameterMap = new HashMap<>();
        }
        return parameterMap;
    }

    public void setParameterMap(Map<String, StepParameter> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public StepParameterSet getStepParameterSet() {
        return stepParameterSet;
    }

    public void setStepParameterSet(StepParameterSet stepParameterSet) {
        this.stepParameterSet = stepParameterSet;
    }
}
