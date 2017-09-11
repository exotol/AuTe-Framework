package ru.bsc.test.at.executor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by sdoroshin on 08.09.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
@Entity
@Table(name = "AT_STEP_PARAMETER")
public class StepParameter {

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_STEP_PARAMETER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STEP_PARAMETER_SET_ID")
    @JsonBackReference
    private StepParameterSet stepParameterSet;

    @Column(name = "NAME")
    private String name;

    @Column(name = "VALUE")
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StepParameterSet getStepParameterSet() {
        return stepParameterSet;
    }

    public void setStepParameterSet(StepParameterSet stepParameterSet) {
        this.stepParameterSet = stepParameterSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StepParameter copy() {
        StepParameter copy = new StepParameter();
        copy.setName(getName());
        copy.setValue(getValue());
        return copy;
    }
}
