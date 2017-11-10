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
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
@Entity
@Table(name = "AT_SCENARIO")
public class Scenario {

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_SCENARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "NAME", length = 500)
    private String name;
    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    @JsonBackReference
    private Project project;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SCENARIO_GROUP_ID")
    private ScenarioGroup scenarioGroup;
    @Transient
    private List<StepResult> stepResults = null;
    @Column(name = "LAST_RUN_AT")
    private Date lastRunAt;
    @Column(name = "LAST_RUN_FAILURES")
    private Integer lastRunFailures;

    @ManyToOne
    @JoinColumn(name = "BEFORE_SCENARIO_ID")
    private Scenario beforeScenario;

    @ManyToOne
    @JoinColumn(name = "AFTER_SCENARIO_ID")
    private Scenario afterScenario;
    @OneToMany(mappedBy = "scenario", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("SORT ASC")
    @JsonManagedReference
    private List<Step> steps;
    @Column(name = "BEFORE_SCENARIO_IGNORE")
    private Boolean beforeScenarioIgnore;
    @Column(name = "AFTER_SCENARIO_IGNORE")
    private Boolean afterScenarioIgnore;

    @ManyToOne
    @JoinColumn(name = "STAND_ID")
    /* null - default (from project settings) */
    private Stand stand;

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
    public ScenarioGroup getScenarioGroup() {
        return scenarioGroup;
    }
    public void setScenarioGroup(ScenarioGroup scenarioGroup) {
        this.scenarioGroup = scenarioGroup;
    }
    public List<StepResult> getStepResults() {
        return stepResults;
    }
    public void setLastRunAt(Date lastRunAt) {
        this.lastRunAt = lastRunAt;
    }
    @SuppressWarnings("unused")
    public Date getLastRunAt() {
        return lastRunAt;
    }
    public void setLastRunFailures(Integer lastRunFailures) {
        this.lastRunFailures = lastRunFailures;
    }
    @SuppressWarnings("unused")
    public Integer getLastRunFailures() {
        return lastRunFailures;
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
    public List<Step> getSteps() {
        if (steps == null) {
            steps = new LinkedList<>();
        }
        return steps;
    }
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }
    public void setStepResults(List<StepResult> stepResults) {
        this.stepResults = stepResults;
    }
    public Stand getStand() {
        return stand;
    }
    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public Scenario copy() {
        Scenario scenario = new Scenario();
        scenario.setId(null);
        scenario.setProject(getProject());
        scenario.setName(getName());
        scenario.setScenarioGroup(getScenarioGroup());
        scenario.setLastRunAt(null);
        scenario.setLastRunFailures(null);
        scenario.setBeforeScenario(getBeforeScenario());
        scenario.setAfterScenario(getAfterScenario());
        scenario.setStand(getStand());

        scenario.setSteps(new LinkedList<>());
        for (Step step: getSteps()) {
            Step scenarioStep = step.copy();
            scenarioStep.setScenario(scenario);
            scenario.getSteps().add(scenarioStep);
        }

        return scenario;
    }

    public Boolean getBeforeScenarioIgnore() {
        return beforeScenarioIgnore == null ? false : beforeScenarioIgnore;
    }
    @SuppressWarnings("unused")
    public void setBeforeScenarioIgnore(Boolean beforeScenarioIgnore) {
        this.beforeScenarioIgnore = beforeScenarioIgnore;
    }

    public Boolean getAfterScenarioIgnore() {
        return afterScenarioIgnore == null ? false : afterScenarioIgnore;
    }
    @SuppressWarnings("unused")
    public void setAfterScenarioIgnore(Boolean afterScenarioIgnore) {
        this.afterScenarioIgnore = afterScenarioIgnore;
    }
}
