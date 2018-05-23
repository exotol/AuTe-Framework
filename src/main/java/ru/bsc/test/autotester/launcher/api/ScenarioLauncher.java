package ru.bsc.test.autotester.launcher.api;

import java.util.List;
import java.util.Set;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.ScenarioResult;
import ru.bsc.test.at.executor.service.IExecutingFinishObserver;
import ru.bsc.test.autotester.model.ExecutionResult;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.report.AbstractReportGenerator;
import ru.bsc.test.autotester.service.ProjectService;

/**
 * Launch scenario interface.
 *
 * @author Pavel Golovkin
 */
public interface ScenarioLauncher {
  /**
   * Launch scenario from UI
   * @param scenarioList
   * @param project
   * @param properties
   * @param executionResult
   * @param stopExecutingSet
   * @param projectService
   * @param runningUuid
   */
  void launchScenarioFromUI(List<Scenario> scenarioList,
                            Project project,
                            EnvironmentProperties properties,
                            ExecutionResult executionResult,
                            Set<String> stopExecutingSet,
                            ProjectService projectService,
                            String runningUuid);

  /**
   * Launch scenario from command line
   * @param scenarioToExecute
   * @param project
   * @param properties
   * @param reportGenerator
   * @param scenarioResults
   */
  void launchScenarioFromCLI(List<Scenario> scenarioToExecute,
                             Project project,
                             EnvironmentProperties properties,
                             AbstractReportGenerator reportGenerator,
                             List<ScenarioResult> scenarioResults);
}
