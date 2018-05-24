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
 * 23.03.2018 11:34
 */
@Component
public class HeadersAttachExtractor extends AbstractAttachExtractor<StepResult> {

    private static final String FILE_NAME = "Headers";

    @Override
    public List<Attachment> extract(File resultDirectory, StepResult result) {
        String headers = result.getStep().getRequestHeaders();
        if (StringUtils.isEmpty(headers)) {
            return null;
        }
        String relativePath = writeDataToFile(resultDirectory, headers, FILE_NAME);
        if (relativePath != null) {
            return Collections.singletonList(new Attachment()
                    .withTitle(FILE_NAME)
                    .withSource(relativePath)
                    .withType(TEXT_PLAIN));
        }
        return null;
    }
}
