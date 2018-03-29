package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;

/**
 * Created by smakarov
 * 23.03.2018 13:41
 */
@Component
public class PollingAttachExtractor extends AbstractAttachExtractor {

    private static final String FILE_NAME = "Polling";

    @Override
    public Attachment extract(File resultDirectory, StepResult result) {
        if (!result.getStep().getUsePolling()) {
            return null;
        }
        String data = "Polling JSON XPath: " + result.getStep().getPollingJsonXPath();
        String relativePath = writeDataToFile(resultDirectory, data, FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType(TEXT_PLAIN);
        }
        return null;
    }
}
