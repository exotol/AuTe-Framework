package ru.bsc.test.at.executor.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
public class Project extends AbstractModel implements Serializable, Cloneable {

    private String name;
    private String projectCode;
    private List<Scenario> scenarioList;
    private List<ScenarioGroup> scenarioGroups;
    private List<Stand> standList;
    private Scenario beforeScenario;
    private Scenario afterScenario;
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
    public Scenario getAfterScenario() {
        return afterScenario;
    }
    public void setAfterScenario(Scenario afterScenario) {
        this.afterScenario = afterScenario;
    }
    public String getProjectCode() {
        return projectCode;
    }
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
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
    public List<ScenarioGroup> getScenarioGroups() {
        if (scenarioGroups == null) {
            scenarioGroups = new LinkedList<>();
        }
        return scenarioGroups;
    }
    public void setScenarioGroups(List<ScenarioGroup> scenarioGroups) {
        this.scenarioGroups = scenarioGroups;
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

    @Override
    public Project clone() throws CloneNotSupportedException {
        super.clone();
        Project cloned = new Project();
        cloned.setId(null);
        cloned.setName(getName());
        cloned.setProjectCode(getProjectCode() + "_COPY");
        cloned.setUseRandomTestId(getUseRandomTestId());
        cloned.setTestIdHeaderName(getTestIdHeaderName());

        Map<Stand, Stand> standToClonedMap = new HashMap<>();

        cloned.setStandList(new LinkedList<>());
        for (Stand stand: getStandList()) {
            Stand clonedStand = stand.clone();
            clonedStand.setProject(cloned);
            standToClonedMap.put(stand, clonedStand);

            cloned.getStandList().add(clonedStand);
        }

        cloned.setStand(standToClonedMap.get(getStand()));

        Map<Scenario, Scenario> scenarioToClonedScenarioMap = new HashMap<>();
        cloned.setScenarioList(new LinkedList<>());
        for (Scenario scenario: getScenarioList()) {
            Scenario clonedScenario = scenario.clone();
            clonedScenario.setProject(cloned);
            clonedScenario.setStand(standToClonedMap.get(scenario.getStand()));
            scenarioToClonedScenarioMap.put(scenario, clonedScenario);


            // Для начала назначаются уже существующие в проекте группы.
            // Во время клонирования групп далее будет переприсваиваться новые созданные группы
            clonedScenario.setScenarioGroup(scenario.getScenarioGroup());

            // Добавить склонированный сценарий в склонированный проект
            cloned.getScenarioList().add(clonedScenario);

            // Правильная привязка сценариев
            if (scenario.equals(getBeforeScenario())) {
                cloned.setBeforeScenario(clonedScenario);
            }
            if (scenario.equals(getAfterScenario())) {
                cloned.setAfterScenario(clonedScenario);
            }
        }

        for (Scenario scenario: cloned.getScenarioList()) {
            scenario.setBeforeScenario(scenarioToClonedScenarioMap.get(scenario.getBeforeScenario()));
            scenario.setAfterScenario(scenarioToClonedScenarioMap.get(scenario.getAfterScenario()));
        }

        cloned.setScenarioGroups(new LinkedList<>());
        for (ScenarioGroup scenarioGroup: getScenarioGroups()) {
            ScenarioGroup clonedScenarioGroup = scenarioGroup.clone();
            clonedScenarioGroup.setProject(cloned);
            cloned.getScenarioGroups().add(clonedScenarioGroup);

            // Всем сценариям, привязанным к этой группе, переназначить группы, созданные в склонированном проекте
            cloned.getScenarioList().stream()
                    .filter(scenario -> scenarioGroup.equals(scenario.getScenarioGroup()))
                    .forEach(scenario -> scenario.setScenarioGroup(clonedScenarioGroup));
        }

        cloned.setAmqpBroker(getAmqpBroker().copy());

        return cloned;
    }
}
