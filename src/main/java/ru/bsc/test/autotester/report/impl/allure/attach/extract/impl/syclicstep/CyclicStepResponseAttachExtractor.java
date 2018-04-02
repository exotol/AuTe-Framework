package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.syclicstep;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.RequestData;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.AbstractJsonAttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;

/**
 * Created by smakarov
 * 30.03.2018 12:09
 */
@Component
public class CyclicStepResponseAttachExtractor extends AbstractJsonAttachExtractor<RequestData> {

    private static final String FILE_NAME = "Response";

    @Override
    public Attachment extract(File resultDirectory, RequestData result) {
        if (StringUtils.isEmpty(result.getResponseBody())) {
            return null;
        }
        String data = result.getResponseBody();
        String relativePath = writeDataToFile(resultDirectory, data, FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType(APPLICATION_JSON);
        }
        return null;
    }
}
