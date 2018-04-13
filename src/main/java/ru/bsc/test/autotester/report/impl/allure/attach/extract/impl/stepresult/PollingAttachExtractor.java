package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.stepresult;

import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.AbstractAttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by smakarov
 * 23.03.2018 13:41
 */
@Component
public class PollingAttachExtractor extends AbstractAttachExtractor<StepResult> {

    private static final String FILE_NAME = "Polling";

    @Override
    public List<Attachment> extract(File resultDirectory, StepResult result) {
        if (!result.getStep().getUsePolling()) {
            return null;
        }
        String data = "Polling JSON XPath: " + result.getStep().getPollingJsonXPath();
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
