package ru.bsc.test.autotester.launcher.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.ScenarioResult;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.service.AtExecutor;
import ru.bsc.test.at.executor.service.IExecutingFinishObserver;
import ru.bsc.test.at.executor.service.IStopObserver;
import ru.bsc.test.autotester.launcher.api.ScenarioLauncher;
import ru.bsc.test.autotester.model.ExecutionResult;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.report.AbstractReportGenerator;
import ru.bsc.test.autotester.repository.ScenarioRepository;
import ru.bsc.test.autotester.service.ProjectService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Pavel Golovkin
 */
@Component
@Slf4j
public class ScenarioLauncherImpl implements ScenarioLauncher {

  private static final String DEFAULT_GROUP = "__default";
  private static final ObjectMapper objectMapper = new ObjectMapper().setVisibility(
      PropertyAccessor.FIELD,
      JsonAutoDetect.Visibility.ANY
  );

  @Autowired
  private ScenarioRepository scenarioRepository;

  public void launchScenarioFromUI(List<Scenario> scenarioToExecute,
                                   Project project,
                                   EnvironmentProperties properties,
                                   ExecutionResult executionResult,
                                   Set<String> stopExecutingSet,
                                   ProjectService projectService,
                                   String runningUuid) {
    log.info("Launch scenario from UI {} {}", scenarioToExecute, project);
    AtExecutor atExecutor = new AtExecutor();
    atExecutor.setProjectPath(Paths.get(properties.getProjectsDirectoryPath(), project.getCode()).toString());

    new Thread(() -> {
      IStopObserver stopObserver = () -> stopExecutingSet.remove(runningUuid);
      IExecutingFinishObserver finishObserver = scenarioResults -> {
        executionResult.setFinished(true);
        synchronized (projectService) {
          processResults(project, scenarioResults);
        }
      };
      atExecutor.executeScenarioList(project, scenarioToExecute, executionResult.getScenarioResults(), stopObserver, finishObserver);
    }).start();
  }

  @Override
  public List<ScenarioResult> launchScenarioFromCLI(List<Scenario> scenarioToExecute, Project project, EnvironmentProperties properties, AbstractReportGenerator reportGenerator) {
    log.info("Launch scenario from CLI {} {}", scenarioToExecute, project);
    AtExecutor atExecutor = new AtExecutor();
    atExecutor.setProjectPath(Paths.get(properties.getProjectsDirectoryPath(), project.getCode()).toString());

    List<ScenarioResult> scenarioResultList = new ArrayList<>();
    atExecutor.executeScenarioList(
        project,
        scenarioToExecute,
        scenarioResultList,
        () -> false,
        list -> {}
    );
    addResultsToReport(reportGenerator, scenarioResultList);
    return scenarioResultList;
  }

  private void addResultsToReport(AbstractReportGenerator reportGenerator, List<ScenarioResult> scenarioResultList) {
    for (ScenarioResult scenarioResult : scenarioResultList) {
      reportGenerator.add(scenarioResult.getScenario(), scenarioResult.getStepResultList());
    }
  }

  private void processResults(Project project, List<ScenarioResult> scenarioResults) {
    for (ScenarioResult scenarioResult: scenarioResults) {
      try {
        Scenario scenario = scenarioResult.getScenario();
        String scenarioGroup = scenario.getScenarioGroup();
        String scenarioPath = (StringUtils.isEmpty(scenarioGroup) ? "" : scenarioGroup + "/") + scenario.getCode();
        String groupDir = scenarioGroup != null ? scenarioGroup : DEFAULT_GROUP;

        Scenario scenarioToUpdate = scenarioRepository.findScenario(project.getCode(), scenarioPath);
        List<StepResult> stepResultList = scenarioResult.getStepResultList();
        scenarioToUpdate.setFailed(scenarioResult.isFailed());
        scenarioToUpdate.setHasResults(true);
        scenario = scenarioRepository.saveScenario(project.getCode(), scenarioPath, scenarioToUpdate, false);

        Path path = Paths.get("tmp", "results", project.getCode(), groupDir, scenario.getCode());
        if (!Files.exists(path)) {
          Files.createDirectories(path);
        }
        Path resultFile = path.resolve("results.json");
        Files.deleteIfExists(resultFile);
        objectMapper.writeValue(resultFile.toFile(), stepResultList);
      } catch (IOException e) {
        log.error("", e);
      }
    }
  }
}
