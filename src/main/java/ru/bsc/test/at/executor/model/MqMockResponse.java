package ru.bsc.test.at.executor.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Created by sdoroshin on 27.07.2017.
 */
@Getter
@Setter
public class MqMockResponse implements AbstractModel, CodeAccessible {

    private String code;
    private String responseBody;
    private String sourceQueueName;
    private String httpUrl;
    private String destinationQueueName;
    private String xpath;

    protected MqMockResponse copy() {
        MqMockResponse response = new MqMockResponse();
        response.setResponseBody(getResponseBody());
        response.setSourceQueueName(getSourceQueueName());
        response.setHttpUrl(getHttpUrl());
        response.setDestinationQueueName(getDestinationQueueName());
        response.setXpath(getXpath());
        return response;
    }
}
