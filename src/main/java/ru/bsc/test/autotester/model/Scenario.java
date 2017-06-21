package ru.bsc.test.autotester.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@Entity
@Table(name = "AT_SCENARIO")
public class Scenario implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_SCENARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @JoinColumn(name = "PROJECT_ID")
    private Long projectId;

    @Column(name = "SCENARIO_GROUP_ID")
    private Long scenarioGroupId;

    @Transient
    private List<StepResult> stepResults = null;

    @Column(name = "LAST_RUN_AT")
    private Date lastRunAt;

    @Column(name = "LAST_RUN_FAILURES")
    private Integer lastRunFailures;

    @Column(name = "BEFORE_SCENARIO_ID")
    private Long beforeScenarioId;

    @Column(name = "AFTER_SCENARIO_ID")
    private Long afterScenarioId;

    @OneToMany
    @JoinColumn(name="SCENARIO_ID", referencedColumnName="ID")
    @OrderBy("SORT ASC")
    private List<Step> steps;

    public Long getId() {
        return id;
    }
    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }
    public Long getScenarioGroupId() {
        return scenarioGroupId;
    }
    public void setScenarioGroupId(Long scenarioGroupId) {
        this.scenarioGroupId = scenarioGroupId;
    }
    public List<StepResult> getStepResults() {
        if (stepResults == null) {
            stepResults = new LinkedList<>();
        }
        return stepResults;
    }
    public void setLastRunAt(Date lastRunAt) {
        this.lastRunAt = lastRunAt;
    }
    public Date getLastRunAt() {
        return lastRunAt;
    }
    public void setLastRunFailures(Integer lastRunFailures) {
        this.lastRunFailures = lastRunFailures;
    }
    public Integer getLastRunFailures() {
        return lastRunFailures;
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
    public List<Step> getSteps() {
        return steps;
    }
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
    public Long getProjectId() {
        return projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    public void setStepResults(List<StepResult> stepResults) {
        this.stepResults = stepResults;
    }
}
