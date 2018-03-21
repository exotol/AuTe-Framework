package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;

/**
 * Created by smakarov
 * 23.03.2018 11:03
 */
@Component
public class StacktraceAttachExtractor extends AbstractAttachExtractor {
    @Override
    public Attachment extract(File resultDirectory, StepResult result) {
        if (StepResult.RESULT_OK.equals(result.getResult())) {
            return null;
        }
        String relativePath = writeDataToFile(resultDirectory, result.getDetails(), "Stacktrace");
        if (relativePath != null) {
            return new Attachment().withTitle("Stacktrace").withSource(relativePath).withType("text/plain");
        }
        return null;
    }
}
