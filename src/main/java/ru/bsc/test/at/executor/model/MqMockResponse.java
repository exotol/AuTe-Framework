package ru.bsc.test.at.executor.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sdoroshin on 27.07.2017.
 */
@Getter
@Setter
public class MqMockResponse implements AbstractModel {

    private String responseBody;
    private String sourceQueueName;
    private String httpUrl;
    private String destinationQueueName;
    private String XPath;

    protected MqMockResponse copy() {
        MqMockResponse response = new MqMockResponse();
        response.setResponseBody(getResponseBody());
        response.setSourceQueueName(getSourceQueueName());
        response.setHttpUrl(getHttpUrl());
        response.setDestinationQueueName(getDestinationQueueName());
        response.setXPath(getXPath());
        return response;
    }
}
