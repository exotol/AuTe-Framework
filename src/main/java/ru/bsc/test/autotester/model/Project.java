package ru.bsc.test.autotester.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@Entity
@Table(name = "AT_PROJECT")
public class Project implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_PROJECT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SERVICE_URL")
    private String serviceUrl;

    // TODO привязать before scenario с помощью @OneToMany
    @Column(name = "BEFORE_SCENARIO_ID")
    private Long beforeScenarioId;

    @Column(name = "AFTER_SCENARIO_ID")
    private Long afterScenarioId;

    @Column(name = "PROJECT_CODE")
    private String projectCode;

    @Column(name = "DB_URL")
    private String dbUrl;
    @Column(name = "DB_USER")
    private String dbUser;
    @Column(name = "DB_PASSWORD")
    private String dbPassword;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    @OrderBy("SCENARIO_GROUP_ID ASC, NAME ASC")
    private List<Scenario> scenarios;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
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
}
