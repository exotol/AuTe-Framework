package ru.bsc.test.autotester.report.impl.allure;

import com.google.gson.Gson;
import io.qameta.allure.ConfigurationBuilder;
import io.qameta.allure.ReportGenerator;
import io.qameta.allure.allure1.Allure1Plugin;
import io.qameta.allure.allure2.Allure2Plugin;
import io.qameta.allure.category.CategoriesPlugin;
import io.qameta.allure.category.CategoriesTrendPlugin;
import io.qameta.allure.context.FreemarkerContext;
import io.qameta.allure.context.JacksonContext;
import io.qameta.allure.context.MarkdownContext;
import io.qameta.allure.context.RandomUidContext;
import io.qameta.allure.core.*;
import io.qameta.allure.duration.DurationPlugin;
import io.qameta.allure.duration.DurationTrendPlugin;
import io.qameta.allure.environment.Allure1EnvironmentPlugin;
import io.qameta.allure.executor.ExecutorPlugin;
import io.qameta.allure.history.HistoryPlugin;
import io.qameta.allure.history.HistoryTrendPlugin;
import io.qameta.allure.influxdb.InfluxDbExportPlugin;
import io.qameta.allure.launch.LaunchPlugin;
import io.qameta.allure.mail.MailPlugin;
import io.qameta.allure.owner.OwnerPlugin;
import io.qameta.allure.prometheus.PrometheusExportPlugin;
import io.qameta.allure.retry.RetryPlugin;
import io.qameta.allure.retry.RetryTrendPlugin;
import io.qameta.allure.severity.SeverityPlugin;
import io.qameta.allure.status.StatusChartPlugin;
import io.qameta.allure.suites.SuitesPlugin;
import io.qameta.allure.summary.SummaryPlugin;
import io.qameta.allure.tags.TagsPlugin;
import io.qameta.allure.timeline.TimelinePlugin;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.StepParameterSet;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.report.AbstractReportGenerator;
import ru.bsc.test.autotester.report.impl.allure.attach.AttachResultBuilder;
import ru.bsc.test.autotester.report.impl.allure.plugin.DefaultCategoriesPlugin;
import ru.yandex.qatools.allure.model.Step;
import ru.yandex.qatools.allure.model.TestCaseResult;
import ru.yandex.qatools.allure.model.TestSuiteResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static ru.yandex.qatools.allure.model.Status.FAILED;
import static ru.yandex.qatools.allure.model.Status.PASSED;

@Slf4j
@Component
public class AllureReportGenerator extends AbstractReportGenerator {
    private static final String WITHOUT_GROUP = "Без группы";

    private final Gson gson = new Gson();
    private final AttachResultBuilder attachBuilder;

    @Autowired
    public AllureReportGenerator(AttachResultBuilder attachBuilder) {
        this.attachBuilder = attachBuilder;
    }

    @Override
    public void generate(File directory) throws Exception {
        File resultDirectory = new File(directory, "results-directory");

        if (!resultDirectory.exists() && !resultDirectory.mkdirs()) {
            throw new Exception("mkdirs failed: " + resultDirectory.getAbsolutePath());
        }

        for (AllurePreparedData data : buildReportData(resultDirectory, getScenarioStepResultMap())) {
            try (FileWriter writer = new FileWriter(data.getDataFile())) {
                gson.toJson(data.getSuiteResult(), writer);
            } catch (IOException e) {
                log.error("Could not convert testSuiteResult {} to json", data, e);
            }
        }

        Configuration configuration = createConfiguration();
        final ReportGenerator generator = new ReportGenerator(configuration);
        Path output = new File(directory + File.separator + "output").toPath();
        final Path resultsDirectory = resultDirectory.toPath();
        generator.generate(output, resultsDirectory);
    }

    private Configuration createConfiguration() {
        return new ConfigurationBuilder()
                .fromExtensions(Arrays.asList(
                        new JacksonContext(),
                        new MarkdownContext(),
                        new FreemarkerContext(),
                        new RandomUidContext(),
                        new MarkdownDescriptionsPlugin(),
                        new RetryPlugin(),
                        new RetryTrendPlugin(),
                        new TagsPlugin(),
                        new SeverityPlugin(),
                        new OwnerPlugin(),
                        new DefaultCategoriesPlugin(),
                        new CategoriesPlugin(),
                        new CategoriesTrendPlugin(),
                        new HistoryPlugin(),
                        new HistoryTrendPlugin(),
                        new DurationPlugin(),
                        new DurationTrendPlugin(),
                        new StatusChartPlugin(),
                        new TimelinePlugin(),
                        new SuitesPlugin(),
                        new ReportWebPlugin(),
                        new TestsResultsPlugin(),
                        new AttachmentsPlugin(),
                        new MailPlugin(),
                        new InfluxDbExportPlugin(),
                        new PrometheusExportPlugin(),
                        new SummaryPlugin(),
                        new ExecutorPlugin(),
                        new LaunchPlugin(),
                        new Allure1Plugin(),
                        new Allure1EnvironmentPlugin(),
                        new Allure2Plugin()
                )).build();
    }

