package ru.bsc.test.at.executor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@Entity
@Table(name = "AT_SCENARIO_GROUP")
public class ScenarioGroup implements Serializable, Cloneable {

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_SCENARIO_GROUP", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "NAME", length = 150)
    private String name;
    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

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
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public ScenarioGroup clone() throws CloneNotSupportedException {
        super.clone();
        ScenarioGroup cloned = new ScenarioGroup();
        cloned.setName(getName());

        return cloned;
    }
}
