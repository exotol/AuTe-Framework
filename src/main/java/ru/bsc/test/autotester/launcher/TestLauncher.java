package ru.bsc.test.autotester.launcher;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.service.AtExecutor;
import ru.bsc.test.autotester.component.ProjectsSource;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.report.ReportGenerator;
import ru.bsc.test.autotester.yaml.YamlUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TestLauncher {

    public void launch() throws IOException {

        EnvironmentProperties environmentProperties = YamlUtils.loadAs(new File("env.yml"), EnvironmentProperties.class);

        ProjectsSource projectsSource = new ProjectsSource();
        projectsSource.setEnvironmentProperties(environmentProperties);
        List<Project> projectList = projectsSource.getProjectList();
        ReportGenerator reportGenerator = new ReportGenerator();

        int testFailedCount = projectList.stream().mapToInt(project -> {
            if (environmentProperties.getProjectStandMap() == null || !environmentProperties.getProjectStandMap().containsKey(project.getCode())) {
                return 0;
            }
            AtExecutor atExecutor = new AtExecutor();
            atExecutor.setProjectPath(environmentProperties.getProjectsDirectoryPath() + "/" + project.getCode() + "/");

            int scenarioFailedCount = 0;
            for (Scenario scenario: project.getScenarioList().subList(0, Math.min(project.getScenarioList().size(), 5))) {
                scenarioFailedCount += test(scenario, project, atExecutor, reportGenerator);
            }
            return scenarioFailedCount;
        }).sum();

        reportGenerator.generateHtml(new File("./report/"));
        System.exit(testFailedCount > 0 ? 1 : 0);
    }

    private int test(Scenario scenarioToExecute, Project project, AtExecutor atExecutor, ReportGenerator reportGenerator) {
        Map<Scenario, List<StepResult>> result = atExecutor.executeScenarioList(project, Collections.singletonList(scenarioToExecute));

        return result.entrySet().stream().mapToInt(value -> {
            Scenario scenario = value.getKey();
            List<StepResult> stepResultList = value.getValue();

            reportGenerator.add(scenario, stepResultList);
            return stepResultList.stream().mapToInt(stepResult -> ("OK".equals(stepResult.getResult())) ? 0 : 1).sum();
        }).sum();
    }
}
