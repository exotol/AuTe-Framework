package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;

/**
 * Created by smakarov
 * 23.03.2018 11:00
 */
@Component
public class ExpectedResultAttachExtractor extends AbstractJsonAttachExtractor {

    private static final String FILE_NAME = "Expected result";

    @Override
    public Attachment extract(File resultDirectory, StepResult result) {
        if (StringUtils.isEmpty(result.getExpected())) {
            return null;
        }
        String formattedValue = formatJsonValue(result.getExpected());
        String relativePath = writeDataToFile(resultDirectory, formattedValue, FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType("application/json");
        }
        return null;
    }
}
