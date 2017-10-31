package ru.bsc.test.at.executor.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
public class Scenario extends AbstractModel implements Serializable, Cloneable {

    private String name;
    private Project project;
    private ScenarioGroup scenarioGroup;
    private List<StepResult> stepResults = null;
    private Date lastRunAt;
    private Integer lastRunFailures;
    private Scenario beforeScenario;
    private Scenario afterScenario;
    private List<Step> steps;
    private Boolean beforeScenarioIgnore;
    private Boolean afterScenarioIgnore;
    private Stand stand;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ScenarioGroup getScenarioGroup() {
        return scenarioGroup;
    }
    public void setScenarioGroup(ScenarioGroup scenarioGroup) {
        this.scenarioGroup = scenarioGroup;
    }
    public List<StepResult> getStepResults() {
        return stepResults;
    }
    public void setLastRunAt(Date lastRunAt) {
        this.lastRunAt = lastRunAt;
    }
    @SuppressWarnings("unused")
    public Date getLastRunAt() {
        return lastRunAt;
    }
    public void setLastRunFailures(Integer lastRunFailures) {
        this.lastRunFailures = lastRunFailures;
    }
    @SuppressWarnings("unused")
    public Integer getLastRunFailures() {
        return lastRunFailures;
    }
    public Scenario getBeforeScenario() {
        return beforeScenario;
    }
    public void setBeforeScenario(Scenario beforeScenario) {
        this.beforeScenario = beforeScenario;
    }
    public Scenario getAfterScenario() {
        return afterScenario;
    }
    public void setAfterScenario(Scenario afterScenario) {
        this.afterScenario = afterScenario;
    }
    public List<Step> getSteps() {
        if (steps == null) {
            steps = new LinkedList<>();
        }
        return steps;
    }
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }
    public void setStepResults(List<StepResult> stepResults) {
        this.stepResults = stepResults;
    }
    public Stand getStand() {
        return stand;
    }
    public void setStand(Stand stand) {
        this.stand = stand;
    }

    @Override
    public Scenario clone() throws CloneNotSupportedException {
        super.clone();
        Scenario cloned = new Scenario();
        cloned.setId(null);
        cloned.setProject(getProject());
        cloned.setName(getName());
        cloned.setScenarioGroup(getScenarioGroup());
        cloned.setLastRunAt(null);
        cloned.setLastRunFailures(null);
        cloned.setBeforeScenario(getBeforeScenario());
        cloned.setAfterScenario(getAfterScenario());
        cloned.setStand(getStand());

        cloned.setSteps(new LinkedList<>());
        for (Step step: getSteps()) {
            Step clonedStep = step.clone();
            clonedStep.setScenario(cloned);
            cloned.getSteps().add(clonedStep);
        }

        return cloned;
    }

    public Boolean getBeforeScenarioIgnore() {
        return beforeScenarioIgnore == null ? false : beforeScenarioIgnore;
    }
    @SuppressWarnings("unused")
    public void setBeforeScenarioIgnore(Boolean beforeScenarioIgnore) {
        this.beforeScenarioIgnore = beforeScenarioIgnore;
    }

    public Boolean getAfterScenarioIgnore() {
        return afterScenarioIgnore == null ? false : afterScenarioIgnore;
    }
    @SuppressWarnings("unused")
    public void setAfterScenarioIgnore(Boolean afterScenarioIgnore) {
        this.afterScenarioIgnore = afterScenarioIgnore;
    }
}
