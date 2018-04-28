package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.stepresult;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.MqMessage;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.AbstractAttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smakarov
 * 23.03.2018 13:29
 */
@Component
public class MessageQueueAttachExtractor extends AbstractAttachExtractor<StepResult> {

    private static final String FILE_NAME_TEMPLATE = "MQ Message %d";

    @Override
    public List<Attachment> extract(File resultDirectory, StepResult result) {
        if (CollectionUtils.isEmpty(result.getStep().getMqMessages())) {
            return null;
        }
        List<MqMessage> messages = result.getStep().getMqMessages();
        List<Attachment> attachments = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            MqMessage message = messages.get(i);
            String data = "Message queue name: " + message.getQueueName() + "\nMessage:\n" + message.getMessage();
            String attachName = String.format(FILE_NAME_TEMPLATE, i + 1);
            String relativePath = writeDataToFile(resultDirectory, data, attachName);
            if (relativePath != null) {
                attachments.add(new Attachment().withTitle(attachName).withSource(relativePath).withType(TEXT_PLAIN));
            }
        }
        return attachments;
    }
}
