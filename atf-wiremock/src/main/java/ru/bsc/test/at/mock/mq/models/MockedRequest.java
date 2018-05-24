package ru.bsc.test.at.mock.mq.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MockedRequest {
    private Date date = new Date();
    private String requestBody;
    private String sourceQueue;
    private String testId;
    private String mappingGuid;
    private String responseBody;
    private String destinationQueue;
    private String httpRequestUrl;
}
