package ru.bsc.test.autotester.dto;

/**
 * Created by sdoroshin on 11.09.2017.
 *
 */
public class DeleteParameterSetDto {

    private Long stepId;
    private Long stepParameterSetId;

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public Long getStepParameterSetId() {
        return stepParameterSetId;
    }

    public void setStepParameterSetId(Long stepParameterSetId) {
        this.stepParameterSetId = stepParameterSetId;
    }
}
