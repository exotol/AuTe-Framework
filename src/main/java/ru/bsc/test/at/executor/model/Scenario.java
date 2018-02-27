package ru.bsc.test.at.executor.model;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 10.05.2017.
 */
@Data
public class Scenario implements Serializable, AbstractModel {
    private static final long serialVersionUID = -3442194270361216323L;

    private String code;
    private String name;
    private String scenarioGroup;
    private List<Step> stepList = new LinkedList<>();
    private Boolean beforeScenarioIgnore;
    private Boolean afterScenarioIgnore;
    private Boolean failed;

    public Scenario copy() {
        Scenario scenario = new Scenario();
        scenario.setName(getName());
        scenario.setScenarioGroup(getScenarioGroup());

        scenario.setStepList(new LinkedList<>());
        for (Step step : getStepList()) {
            Step clonedStep = step.copy();
            scenario.getStepList().add(clonedStep);
        }

        return scenario;
    }
}
