package ru.bsc.test.autotester.launcher.impl;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.ScenarioResult;
import ru.bsc.test.at.executor.service.AtExecutor;
import ru.bsc.test.at.executor.service.IExecutingFinishObserver;
import ru.bsc.test.autotester.launcher.api.ScenarioLauncher;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.report.AbstractReportGenerator;

/**
 * @author Pavel Golovkin
 */
@Component
public class ScenarioLauncherImpl implements ScenarioLauncher {

  public void launchScenario(Scenario scenarioToExecute,
                                      Project project,
                                      EnvironmentProperties properties,
                                      AbstractReportGenerator reportGenerator,
                                      List<ScenarioResult> scenarioResults,
                                      IExecutingFinishObserver finishObserver) {
    AtExecutor atExecutor = new AtExecutor();
    atExecutor.setProjectPath(Paths.get(properties.getProjectsDirectoryPath(), project.getCode()).toString());

    atExecutor.executeScenarioList(
            project,
            Collections.singletonList(scenarioToExecute),
            scenarioResults,
            () -> false,
            finishObserver
    );
    addResultsToReport(reportGenerator, scenarioResults);
  }

  private void addResultsToReport(AbstractReportGenerator reportGenerator, List<ScenarioResult> scenarioResultList) {
    for (ScenarioResult scenarioResult : scenarioResultList) {
      reportGenerator.add(scenarioResult.getScenario(), scenarioResult.getStepResultList());
    }
  }
}