    private List<AllurePreparedData> buildReportData(
            File resultDirectory,
            Map<Scenario, List<StepResult>> scenarioStepResultMap
    ) {
        Set<String> groups = scenarioStepResultMap.keySet().stream()
                .map(Scenario::getScenarioGroup)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        groups.add(WITHOUT_GROUP);
        return groups.stream()
                .map(group -> buildTestSuiteData(resultDirectory, group, scenarioStepResultMap))
                .collect(Collectors.toList());
    }

    private AllurePreparedData buildTestSuiteData(
            File resultDirectory,
            String group,
            Map<Scenario, List<StepResult>> scenarioListMap
    ) {
        File dataFile = new File(resultDirectory + File.separator + UUID.randomUUID() + "-testsuite.json");
        TestSuiteResult suiteResult = new TestSuiteResult()
                .withName(group)
                .withTestCases(buildTestCasesData(resultDirectory, group, scenarioListMap));
        return AllurePreparedData.of(dataFile, suiteResult);
    }

    private List<TestCaseResult> buildTestCasesData(
            File resultDirectory,
            String group,
            Map<Scenario, List<StepResult>> scenarioStepResultMap
    ) {
        return scenarioStepResultMap.entrySet().stream()
                .filter(entry -> group.equals(entry.getKey().getScenarioGroup() == null ?
                                              WITHOUT_GROUP :
                                              entry.getKey().getScenarioGroup()))
                .map(entry -> buildTestCaseData(resultDirectory, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private TestCaseResult buildTestCaseData(File resultDirectory, Scenario scenario, List<StepResult> stepResults) {
        List<Step> steps = buildStepsData(resultDirectory, stepResults);
        return new TestCaseResult()
                .withName(scenario.getName())
                .withStart(steps.stream().mapToLong(Step::getStart).min().orElse(0))
                .withStop(steps.stream().mapToLong(Step::getStop).max().orElse(0))
                .withStatus(steps.stream().anyMatch(step -> FAILED.equals(step.getStatus())) ? FAILED : PASSED)
                .withSteps(steps);
    }

    private List<Step> buildStepsData(File resultDirectory, List<StepResult> stepResults) {
        return stepResults.stream()
                .collect(Collectors.groupingBy(StepResult::getStep))
                .entrySet()
                .stream()
                .map(buildSteps(resultDirectory))
                .flatMap(List::stream)
                .sorted(Comparator.comparingLong(Step::getStart))
                .collect(Collectors.toList());
    }

    private Function<Map.Entry<ru.bsc.test.at.executor.model.Step, List<StepResult>>, List<Step>> buildSteps(File resultDirectory) {
        return resultGroup -> {
            ru.bsc.test.at.executor.model.Step step = resultGroup.getKey();
            if (CollectionUtils.isEmpty(step.getStepParameterSetList())) {
                StepResult result = resultGroup.getValue().get(0);
                return Collections.singletonList(buildStep(resultDirectory, stepName(step), result));
            } else {
                return testCaseSteps(resultDirectory, step, resultGroup.getValue());
            }
        };
    }

    private List<Step> testCaseSteps(
            File resultDirectory,
            ru.bsc.test.at.executor.model.Step step,
            List<StepResult> results
    ) {
        List<StepParameterSet> testCases = step.getStepParameterSetList();
        List<Step> steps = new ArrayList<>();
        for (int i = 0; i < testCases.size(); i++) {
            StepParameterSet testCase = testCases.get(i);
            String stepName = stepName(step);
            String testCaseName = isNotEmpty(testCase.getDescription()) ? testCase.getDescription() : valueOf(i + 1);
            String name = stepName + " - " + testCaseName;
            StepResult stepResult = results.get(i);
            steps.add(buildStep(resultDirectory, name, stepResult));
        }
        return steps;
    }

    private Step buildStep(File resultDirectory, String name, StepResult result) {
        return new Step()
                .withName(name)
                .withStart(result.getStart())
                .withStop(result.getStop())
                .withStatus(StepResult.RESULT_OK.equals(result.getResult()) ? PASSED : FAILED)
                .withAttachments(attachBuilder.build(resultDirectory, result));
    }

    private String stepName(ru.bsc.test.at.executor.model.Step step) {
        return isNotEmpty(step.getStepComment()) ? step.getStepComment() : step.getCode();
    }
}
