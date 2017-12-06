package ru.bsc.test.at.executor.model;

/**
 * Created by sdoroshin on 08.09.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
public class StepParameter extends AbstractModel {

    private String name;
    private String value;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public StepParameter copy() {
        StepParameter copy = new StepParameter();
        copy.setName(getName());
        copy.setValue(getValue());
        return copy;
    }
}
