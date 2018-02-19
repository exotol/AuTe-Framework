package ru.bsc.test.autotester.ro;

import java.io.Serializable;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
public class StepParameterRo implements Serializable{
    private static final long serialVersionUID = -2094782435995324148L;

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
}
