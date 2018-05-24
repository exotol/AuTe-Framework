package ru.bsc.test.autotester.launcher.impl;

import java.util.List;

import lombok.ToString;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.model.ScenarioResult;

/**
 * @author Pavel Golovkin
 */
@ToString
public class LaunchResult {

  private int failedTestsCount = 0;
  private int passedTestsCount = 0;

  public boolean isFailed() {
    return failedTestsCount > 0;
  }

  LaunchResult(List<ScenarioResult> scenarioResults) {
    for (ScenarioResult scenarioResult: scenarioResults) {
      if (scenarioResult.isFailed()) {
        failedTestsCount++;
      } else {
        passedTestsCount++;
      }
    }
  }
}
