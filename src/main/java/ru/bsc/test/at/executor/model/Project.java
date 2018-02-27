package ru.bsc.test.at.executor.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 10.05.2017.
 */
@Getter
@Setter
public class Project implements Serializable, AbstractModel {
    private static final long serialVersionUID = 7331632683933716938L;

    private String code;
    private String name;
    private List<Scenario> scenarioList = new LinkedList<>();
    private String beforeScenarioPath;
    private String afterScenarioPath;
    private Stand stand;
    private Boolean useRandomTestId;
    private String testIdHeaderName;
    private AmqpBroker amqpBroker;
    private List<String> groupList;

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
