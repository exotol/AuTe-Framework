package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.MqMockResponse;
import ru.bsc.test.at.executor.model.StepResult;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by smakarov
 * 23.03.2018 14:17
 */
@Component
public class MqResponsesAttachExtractor extends AbstractAttachExtractor {

    private static final String FILE_NAME = "MQ responses";

    @Override
    public Attachment extract(File resultDirectory, StepResult result) {
        List<MqMockResponse> responses = result.getStep().getMqMockResponseList();
        if (CollectionUtils.isEmpty(responses)) {
            return null;
        }

        String responsesData = getMqResponsesData(responses);
        String relativePath = writeDataToFile(resultDirectory, responsesData, FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType("text/plain");
        }
        return null;
    }

    private String getMqResponsesData(List<MqMockResponse> responses) {
        return responses.stream()
                .map(response ->
                        "Source queue name: " +
                        response.getSourceQueueName() +
                        "\nDestination queue name: " +
                        response.getDestinationQueueName() +
                        "\nXPath: " +
                        response.getXpath() +
                        "\nHTTP URL: " +
                        response.getHttpUrl() +
                        "\nRequest body:\n" +
                        response.getResponseBody()
                )
                .collect(Collectors.joining("\n\n"));
    }
}
