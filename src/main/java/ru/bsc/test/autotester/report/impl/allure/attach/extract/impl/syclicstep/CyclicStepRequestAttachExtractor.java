package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.syclicstep;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.RequestData;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.AbstractJsonAttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;

/**
 * Created by smakarov
 * 30.03.2018 12:08
 */
@Component
public class CyclicStepRequestAttachExtractor extends AbstractJsonAttachExtractor<RequestData> {

    private static final String FILE_NAME = "Request";

    @Override
    public Attachment extract(File resultDirectory, RequestData result) {
        if (StringUtils.isEmpty(result.getRequestBody())) {
            return null;
        }
        String data = result.getRequestBody();
        String relativePath = writeDataToFile(resultDirectory, data, FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType(APPLICATION_JSON);
        }
        return null;
    }
}
