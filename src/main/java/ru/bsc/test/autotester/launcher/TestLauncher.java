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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class TestLauncher {

    private final static String PROPERTIES_FILE_NAME = "environment.properties";
    private final static String PROJECTS_PATH_PROPERTY = "projects.directory.path";

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
    private Object[][] dataProvider() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(PROPERTIES_FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        ProjectsSource projectsSource = new ProjectsSource();
        projectsSource.setDirectoryPath(prop.getProperty(PROJECTS_PATH_PROPERTY));
        List<Project> projectList = projectsSource.getProjectList();

        List<Object[]> scenarios = new LinkedList<>();
        projectList.forEach(project -> {
            System.out.println("Project: [" + project.getCode() + "]" + project.getName());
            AtExecutor atExecutor = new AtExecutor();
            atExecutor.setProjectPath(prop.getProperty(PROJECTS_PATH_PROPERTY) + "/" + project.getCode() + "/");

            for (Scenario scenario: project.getScenarioList()) {
                scenarios.add(new Object[]{ scenario, project, atExecutor });
            }

        });

        return scenarios.subList(0, 20).toArray(new Object[scenarios.subList(0, 20).size()][3]);
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
