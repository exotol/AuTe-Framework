package ru.bsc.test.at.executor.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 10.05.2017.
 */
@SuppressWarnings("WeakerAccess")
public class Project extends AbstractModel implements Serializable {

    private String code;
    private String name;
    private List<Scenario> scenarioList;
    private String beforeScenarioPath;
    private String afterScenarioPath;
    private Stand stand;
    private Boolean useRandomTestId;
    private String testIdHeaderName;
    private AmqpBroker amqpBroker;
    private List<String> groupList;

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

    public String getBeforeScenarioPath() {
        return beforeScenarioPath;
    }

    public void setBeforeScenarioPath(String beforeScenarioPath) {
        this.beforeScenarioPath = beforeScenarioPath;
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

    public List<String> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<String> groupList) {
        this.groupList = groupList;
    }

    public Project copy() {
        Project project = new Project();
        project.setName(getName());
        project.setCode(getCode() + "_COPY");
        project.setUseRandomTestId(getUseRandomTestId());
        project.setTestIdHeaderName(getTestIdHeaderName());
        project.setStand(getStand().copy());
        if (getScenarioList() != null) {
            project.setScenarioList(new LinkedList<>());
            for (Scenario scenario : getScenarioList()) {
                Scenario projectScenario = scenario.copy();
                projectScenario.setScenarioGroup(scenario.getScenarioGroup());
                project.getScenarioList().add(projectScenario);
            }
        }

        if (getGroupList() != null) {
            project.setGroupList(new LinkedList<>());
            for (String group : getGroupList()) {
                project.getGroupList().add(group);
            }
        }

        project.setAmqpBroker(getAmqpBroker().copy());
        return project;
    }

    @Override
    public String toString() {
        return "Project{" + code + '}';
    }
}
