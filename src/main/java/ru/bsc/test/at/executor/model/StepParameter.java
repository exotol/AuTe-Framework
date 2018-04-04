package ru.bsc.test.at.executor.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sdoroshin on 08.09.2017.
 *
 */
@Getter
@Setter
public class StepParameter implements AbstractModel {
    private String name;
    private String value;

    public StepParameter copy() {
        StepParameter copy = new StepParameter();
        copy.setName(getName());
        copy.setValue(getValue());
        return copy;
    }
}
