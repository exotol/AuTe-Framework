package ru.bsc.test.at.executor.model;

import java.io.Serializable;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
public class ScenarioGroup extends AbstractModel implements Serializable, Cloneable {

    private String name;
    private Project project;

    public String getName() {
        return name;
    }
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
