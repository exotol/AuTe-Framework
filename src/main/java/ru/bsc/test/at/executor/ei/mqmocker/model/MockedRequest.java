package ru.bsc.test.at.executor.ei.mqmocker.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MockedRequest {
    private Date date;
    private String requestBody;
    private String sourceQueue;
    private String testId;
    private String mappingGuid;
    private String responseBody;
    private String destinationQueue;
}
