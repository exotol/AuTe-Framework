package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.stepresult;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.ExpectedMqRequest;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.AbstractAttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by smakarov
 * 23.03.2018 14:30
 */
@Component
public class MqRequestsAttachExtractor extends AbstractAttachExtractor<StepResult> {

    private static final String FILE_NAME = "MQ requests";

    @Override
    public Attachment extract(File resultDirectory, StepResult result) {
        List<ExpectedMqRequest> requests = result.getStep().getExpectedMqRequestList();
        if (CollectionUtils.isEmpty(requests)) {
            return null;
        }

        String requestsData = getMqRequestsData(requests);
        String relativePath = writeDataToFile(resultDirectory, requestsData, FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType(TEXT_PLAIN);
        }
        return null;
    }

    private String getMqRequestsData(List<ExpectedMqRequest> requests) {
        return requests.stream()
                .map(request ->
                        "Source queue name: " +
                        request.getSourceQueue() +
                        "\nIgnored tags:" +
                        request.getIgnoredTags() +
                        "\nExpected MQ request:\n" +
                        request.getRequestBody()
                ).collect(Collectors.joining("\n\n"));
    }
}
