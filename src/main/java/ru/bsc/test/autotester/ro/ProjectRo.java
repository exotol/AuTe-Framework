package ru.bsc.test.autotester.ro;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
public class ProjectRo extends AbstractRo {

    private static final long serialVersionUID = 3953325934454830833L;

    private Long id;
    private String name;
    private Long beforeScenarioId;
    private Long afterScenarioId;
    private String projectCode;
    private List<ScenarioGroupRo> scenarioGroups;
    private List<StandRo> standList;
    private StandRo stand;
    private Boolean useRandomTestId;
    private String testIdHeaderName;
    private AmqpBrokerRo amqpBroker;

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

    public AmqpBrokerRo getAmqpBroker() {
        return amqpBroker;
    }

    public void setAmqpBroker(AmqpBrokerRo amqpBroker) {
        this.amqpBroker = amqpBroker;
    }
}
