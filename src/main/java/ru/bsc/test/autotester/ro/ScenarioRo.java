package ru.bsc.test.autotester.ro;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
public class ScenarioRo extends AbstractRo {

    private static final long serialVersionUID = -6026744701723398082L;

    private Long id;
    private String code;
    private String projectCode;
    private String projectName;
    private String name;

    private String scenarioGroup;
    private Long beforeScenarioId;
    private Long afterScenarioId;
    private List<StepRo> stepList;
    private Boolean beforeScenarioIgnore;
    private Boolean afterScenarioIgnore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
}
