package ru.bsc.test.at.executor.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Scenario extends AbstractModel implements Serializable {

    private String name;
    private Project project;
    private ScenarioGroup scenarioGroup;
    private Scenario beforeScenario;
    private Scenario afterScenario;
    private List<Step> stepList;
    private String stepListYamlFile;
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
    public List<Step> getStepList() {
        if (stepList == null) {
            stepList = new LinkedList<>();
        }
        return stepList;
    }
    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }
    public String getStepListYamlFile() {
        return stepListYamlFile;
    }
    public void setStepListYamlFile(String stepListYamlFile) {
        this.stepListYamlFile = stepListYamlFile;
    }
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }
    public Stand getStand() {
        return stand;
    }
    public void setStand(Stand stand) {
        this.stand = stand;
    }
    public Boolean getBeforeScenarioIgnore() {
        return beforeScenarioIgnore == null ? false : beforeScenarioIgnore;
    }
    public void setBeforeScenarioIgnore(Boolean beforeScenarioIgnore) {
        this.beforeScenarioIgnore = beforeScenarioIgnore;
    }
    public Boolean getAfterScenarioIgnore() {
        return afterScenarioIgnore == null ? false : afterScenarioIgnore;
    }
    public void setAfterScenarioIgnore(Boolean afterScenarioIgnore) {
        this.afterScenarioIgnore = afterScenarioIgnore;
    }

    public Scenario copy() {
        Scenario scenario = new Scenario();
        scenario.setId(null);
        scenario.setProject(getProject());
        scenario.setName(getName());
        scenario.setScenarioGroup(getScenarioGroup());
        scenario.setBeforeScenario(getBeforeScenario());
        scenario.setAfterScenario(getAfterScenario());
        scenario.setStand(getStand());

        scenario.setStepListYamlFile(getStepListYamlFile());
        scenario.setStepList(new LinkedList<>());
        for (Step step: getStepList()) {
            Step clonedStep = step.copy();
            scenario.getStepList().add(clonedStep);
        }

        return scenario;
    }
}
