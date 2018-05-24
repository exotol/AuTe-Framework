package ru.bsc.test.at.executor.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 08.09.2017.
 */
@Getter
@Setter
public class StepParameterSet implements AbstractModel {
    private List<StepParameter> stepParameterList = new LinkedList<>();
    private String description;

    public StepParameterSet copy() {
        StepParameterSet cloned = new StepParameterSet();
        cloned.setDescription(getDescription());

        cloned.setStepParameterList(new LinkedList<>());
        for (StepParameter stepParameter : getStepParameterList()) {
            StepParameter copyStepParameter = stepParameter.copy();
            cloned.getStepParameterList().add(copyStepParameter);
        }

        return cloned;
    }
}
