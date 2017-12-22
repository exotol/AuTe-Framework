package ru.bsc.test.autotester.launcher;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.service.AtExecutor;
import ru.bsc.test.autotester.component.ProjectsSource;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.yaml.YamlUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TestLauncher {

    public void launch() {
        TestNG testNG = new TestNG();

        List<String> testFailedCount = new LinkedList<>();
        testNG.addListener(new ITestListener() {
            @Override
            public void onTestStart(ITestResult result) {}

            @Override
            public void onTestSuccess(ITestResult result) {}

            @Override
            public void onTestFailure(ITestResult result) {
                testFailedCount.add("fail");
            }

            @Override
            public void onTestSkipped(ITestResult result) {}

            @Override
            public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

            @Override
            public void onStart(ITestContext context) {}

            @Override
            public void onFinish(ITestContext context) {}
        });

        testNG.setTestClasses(new Class[]{ getClass() });
        testNG.run();

        System.exit(testFailedCount.size() > 0 ? 1 : 0);
    }

    @DataProvider
    private Object[][] dataProvider() throws IOException {
        EnvironmentProperties environmentProperties = YamlUtils.loadAs(new File("env.yml"), EnvironmentProperties.class);

        ProjectsSource projectsSource = new ProjectsSource();
        projectsSource.setEnvironmentProperties(environmentProperties);
        List<Project> projectList = projectsSource.getProjectList();

        List<Object[]> scenarios = new LinkedList<>();
        projectList.forEach(project -> {
            if (environmentProperties.getProjectStandMap() == null || !environmentProperties.getProjectStandMap().containsKey(project.getCode())) {
                return;
            }
            AtExecutor atExecutor = new AtExecutor();
            atExecutor.setProjectPath(environmentProperties.getProjectsDirectoryPath() + "/" + project.getCode() + "/");

            for (Scenario scenario: project.getScenarioList()) {
                scenarios.add(new Object[]{ scenario, project, atExecutor });
            }
        });

        return scenarios.toArray(new Object[scenarios.size()][3]);
    }

    @Test(dataProvider = "dataProvider")
    public void test(Scenario scenarioToExecute, Project project, AtExecutor atExecutor) {
        Map<Scenario, List<StepResult>> result = atExecutor.executeScenarioList(project, Collections.singletonList(scenarioToExecute));
        StringBuilder stringBuilder = new StringBuilder();
        int failureProjectCounter = result.entrySet().stream().mapToInt(value -> {
            Scenario scenario = value.getKey();
            List<StepResult> stepResultList = value.getValue();

            stringBuilder.append("Scenario [")
                    .append(scenario.getScenarioGroup() == null ? "" : scenario.getScenarioGroup() + " : ")
                    .append(scenario.getCode()).append("] ")
                    .append(scenario.getName()).append("\r\n");
            return stepResultList.stream().mapToInt(stepResult -> {
                if (!"OK".equals(stepResult.getResult())) {
                    stringBuilder
                            .append("\r\n\r\n\r\n\r\n\r\n")
                            .append(stepResult.getResult()).append("\t")
                            .append("[").append(stepResult.getStep().getCode()).append("]\t")
                            .append(stepResult.getStep().getRequestMethod()).append(" ").append(stepResult.getRequestUrl()).append("\r\n");
                    if (stepResult.getDetails() != null) {
                        stringBuilder.append(stepResult.getDetails()).append("\r\n");
                    }
                    return 1;
                } else {
                    stringBuilder
                            .append("\r\n\r\n\r\n\r\n\r\n")
                            .append(stepResult.getResult()).append("\t")
                            .append("[").append(stepResult.getStep().getCode()).append("]\t")
                            .append(stepResult.getStep().getRequestMethod()).append(" ").append(stepResult.getRequestUrl()).append("\r\n");
                    return 0;
                }
            }).sum();
        }).sum();

        Assert.assertEquals(failureProjectCounter, 0, stringBuilder.toString());
    }
}
