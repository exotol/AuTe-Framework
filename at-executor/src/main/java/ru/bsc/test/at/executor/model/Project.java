package ru.bsc.test.at.executor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @Column(name = "SERVICE_URL", length = 400)
    private String serviceUrl;

    @ManyToOne
    @JoinColumn(name = "BEFORE_SCENARIO_ID")
    private Scenario beforeScenario;

    @ManyToOne
    @JoinColumn(name = "AFTER_SCENARIO_ID")
    private Scenario afterScenario;

    @Column(name = "PROJECT_CODE", length = 20)
    private String projectCode;

    @Column(name = "DB_URL")
    private String dbUrl;
    @Column(name = "DB_USER")
    private String dbUser;
    @Column(name = "DB_PASSWORD")
    private String dbPassword;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    @OrderBy("SCENARIO_GROUP_ID ASC, NAME ASC")
    private List<Scenario> scenarios;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    //@JoinColumn(name="PROJECT_ID", referencedColumnName="ID")
    @OrderBy("NAME ASC")
    private List<ScenarioGroup> scenarioGroups;

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
    public String getServiceUrl() {
        return serviceUrl;
    }
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
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
    @SuppressWarnings("unused")
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    public String getDbUrl() {
        return dbUrl;
    }
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }
    public String getDbUser() {
        return dbUser;
    }
    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }
    public String getDbPassword() {
        return dbPassword;
    }
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
    public List<Scenario> getScenarios() {
        if (scenarios == null) {
            scenarios = new LinkedList<>();
        }
        return scenarios;
    }
    @SuppressWarnings("unused")
    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }
    public List<ScenarioGroup> getScenarioGroups() {
        return scenarioGroups;
    }
    @SuppressWarnings("unused")
    public void setScenarioGroups(List<ScenarioGroup> scenarioGroups) {
        this.scenarioGroups = scenarioGroups;
    }

    @Override
    public Project clone() throws CloneNotSupportedException {
        super.clone();
        Project cloned = new Project();
        cloned.setId(null);
        cloned.setName(getName());
        cloned.setServiceUrl(getServiceUrl());
        cloned.setProjectCode(getProjectCode() + "_COPY");
        cloned.setDbUrl(getDbUrl());
        cloned.setDbUser(getDbUser());
        cloned.setDbPassword(getDbPassword());

        Map<Scenario, Scenario> scenarioToClonedScenarioMap = new HashMap<>();

        cloned.setScenarios(new LinkedList<>());
        for (Scenario scenario: getScenarios()) {
            Scenario clonedScenario = scenario.clone();
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
            ScenarioGroup clonedScenarioGroup = scenarioGroup.clone();
            cloned.getScenarioGroups().add(clonedScenarioGroup);

            // Всем сценариям, привязанным к этой группе, переназначить группы, созданные в склонированном проекте
            for (Scenario scenario: getScenarios()) {
                if (scenarioGroup.equals(scenario.getScenarioGroup())) {
                    scenario.setScenarioGroup(clonedScenarioGroup);
                }
            }
        }

        return cloned;
    }
}
