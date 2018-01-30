package ru.bsc.test.autotester.ro;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
public class ScenarioResultRo extends AbstractRo {

    private static final long serialVersionUID = 3389416118467296472L;

    private ScenarioRo scenario;
    private List<StepResultRo> stepResultList;
    private Integer totalSteps;

    public ScenarioRo getScenario() {
        return scenario;
    }

    public void setScenario(ScenarioRo scenario) {
        this.scenario = scenario;
    }

    public List<StepResultRo> getStepResultList() {
        return stepResultList;
    }

    public void setStepResultList(List<StepResultRo> stepResultList) {
        this.stepResultList = stepResultList;
    }

    public ScenarioResultRo withScenario(ScenarioRo scenario) {
        setScenario(scenario);
        return this;
    }

    public ScenarioResultRo withStepResultRo(List<StepResultRo> stepResultList) {
        setStepResultList(stepResultList);
        return this;
    }

    public Integer getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(Integer totalSteps) {
        this.totalSteps = totalSteps;
    }

    public ScenarioResultRo withTotalSteps(Integer totalSteps) {
        setTotalSteps(totalSteps);
        return this;
    }
}
