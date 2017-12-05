package ru.bsc.test.autotester.ro;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
public class ScenarioRo extends AbstractRo {

    private static final long serialVersionUID = -6026744701723398082L;

    private Long id;
    private Long projectId;
    private String projectName;
    private StandRo projectStand;
    private String name;

    private ScenarioGroupRo scenarioGroup;
    private Long beforeScenarioId;
    private Long afterScenarioId;
    private List<StepRo> stepList;
    private Boolean beforeScenarioIgnore;
    private Boolean afterScenarioIgnore;
    private StandRo stand;
    private Boolean failed;

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

    public Long getBeforeScenarioId() {
        return beforeScenarioId;
    }

    public void setBeforeScenarioId(Long beforeScenarioId) {
        this.beforeScenarioId = beforeScenarioId;
    }

    public Long getAfterScenarioId() {
        return afterScenarioId;
    }

    public void setAfterScenarioId(Long afterScenarioId) {
        this.afterScenarioId = afterScenarioId;
    }

    public List<StepRo> getStepList() {
        return stepList;
    }

    public void setStepList(List<StepRo> stepList) {
        this.stepList = stepList;
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

    public Boolean getFailed() {
        return failed;
    }

    public void setFailed(Boolean failed) {
        this.failed = failed;
    }
}
