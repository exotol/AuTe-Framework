package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.stepresult;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.AbstractAttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by smakarov
 * 23.03.2018 11:50
 */
@Component
public class MockRequestsAttachExtractor extends AbstractAttachExtractor<StepResult> {

    private static final String FILE_NAME = "Mock requests";

    @Override
    public List<Attachment> extract(File resultDirectory, StepResult result) {
        List<ExpectedServiceRequest> requests = result.getStep().getExpectedServiceRequests();
        if (CollectionUtils.isEmpty(requests)) {
            return null;
        }
        String requestsData = getRequestsData(requests);
        String relativePath = writeDataToFile(resultDirectory, requestsData, FILE_NAME);
        if (relativePath != null) {
            return Collections.singletonList(new Attachment()
                    .withTitle(FILE_NAME)
                    .withSource(relativePath)
                    .withType(TEXT_PLAIN));
        }
        return null;
    }

    private String getRequestsData(List<ExpectedServiceRequest> requests) {
        return requests.stream()
                .map(request ->
                        "Service name:" +
                        request.getServiceName() +
                        "\nIgnored tags:" +
                        request.getIgnoredTags() +
                        "\nExpected request:\n" +
                        request.getExpectedServiceRequest()
                ).collect(Collectors.joining("\n\n"));
    }
}
