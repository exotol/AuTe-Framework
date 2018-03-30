package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.stepresult;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.RequestData;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.AbstractJsonAttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by smakarov
 * 23.03.2018 10:46
 */
@Component
public class ActualResultAttachExtractor extends AbstractJsonAttachExtractor<StepResult> {

    private static final String FILE_NAME = "Actual result";

    @Override
    public Attachment extract(File resultDirectory, StepResult result) {
        if (result.getStep().getExpectedResponseIgnore() || isEmpty(result.getActual())) {
            return null;
        }
        List<RequestData> requestDataList = result.getRequestDataList();
        if (CollectionUtils.isNotEmpty(requestDataList) && requestDataList.size() > 1) {
            return null;
        }
        String data = result.getActual();
        String relativePath = writeDataToFile(resultDirectory, data, FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType(APPLICATION_JSON);
        }
        return null;
    }
}
