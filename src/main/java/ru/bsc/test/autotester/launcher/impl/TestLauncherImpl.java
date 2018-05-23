package ru.bsc.test.autotester.launcher.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.ScenarioResult;
import ru.bsc.test.autotester.launcher.api.ScenarioLauncher;
import ru.bsc.test.autotester.launcher.api.TestLauncher;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.report.AbstractReportGenerator;
import ru.bsc.test.autotester.repository.ProjectRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TestLauncherImpl implements TestLauncher {
    private final EnvironmentProperties properties;
    private final ProjectRepository projectRepository;
    private final AbstractReportGenerator reportGenerator;
    private final ScenarioLauncher scenarioLauncher;

    public TestLauncherImpl(
            EnvironmentProperties properties,
            ProjectRepository projectRepository,
            AbstractReportGenerator reportGenerator,
            ScenarioLauncher scenarioLauncher
    ) {
        this.properties = properties;
        this.projectRepository = projectRepository;
        this.reportGenerator = reportGenerator;
        this.scenarioLauncher = scenarioLauncher;
    }

    @Override
    public void launch() throws Exception {
        log.info("Launch scenarios ...");
        List<Project> projectList = projectRepository.findAllProjectsWithScenarios();
        List<ScenarioResult> scenarioResults = new ArrayList<>();

        for (Project project: projectList) {
            if (properties.getProjectStandMap() == null || !properties.getProjectStandMap().containsKey(project.getCode())) {
                continue;
            }
            log.info("Launch scenarios for project {}", project.getName());
            scenarioLauncher.launchScenarioFromCLI(project.getScenarioList(), project, properties, reportGenerator, scenarioResults);
        }
        LaunchResult launchResult = new LaunchResult(scenarioResults);
        log.info("Launch result {}", launchResult);
        reportGenerator.generate(new File("." + File.separator + "report"));
        log.info("Report for test run generated");
        TestRunnerExitCodeContext.getInstance().setExitCode(launchResult.isFailed() ? 1 : 0);
    }
}
