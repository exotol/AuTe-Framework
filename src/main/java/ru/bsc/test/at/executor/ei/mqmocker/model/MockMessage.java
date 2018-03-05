package ru.bsc.test.at.executor.ei.mqmocker.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MockMessage {
    private String guid;

    private String sourceQueueName;
    private String testId;

    private String responseBody;
    private String httpUrl;
    private String destinationQueueName;
}
