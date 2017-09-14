package ru.bsc.test.autotester.ro;

import ru.bsc.test.autotester.dto.AbstractRo;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
public class ProjectRo extends AbstractRo {

    private Long id;
    private String name;
    private ScenarioRo beforeScenario;
    private ScenarioRo afterScenario;
    private String projectCode;
    private List<ScenarioGroupRo> scenarioGroups;
    private List<StandRo> standList;
    private StandRo stand;
    private Boolean useRandomTestId;
    private String testIdHeaderName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public List<ScenarioGroupRo> getScenarioGroups() {
        return scenarioGroups;
    }

    public void setScenarioGroups(List<ScenarioGroupRo> scenarioGroups) {
        this.scenarioGroups = scenarioGroups;
    }

    public List<StandRo> getStandList() {
        return standList;
    }

    public void setStandList(List<StandRo> standList) {
        this.standList = standList;
    }

    public StandRo getStand() {
        return stand;
    }

    public void setStand(StandRo stand) {
        this.stand = stand;
    }

    public Boolean getUseRandomTestId() {
        return useRandomTestId;
    }

    public void setUseRandomTestId(Boolean useRandomTestId) {
        this.useRandomTestId = useRandomTestId;
    }

    public String getTestIdHeaderName() {
        return testIdHeaderName;
    }

    public void setTestIdHeaderName(String testIdHeaderName) {
        this.testIdHeaderName = testIdHeaderName;
    }
}
