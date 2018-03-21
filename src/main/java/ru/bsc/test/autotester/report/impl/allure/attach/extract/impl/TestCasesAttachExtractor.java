package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepParameter;
import ru.bsc.test.at.executor.model.StepParameterSet;
import ru.bsc.test.at.executor.model.StepResult;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by smakarov
 * 23.03.2018 12:16
 */
@Component
public class TestCasesAttachExtractor extends AbstractAttachExtractor {

    private static final String FILE_NAME = "Test cases";

    @Override
    public Attachment extract(File resultDirectory, StepResult result) {
        List<StepParameterSet> testCases = result.getStep().getStepParameterSetList();
        if (CollectionUtils.isEmpty(testCases)) {
            return null;
        }

        String testCasesData = getTestCasesData(testCases);
        String relativePath = writeDataToFile(resultDirectory, testCasesData, FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType("text/plain");
        }
        return null;
    }

    private String getTestCasesData(List<StepParameterSet> testCases) {
        StepParameterSet first = testCases.get(0);
        String header = first.getStepParameterList().stream()
                .map(StepParameter::getName)
                .collect(Collectors.joining(" | "));
        String body = testCases.stream().map(stepParameterSet ->
                stepParameterSet.getDescription() + ": " +
                stepParameterSet.getStepParameterList().stream()
                        .map(StepParameter::getValue)
                        .collect(Collectors.joining(" | "))
        ).collect(Collectors.joining("\n"));
        return header + "\n" + body;
    }
}
