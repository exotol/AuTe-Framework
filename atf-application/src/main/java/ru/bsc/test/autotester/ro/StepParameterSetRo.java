package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 */
@Getter
@Setter
public class StepParameterSetRo implements AbstractRo {
    private static final long serialVersionUID = -8505680675142636322L;

    private List<StepParameterRo> stepParameterList;
    private String description;
}
