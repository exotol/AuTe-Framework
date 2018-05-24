package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
@Getter
@Setter
public class StepParameterRo implements Serializable{
    private static final long serialVersionUID = -2094782435995324148L;

    private String name;
    private String value;
}
