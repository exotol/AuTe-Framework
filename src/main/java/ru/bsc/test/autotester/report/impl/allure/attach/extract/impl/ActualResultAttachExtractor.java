package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;

/**
 * Created by smakarov
 * 23.03.2018 10:46
 */
@Component
public class ActualResultAttachExtractor extends AbstractJsonAttachExtractor {

    private static final String FILE_NAME = "Actual result";

    @Override
    public Attachment extract(File resultDirectory, StepResult result) {
        if (StringUtils.isEmpty(result.getActual())) {
            return null;
        }
        String formattedValue = formatJsonValue(result.getActual());
        String relativePath = writeDataToFile(resultDirectory, formattedValue, FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType(APPLICATION_JSON);
        }
        return null;
    }
}
