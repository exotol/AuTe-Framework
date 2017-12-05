package ru.bsc.test.autotester.ro;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
public class StepParameterSetRo extends AbstractRo {

    private static final long serialVersionUID = -8505680675142636322L;

    private Long sort;
    private List<StepParameterRo> stepParameterList;
    private String description;

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public List<StepParameterRo> getStepParameterList() {
        return stepParameterList;
    }

    public void setStepParameterList(List<StepParameterRo> stepParameterList) {
        this.stepParameterList = stepParameterList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
