package ru.bsc.test.at.executor.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 27.07.2017.
 */
@Getter
@Setter
public class MqMock implements AbstractModel, CodeAccessible {

    private String code;
    private String sourceQueueName;
    private List<MqMockResponse> responses = new LinkedList<>();
    private String httpUrl;
    private String xpath;

    @Deprecated
    private String responseBody;
    @Deprecated
    private String destinationQueueName;

    protected MqMock copy() {
        MqMock response = new MqMock();
        response.setSourceQueueName(getSourceQueueName());
        response.setHttpUrl(getHttpUrl());
        getResponses().forEach(r -> response.getResponses().add(r.copy()));
        response.setXpath(getXpath());

        response.setResponseBody(getResponseBody());
        response.setDestinationQueueName(getDestinationQueueName());
        return response;
    }
}
