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
public class Scenario implements Serializable, Cloneable {

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
    @OneToMany(mappedBy = "scenario", cascade = CascadeType.ALL)
    @OrderBy("SORT ASC")
    @JsonManagedReference
    private List<Step> steps;
    @Column(name = "BEFORE_SCENARIO_IGNORE")
    private Boolean beforeScenarioIgnore;
    @Column(name = "AFTER_SCENARIO_IGNORE")
    private Boolean afterScenarioIgnore;

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
    public Date getLastRunAt() {
        return lastRunAt;
    }
    public void setLastRunFailures(Integer lastRunFailures) {
        this.lastRunFailures = lastRunFailures;
    }
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
    @SuppressWarnings("unused")
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }
    @SuppressWarnings("unused")
    public void setStepResults(List<StepResult> stepResults) {
        this.stepResults = stepResults;
    }

    @Override
    public Scenario clone() throws CloneNotSupportedException {
        super.clone();
        Scenario cloned = new Scenario();
        cloned.setId(null);
        cloned.setProject(getProject());
        cloned.setName(getName());
        cloned.setScenarioGroup(getScenarioGroup());
        cloned.setLastRunAt(null);
        cloned.setLastRunFailures(null);
        cloned.setBeforeScenario(getBeforeScenario());
        cloned.setAfterScenario(getAfterScenario());

        cloned.setSteps(new LinkedList<>());
        for (Step step: getSteps()) {
            Step clonedStep = step.clone();
            clonedStep.setScenario(cloned);
            cloned.getSteps().add(clonedStep);
        }

        return cloned;
    }

    public Boolean getBeforeScenarioIgnore() {
        return beforeScenarioIgnore;
    }
    @SuppressWarnings("unused")
    public void setBeforeScenarioIgnore(Boolean beforeScenarioIgnore) {
        this.beforeScenarioIgnore = beforeScenarioIgnore;
    }

    public Boolean getAfterScenarioIgnore() {
        return afterScenarioIgnore;
    }
    @SuppressWarnings("unused")
    public void setAfterScenarioIgnore(Boolean afterScenarioIgnore) {
        this.afterScenarioIgnore = afterScenarioIgnore;
    }
}
