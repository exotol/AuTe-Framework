package ru.bsc.test.autotester.ro;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
@Getter
@Setter
@Builder
public class ScenarioResultRo implements AbstractRo {
    private static final long serialVersionUID = 3389416118467296472L;

    private ScenarioRo scenario;
    private List<StepResultRo> stepResultList;
    private Integer totalSteps;
}
