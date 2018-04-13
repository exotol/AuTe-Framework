package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.stepresult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.AbstractAttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by smakarov
 * 23.03.2018 13:29
 */
@Component
public class MessageQueueAttachExtractor extends AbstractAttachExtractor<StepResult> {

    private static final String FILE_NAME = "Message queue";

    @Override
    public List<Attachment> extract(File resultDirectory, StepResult result) {
        String name = result.getStep().getMqName();
        String message = result.getStep().getMqMessage();
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(message)) {
            return null;
        }
        String data = "Message queue name: " + name + "\nMessage:" + message;
        String relativePath = writeDataToFile(resultDirectory, data, FILE_NAME);
        if (relativePath != null) {
            return Collections.singletonList(new Attachment()
                    .withTitle(FILE_NAME)
                    .withSource(relativePath)
                    .withType(TEXT_PLAIN));
        }
        return null;
    }
}
