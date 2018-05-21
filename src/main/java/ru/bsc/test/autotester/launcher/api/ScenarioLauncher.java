package ru.bsc.test.autotester.launcher.api;

import java.util.List;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.ScenarioResult;
import ru.bsc.test.at.executor.service.IExecutingFinishObserver;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.report.AbstractReportGenerator;

/**
 * @author Pavel Golovkin
 */
public interface ScenarioLauncher {
  void launchScenario(Scenario scenarioToExecute,
                                      Project project,
                                      EnvironmentProperties properties,
                                      AbstractReportGenerator reportGenerator,
                                      List<ScenarioResult> scenarioResults,
                                      IExecutingFinishObserver finishObserver);
}
