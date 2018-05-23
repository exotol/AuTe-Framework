package ru.bsc.test.autotester.launcher.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.ScenarioResult;
import ru.bsc.test.autotester.launcher.api.TestLauncher;
import ru.bsc.test.autotester.launcher.api.ScenarioLauncher;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.report.AbstractReportGenerator;
import ru.bsc.test.autotester.repository.ProjectRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestLauncherImpl implements TestLauncher {
    private final EnvironmentProperties properties;
    private final ProjectRepository projectRepository;
    private final AbstractReportGenerator reportGenerator;

    public TestLauncherImpl(
            EnvironmentProperties properties,
            ProjectRepository projectRepository,
            AbstractReportGenerator reportGenerator
    ) {
        this.properties = properties;
        this.projectRepository = projectRepository;
        this.reportGenerator = reportGenerator;
    }

    @Autowired
    private ScenarioLauncher scenarioLauncher;

    @Override
    public void launch() throws Exception {
        List<Project> projectList = projectRepository.findAllProjectsWithScenarios();

        List<ScenarioResult> scenarioResults = new ArrayList<>();

        for (Project project: projectList) {
            if (properties.getProjectStandMap() == null || !properties.getProjectStandMap().containsKey(project.getCode())) {
                continue;
            }
            scenarioLauncher.launchScenarioFromCLI(project.getScenarioList(), project, properties, reportGenerator, scenarioResults);
        }
        LaunchResult launchResult = new LaunchResult(scenarioResults);
        reportGenerator.generate(new File("." + File.separator + "report"));
        TestRunnerExitCodeContext.getInstance().setExitCode(launchResult.isFailed() ? 1 : 0);
    }
}
