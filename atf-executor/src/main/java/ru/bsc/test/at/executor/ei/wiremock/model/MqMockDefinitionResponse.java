package ru.bsc.test.at.executor.ei.wiremock.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by smakarov
 * 22.05.2018 11:19
 */
@Getter
@Setter
@AllArgsConstructor
public class MqMockDefinitionResponse {
    private String responseBody;
    private String destinationQueueName;
}
