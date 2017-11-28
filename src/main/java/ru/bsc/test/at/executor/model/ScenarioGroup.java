package ru.bsc.test.at.executor.model;

import java.io.Serializable;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
public class ScenarioGroup extends AbstractModel implements Serializable {

    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ScenarioGroup copy() {
        ScenarioGroup scenarioGroup = new ScenarioGroup();
        scenarioGroup.setName(getName());
        return scenarioGroup;
    }
}
