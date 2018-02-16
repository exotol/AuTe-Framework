package ru.bsc.test.at.executor.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 10.05.2017.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Scenario implements Serializable, AbstractModel {
    private static final long serialVersionUID = -3442194270361216323L;

    private String code;
    private String name;
    private String scenarioGroup;
    private List<Step> stepList;
    private Boolean beforeScenarioIgnore;
    private Boolean afterScenarioIgnore;
    private Boolean failed;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScenarioGroup() {
        return scenarioGroup;
    }

    public void setScenarioGroup(String scenarioGroup) {
        this.scenarioGroup = scenarioGroup;
    }

    public List<Step> getStepList() {
        if (stepList == null) {
            stepList = new LinkedList<>();
        }
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    public Boolean getBeforeScenarioIgnore() {
        return beforeScenarioIgnore != null && beforeScenarioIgnore;
    }

    public void setBeforeScenarioIgnore(Boolean beforeScenarioIgnore) {
        this.beforeScenarioIgnore = beforeScenarioIgnore;
    }

    public Boolean getAfterScenarioIgnore() {
        return afterScenarioIgnore != null && afterScenarioIgnore;
    }

    public void setAfterScenarioIgnore(Boolean afterScenarioIgnore) {
        this.afterScenarioIgnore = afterScenarioIgnore;
    }

    public Boolean getFailed() {
        return failed;
    }

    public void setFailed(Boolean failed) {
        this.failed = failed;
    }

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

    @Override
    public String toString() {
        return "Scenario{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", scenarioGroup='" + scenarioGroup + '\'' +
                '}';
    }
}
