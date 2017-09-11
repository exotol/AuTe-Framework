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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 08.09.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
@Entity
@Table(name = "AT_STEP_PARAMETER_SET")
public class StepParameterSet {

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_STEP_PARAMETER_SET", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STEP_ID")
    @JsonBackReference
    private Step step;

    @Column(name = "SORT")
    private Long sort;

    @OneToMany(mappedBy = "stepParameterSet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<StepParameter> stepParameterList;

    @Column(name = "DESCRIPTION")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public List<StepParameter> getStepParameterList() {
        return stepParameterList;
    }

    public void setStepParameterList(List<StepParameter> stepParameterList) {
        this.stepParameterList = stepParameterList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StepParameterSet copy() {
        StepParameterSet cloned = new StepParameterSet();
        cloned.setSort(getSort());
        cloned.setDescription(getDescription());

        cloned.setStepParameterList(new LinkedList<>());
        for (StepParameter stepParameter: getStepParameterList()) {
            StepParameter copyStepParameter = stepParameter.copy();
            copyStepParameter.setStepParameterSet(cloned);
            cloned.getStepParameterList().add(copyStepParameter);
        }

        return cloned;
    }
}
