package ru.bsc.test.at.executor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
@Entity
@Table(name = "AT_PROJECT")
public class Project implements Serializable, Cloneable {

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_PROJECT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", length = 100)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BEFORE_SCENARIO_ID")
    private Scenario beforeScenario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AFTER_SCENARIO_ID")
    private Scenario afterScenario;

    @Column(name = "PROJECT_CODE", length = 200)
    private String projectCode;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @OrderBy("SCENARIO_GROUP_ID ASC, NAME ASC")
    @JsonManagedReference
    private List<Scenario> scenarios;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("NAME ASC")
    @JsonManagedReference
    private List<ScenarioGroup> scenarioGroups;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Stand> standList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STAND_ID")
    @JsonBackReference
    private Stand stand;

    @Column(name = "USE_RANDOM_TEST_ID")
    private Boolean useRandomTestId;
    @Column(name = "TEST_ID_HEADER_NAME")
    private String testIdHeaderName;

    @SuppressWarnings("unused")
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
    public List<Scenario> getScenarios() {
        if (scenarios == null) {
            scenarios = new LinkedList<>();
        }
        return scenarios;
    }
    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }
    public List<ScenarioGroup> getScenarioGroups() {
        return scenarioGroups;
    }
    public void setScenarioGroups(List<ScenarioGroup> scenarioGroups) {
        this.scenarioGroups = scenarioGroups;
    }
    public List<Stand> getStandList() {
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

    @Override
    public Project clone() {
        Project cloned = new Project();
        cloned.setId(null);
        cloned.setName(getName());
        cloned.setProjectCode(getProjectCode() + "_COPY");
        cloned.setUseRandomTestId(getUseRandomTestId());
        cloned.setTestIdHeaderName(getTestIdHeaderName());
        Map<Stand, Stand> standToClonedMap = new HashMap<>();
        cloned.setStandList(new LinkedList<>());
        for (Stand stand: getStandList()) {
            Stand clonedStand = stand.copy();
            clonedStand.setProject(cloned);
            standToClonedMap.put(stand, clonedStand);

            cloned.getStandList().add(clonedStand);
        }

        cloned.setStand(standToClonedMap.get(getStand()));

        Map<Scenario, Scenario> scenarioToClonedScenarioMap = new HashMap<>();
        cloned.setScenarios(new LinkedList<>());
        for (Scenario scenario: getScenarios()) {
            Scenario clonedScenario = scenario.copy();
            clonedScenario.setProject(cloned);
            clonedScenario.setStand(standToClonedMap.get(scenario.getStand()));
            scenarioToClonedScenarioMap.put(scenario, clonedScenario);


            // Для начала назначаются уже существующие в проекте группы.
            // Во время клонирования групп далее будет переприсваиваться новые созданные группы
            clonedScenario.setScenarioGroup(scenario.getScenarioGroup());

            // Добавить склонированный сценарий в склонированный проект
            cloned.getScenarios().add(clonedScenario);

            // Правильная привязка сценариев
            if (scenario.equals(getBeforeScenario())) {
                cloned.setBeforeScenario(clonedScenario);
            }
            if (scenario.equals(getAfterScenario())) {
                cloned.setAfterScenario(clonedScenario);
            }
        }

        // TODO Проверить правильность "перекидывания" ссылок
        for (Scenario scenario: cloned.getScenarios()) {
            scenario.setBeforeScenario(scenarioToClonedScenarioMap.get(scenario.getBeforeScenario()));
            scenario.setAfterScenario(scenarioToClonedScenarioMap.get(scenario.getAfterScenario()));
        }

        cloned.setScenarioGroups(new LinkedList<>());
        for (ScenarioGroup scenarioGroup: getScenarioGroups()) {
            ScenarioGroup clonedScenarioGroup = scenarioGroup.copy();
            clonedScenarioGroup.setProject(cloned);
            cloned.getScenarioGroups().add(clonedScenarioGroup);

            // Всем сценариям, привязанным к этой группе, переназначить группы, созданные в склонированном проекте
            cloned.getScenarios().stream()
                    .filter(scenario -> scenarioGroup.equals(scenario.getScenarioGroup()))
                    .forEach(scenario -> scenario.setScenarioGroup(clonedScenarioGroup));
        }

        return cloned;
    }
}
