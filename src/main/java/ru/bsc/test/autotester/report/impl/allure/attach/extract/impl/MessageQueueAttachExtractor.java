package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;

/**
 * Created by smakarov
 * 23.03.2018 13:29
 */
@Component
public class MessageQueueAttachExtractor extends AbstractAttachExtractor {

    private static final String FILE_NAME = "Message queue";

    @Override
    public Attachment extract(File resultDirectory, StepResult result) {
        String name = result.getStep().getMqName();
        String message = result.getStep().getMqMessage();
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(message)) {
            return null;
        }
        String data = "Message queue name: " + name + "\nMessage:" + message;
        String relativePath = writeDataToFile(resultDirectory, data, FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType(TEXT_PLAIN);
        }
        return null;
    }
}
