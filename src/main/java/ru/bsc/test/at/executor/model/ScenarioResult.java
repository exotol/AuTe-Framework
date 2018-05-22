package ru.bsc.test.at.executor.model;

import java.util.List;

/**
 * @author Pavel Golovkin
 */
public class ScenarioResult {
  private Scenario scenario;
  private List<StepResult> stepResultList;
  private Boolean failed;

  public ScenarioResult(Scenario scenario, List<StepResult> stepResultList) {
    this.scenario = scenario;
    this.stepResultList = stepResultList;
  }

  public List<StepResult> getStepResultList() {
    return stepResultList;
  }

  public Scenario getScenario() {
    return scenario;
  }

  public boolean isFailed() {
    if (failed == null) {
      failed = false;
      for (StepResult stepResult: stepResultList) {
        if (!stepResult.getResult().isPositive()) {
          failed = true;
        }
      }
    }
    return failed;
  }

}
