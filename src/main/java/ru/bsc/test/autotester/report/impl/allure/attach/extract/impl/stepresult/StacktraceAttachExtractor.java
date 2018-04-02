package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.stepresult;

import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.AbstractAttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;

/**
 * Created by smakarov
 * 23.03.2018 11:03
 */
@Component
public class StacktraceAttachExtractor extends AbstractAttachExtractor<StepResult> {

    private static final String FILE_NAME = "Stacktrace";

    @Override
    public Attachment extract(File resultDirectory, StepResult result) {
        if (StepResult.RESULT_OK.equals(result.getResult())) {
            return null;
        }
        String relativePath = writeDataToFile(resultDirectory, result.getDetails(), FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType(TEXT_PLAIN);
        }
        return null;
    }
}
