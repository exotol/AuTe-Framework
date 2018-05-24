package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 */
@Getter
@Setter
public class ScenarioRo implements AbstractRo {
    private static final long serialVersionUID = -6026744701723398082L;

    private String code;
    private String projectCode;
    private String name;

    private String scenarioGroup;
    private List<StepRo> stepList;
    private Boolean beforeScenarioIgnore;
    private Boolean afterScenarioIgnore;
    private Boolean failed;
    private Boolean hasResults;
}
