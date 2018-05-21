package ru.bsc.test.at.executor.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Pavel Golovkin
 */
@Getter
public class ScenarioResult {
  private Scenario scenario;
  private List<StepResult> stepResultList;
  private boolean failed;

  public ScenarioResult(Scenario scenario, List<StepResult> stepResultList) {
    this.scenario = scenario;
    this.stepResultList = stepResultList;
    for (StepResult stepResult: stepResultList) {
      if (!stepResult.getResult().isPositive()) {
        failed = true;
      }
    }
  }
}
