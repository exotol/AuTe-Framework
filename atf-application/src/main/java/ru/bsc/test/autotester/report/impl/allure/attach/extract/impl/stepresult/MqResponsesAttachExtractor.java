package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.stepresult;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.MqMock;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.AbstractAttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by smakarov
 * 23.03.2018 14:17
 */
@Component
public class MqResponsesAttachExtractor extends AbstractAttachExtractor<StepResult> {

    private static final String FILE_NAME = "MQ responses";

    @Override
    public List<Attachment> extract(File resultDirectory, StepResult result) {
        List<MqMock> responses = result.getStep().getMqMockResponseList();
        if (CollectionUtils.isEmpty(responses)) {
            return null;
        }

        String responsesData = getMqResponsesData(responses);
        String relativePath = writeDataToFile(resultDirectory, responsesData, FILE_NAME);
        if (relativePath != null) {
            return Collections.singletonList(new Attachment().withTitle(FILE_NAME)
                    .withSource(relativePath)
                    .withType(TEXT_PLAIN));
        }
        return null;
    }

    private String getMqResponsesData(List<MqMock> mocks) {
        return mocks.stream().map(this::mockToString).collect(Collectors.joining("\n\n"));
    }

    private String mockToString(MqMock mock) {
        return "Source queue name: " +
               mock.getSourceQueueName() +
               "\nXPath: " +
               mock.getXpath() +
               "\nHTTP URL: " +
               mock.getHttpUrl() +
               "\nMQ mock responses:" +
               mock.getResponses()
                       .stream()
                       .map(response -> "\nResponse body:\n" +
                                        response.getResponseBody() +
                                        "\nDestination queue name: " +
                                        response.getDestinationQueueName())
                       .collect(Collectors.joining());
    }
}
