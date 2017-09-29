package ru.bsc.test.autotester.ro;

import ru.bsc.test.autotester.dto.AbstractRo;

import java.util.Date;
import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
public class ScenarioRo extends AbstractRo {
    private Long id;
    private Long projectId;
    private String projectName;
    private StandRo projectStand;
    private String name;

    private ScenarioGroupRo scenarioGroup;
    private List<StepResultRo> stepResults = null;
    private Date lastRunAt;
    private Integer lastRunFailures;
    private ScenarioRo beforeScenario;
    private ScenarioRo afterScenario;
    private List<StepRo> steps;
    private Boolean beforeScenarioIgnore;
    private Boolean afterScenarioIgnore;
    private StandRo stand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public StandRo getProjectStand() {
        return projectStand;
    }

    public void setProjectStand(StandRo projectStand) {
        this.projectStand = projectStand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ScenarioGroupRo getScenarioGroup() {
        return scenarioGroup;
    }

    public void setScenarioGroup(ScenarioGroupRo scenarioGroup) {
        this.scenarioGroup = scenarioGroup;
    }

    public List<StepResultRo> getStepResults() {
        return stepResults;
    }

    public void setStepResults(List<StepResultRo> stepResults) {
        this.stepResults = stepResults;
    }

    public Date getLastRunAt() {
        return lastRunAt;
    }

    public void setLastRunAt(Date lastRunAt) {
        this.lastRunAt = lastRunAt;
    }

    public Integer getLastRunFailures() {
        return lastRunFailures;
    }

    public void setLastRunFailures(Integer lastRunFailures) {
        this.lastRunFailures = lastRunFailures;
    }

    public ScenarioRo getBeforeScenario() {
        return beforeScenario;
    }

    public void setBeforeScenario(ScenarioRo beforeScenario) {
        this.beforeScenario = beforeScenario;
    }

    public ScenarioRo getAfterScenario() {
        return afterScenario;
    }

    public void setAfterScenario(ScenarioRo afterScenario) {
        this.afterScenario = afterScenario;
    }

    public List<StepRo> getSteps() {
        return steps;
    }

    public void setSteps(List<StepRo> steps) {
        this.steps = steps;
    }

    public Boolean getBeforeScenarioIgnore() {
        return beforeScenarioIgnore;
    }

    public void setBeforeScenarioIgnore(Boolean beforeScenarioIgnore) {
        this.beforeScenarioIgnore = beforeScenarioIgnore;
    }

    public Boolean getAfterScenarioIgnore() {
        return afterScenarioIgnore;
    }

    public void setAfterScenarioIgnore(Boolean afterScenarioIgnore) {
        this.afterScenarioIgnore = afterScenarioIgnore;
    }

    public StandRo getStand() {
        return stand;
    }

    public void setStand(StandRo stand) {
        this.stand = stand;
    }
}
