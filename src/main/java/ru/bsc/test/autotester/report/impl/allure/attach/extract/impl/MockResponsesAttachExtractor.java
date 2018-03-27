package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.MockServiceResponse;
import ru.bsc.test.at.executor.model.StepResult;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by smakarov
 * 23.03.2018 11:39
 */
@Component
public class MockResponsesAttachExtractor extends AbstractAttachExtractor {

    private static final String FILE_NAME = "Mock responses";

    @Override
    public Attachment extract(File resultDirectory, StepResult result) {
        List<MockServiceResponse> responseList = result.getStep().getMockServiceResponseList();
        if (CollectionUtils.isEmpty(responseList)) {
            return null;
        }
        String requestsData = getMockResponseData(responseList);
        String relativePath = writeDataToFile(resultDirectory, requestsData, FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType(TEXT_PLAIN);
        }
        return null;
    }

    private String getMockResponseData(List<MockServiceResponse> responseList) {
        return responseList.stream()
                .map(response ->
                        response.getServiceUrl() +
                        ", " +
                        response.getHttpStatus() +
                        ", " +
                        response.getContentType() +
                        ":\n" +
                        response.getResponseBody())
                .collect(Collectors.joining("\n\n"));
    }
}
