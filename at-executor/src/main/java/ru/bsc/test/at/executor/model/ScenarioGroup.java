package ru.bsc.test.at.executor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@Entity
@Table(name = "AT_SCENARIO_GROUP")
public class ScenarioGroup implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PROJECT_ID")
    private Long projectId;

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
    public Long getProjectId() {
        return projectId;
    }
    @SuppressWarnings("unused")
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
