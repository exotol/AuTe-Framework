package ru.bsc.test.autotester.report;

import com.google.gson.Gson;
import io.qameta.allure.ConfigurationBuilder;
import io.qameta.allure.ReportGenerator;
import io.qameta.allure.core.Configuration;
import org.apache.commons.lang3.StringUtils;
import ru.bsc.test.at.executor.model.StepResult;
import ru.yandex.qatools.allure.model.Description;
import ru.yandex.qatools.allure.model.DescriptionType;
import ru.yandex.qatools.allure.model.Failure;
import ru.yandex.qatools.allure.model.Label;
import ru.yandex.qatools.allure.model.Parameter;
import ru.yandex.qatools.allure.model.ParameterKind;
import ru.yandex.qatools.allure.model.Status;
import ru.yandex.qatools.allure.model.TestCaseResult;
import ru.yandex.qatools.allure.model.TestSuiteResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class AllureReportGenerator extends AbstractReportGenerator {

    @Override
    public void generate(File directory) throws Exception {

        File resultDirectory = new File(directory + File.separator + "results-directory");

        if (!resultDirectory.exists()) {
            if (!resultDirectory.mkdir()) {
                throw new Exception("mkdir failed: " + resultDirectory.getAbsolutePath());
            }
        }

        int[] scenarioIndex = { 0 };

        scenarioStepResultMap.forEach((scenario, stepResultList) -> {
            scenarioIndex[0]++;
            TestSuiteResult testSuiteResult = new TestSuiteResult();
            testSuiteResult.setName(scenarioIndex[0] + " " + scenario.getCode());
            testSuiteResult.setTitle(scenarioIndex[0] + " " + scenario.getName());
            testSuiteResult.setStart(stepResultList.stream().mapToLong(StepResult::getStart).min().orElse(0));
            testSuiteResult.setStop(stepResultList.stream().mapToLong(StepResult::getStop).max().orElse(0));
            testSuiteResult.setLabels(Collections.singletonList(new Label().withName("group").withValue(scenario.getScenarioGroup())));

            List<TestCaseResult> testCaseResultList = new LinkedList<>();
            testSuiteResult.setTestCases(testCaseResultList);

            int[] stepResultIndex = { 0 };

            stepResultList.forEach(stepResult -> {
                stepResultIndex[0]++;
                TestCaseResult testCaseResult = new TestCaseResult()
                        .withName(stepResultIndex[0] + " " + scenario.getCode() + stepResult.getStep().getCode())
                        .withTitle(stepResultIndex[0] + " " + stepResult.getStep().getRelativeUrl())
                        .withDescription(new Description().withValue(stepResult.getActual()).withType(DescriptionType.TEXT))
                        .withStart(stepResult.getStart())
                        .withStop(stepResult.getStop())
                        .withStatus(StepResult.RESULT_OK.equals(stepResult.getResult()) ? Status.PASSED : Status.FAILED);

                if (StringUtils.isNotEmpty(stepResult.getStep().getStepComment())) {
                    testCaseResult.withParameters(new Parameter().withKind(ParameterKind.ARGUMENT).withName("Comment").withValue(stepResult.getStep().getStepComment()));
                }

                if (!StepResult.RESULT_OK.equals(stepResult.getResult())) {
                    testCaseResult.setFailure(new Failure().withStackTrace(stepResult.getDetails()).withMessage(stepResult.getResult()));
                }

                testCaseResultList.add(testCaseResult);
            });

            try (FileWriter writer = new FileWriter(new File(resultDirectory + File.separator + UUID.randomUUID() + "-testsuite.json"))) {
                new Gson().toJson(testSuiteResult, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Configuration configuration = new ConfigurationBuilder().useDefault().build();

        final ReportGenerator generator = new ReportGenerator(configuration);
        Path output = new File(directory + File.separator + "output").toPath();
        final Path resultsDirectory = resultDirectory.toPath();
        generator.generate(output, resultsDirectory);
    }
}
