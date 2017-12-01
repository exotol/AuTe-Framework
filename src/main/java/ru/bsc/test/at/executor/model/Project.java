package ru.bsc.test.at.executor.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 10.05.2017.
 */
@SuppressWarnings("WeakerAccess")
public class Project extends AbstractModel implements Serializable {

    private String name;
    private List<Scenario> scenarioList;
    private List<Stand> standList;
    private Scenario beforeScenario;
    private String beforeScenarioPath;
    private Scenario afterScenario;
    private String afterScenarioPath;
    private Stand stand;
    private Boolean useRandomTestId;
    private String testIdHeaderName;
    private AmqpBroker amqpBroker;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scenario getBeforeScenario() {
        return beforeScenario;
    }

    public void setBeforeScenario(Scenario beforeScenario) {
        this.beforeScenario = beforeScenario;
    }

    public String getBeforeScenarioPath() {
        return beforeScenarioPath;
    }

    public void setBeforeScenarioPath(String beforeScenarioPath) {
        this.beforeScenarioPath = beforeScenarioPath;
    }

    public Scenario getAfterScenario() {
        return afterScenario;
    }

    public void setAfterScenario(Scenario afterScenario) {
        this.afterScenario = afterScenario;
    }

    public String getAfterScenarioPath() {
        return afterScenarioPath;
    }

    public void setAfterScenarioPath(String afterScenarioPath) {
        this.afterScenarioPath = afterScenarioPath;
    }

    public List<Scenario> getScenarioList() {
        if (scenarioList == null) {
            scenarioList = new LinkedList<>();
        }
        return scenarioList;
    }

    public void setScenarioList(List<Scenario> scenarioList) {
        this.scenarioList = scenarioList;
    }

    public List<Stand> getStandList() {
        if (standList == null) {
            standList = new LinkedList<>();
        }
        return standList;
    }

    public void setStandList(List<Stand> standList) {
        this.standList = standList;
    }

    public Stand getStand() {
        return stand;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public Boolean getUseRandomTestId() {
        return useRandomTestId == null ? false : useRandomTestId;
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

    public AmqpBroker getAmqpBroker() {
        return amqpBroker;
    }

    public void setAmqpBroker(AmqpBroker amqpBroker) {
        this.amqpBroker = amqpBroker;
    }

    public Project copy() {
        Project project = new Project();
        project.setName(getName());
        project.setCode(getCode() + "_COPY");
        project.setUseRandomTestId(getUseRandomTestId());
        project.setTestIdHeaderName(getTestIdHeaderName());
        project.setStandList(new LinkedList<>());
        for (Stand stand : getStandList()) {
            project.getStandList().add(stand.copy());
        }
        project.setStand(getStand().copy());
        project.setScenarioList(new LinkedList<>());
        for (Scenario scenario : getScenarioList()) {
            Scenario projectScenario = scenario.copy();
            projectScenario.setScenarioGroup(scenario.getScenarioGroup());
            project.getScenarioList().add(projectScenario);

            // Правильная привязка сценариев
            if (scenario.equals(getBeforeScenario())) {
                project.setBeforeScenario(projectScenario);
            }
            if (scenario.equals(getAfterScenario())) {
                project.setAfterScenario(projectScenario);
            }
        }

        project.setAmqpBroker(getAmqpBroker().copy());
        return project;
    }
}
