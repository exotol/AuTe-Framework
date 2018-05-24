package ru.bsc.test.at.executor.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by smakarov
 * 21.05.2018 15:34
 */
@Getter
@Setter
public class MqMockResponse {
    private String responseBody;
    private String destinationQueueName;
    private String responseFile;

    public MqMockResponse copy() {
        MqMockResponse copy = new MqMockResponse();
        copy.setDestinationQueueName(getDestinationQueueName());
        copy.setResponseBody(getResponseBody());
        return copy;
    }
}
