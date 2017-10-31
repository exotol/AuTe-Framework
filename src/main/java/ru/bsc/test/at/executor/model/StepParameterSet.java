package ru.bsc.test.at.executor.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 08.09.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
public class StepParameterSet extends AbstractModel {

    private Step step;
    private Long sort;
    private List<StepParameter> stepParameterList;
    private String description;

    public Step getStep() {
        return step;
    }
    public void setStep(Step step) {
        this.step = step;
    }
    public Long getSort() {
        return sort;
    }
    public void setSort(Long sort) {
        this.sort = sort;
    }
    public List<StepParameter> getStepParameterList() {
        return stepParameterList;
    }
    public void setStepParameterList(List<StepParameter> stepParameterList) {
        this.stepParameterList = stepParameterList;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public StepParameterSet copy() {
        StepParameterSet cloned = new StepParameterSet();
        cloned.setSort(getSort());
        cloned.setDescription(getDescription());

        cloned.setStepParameterList(new LinkedList<>());
        for (StepParameter stepParameter: getStepParameterList()) {
            StepParameter copyStepParameter = stepParameter.copy();
            copyStepParameter.setStepParameterSet(cloned);
            cloned.getStepParameterList().add(copyStepParameter);
        }

        return cloned;
    }
}
