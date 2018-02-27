package ru.bsc.test.autotester.report;

import com.google.gson.Gson;
import io.qameta.allure.ConfigurationBuilder;
import io.qameta.allure.ReportGenerator;
import io.qameta.allure.core.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.StepResult;
import ru.yandex.qatools.allure.model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

@Slf4j
public class AllureReportGenerator extends AbstractReportGenerator {
    @Override
    public void generate(File directory) throws Exception {
        File resultDirectory = new File(directory + File.separator + "results-directory");

        if (!resultDirectory.exists() && !resultDirectory.mkdir()) {
            throw new Exception("mkdir failed: " + resultDirectory.getAbsolutePath());
        }

        int scenarioIndex = 0;
        for (Entry<Scenario, List<StepResult>> entry : getScenarioStepResultMap().entrySet()) {
            Scenario scenario = entry.getKey();
            List<StepResult> stepResultList = entry.getValue();
            TestSuiteResult testSuiteResult = createTestSuiteResult(scenarioIndex++, entry);
            int stepResultIndex = 0;
            List<TestCaseResult> testCaseResultList = new LinkedList<>();
            for (StepResult stepResult : stepResultList) {
                testCaseResultList.add(createTestCaseResult(stepResultIndex++, scenario, stepResult));
            }
            testSuiteResult.setTestCases(testCaseResultList);

            try (FileWriter writer = new FileWriter(new File(resultDirectory + File.separator + UUID.randomUUID() + "-testsuite.json"))) {
                new Gson().toJson(testSuiteResult, writer);
            } catch (IOException e) {
                log.error("Could not convert testSuiteResult {} to json", testSuiteResult, e);
            }
        }

        Configuration configuration = new ConfigurationBuilder().useDefault().build();
        final ReportGenerator generator = new ReportGenerator(configuration);
        Path output = new File(directory + File.separator + "output").toPath();
        final Path resultsDirectory = resultDirectory.toPath();
        generator.generate(output, resultsDirectory);
    }

    private TestSuiteResult createTestSuiteResult(long scenarioIndex, Entry<Scenario, List<StepResult>> entry) {
        Scenario scenario = entry.getKey();
        List<StepResult> stepResultList = entry.getValue();
        TestSuiteResult testSuiteResult = new TestSuiteResult();
        testSuiteResult.setName(scenarioIndex + " " + scenario.getCode());
        testSuiteResult.setTitle(scenarioIndex + " " + scenario.getName());
        testSuiteResult.setStart(stepResultList.stream().mapToLong(StepResult::getStart).min().orElse(0));
        testSuiteResult.setStop(stepResultList.stream().mapToLong(StepResult::getStop).max().orElse(0));
        testSuiteResult.setLabels(Collections.singletonList(new Label().withName("group").withValue(scenario.getScenarioGroup())));
        return testSuiteResult;
    }

    private TestCaseResult createTestCaseResult(int index, Scenario scenario, StepResult stepResult) {
        TestCaseResult testCaseResult = new TestCaseResult()
                .withName(index + " " + scenario.getCode() + stepResult.getStep().getCode())
                .withTitle(index + " " + stepResult.getStep().getRelativeUrl())
                .withDescription(new Description().withValue("Actual:\n\n" + stepResult.getActual() + "\n\nExpected:\n\n" + stepResult.getExpected()).withType(DescriptionType.TEXT))
                .withStart(stepResult.getStart())
                .withStop(stepResult.getStop())
                .withStatus(StepResult.RESULT_OK.equals(stepResult.getResult()) ? Status.PASSED : Status.FAILED);

        if (StringUtils.isNotEmpty(stepResult.getStep().getStepComment())) {
            testCaseResult.withParameters(new Parameter().withKind(ParameterKind.ARGUMENT).withName("Comment").withValue(stepResult.getStep().getStepComment()));
        }

        if (!StepResult.RESULT_OK.equals(stepResult.getResult())) {
            testCaseResult.setFailure(new Failure().withStackTrace(stepResult.getDetails()).withMessage(stepResult.getResult()));
        }
        return testCaseResult;
    }
}
