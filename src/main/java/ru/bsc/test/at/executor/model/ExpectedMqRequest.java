package ru.bsc.test.at.executor.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpectedMqRequest implements CodeAccessible{
    private String code;
    private String sourceQueue;
    private String requestBody;
    private String ignoredTags;

    public ExpectedMqRequest copy() {
        ExpectedMqRequest expectedMqRequest = new ExpectedMqRequest();
        expectedMqRequest.setCode(getCode());
        expectedMqRequest.setSourceQueue(getSourceQueue());
        expectedMqRequest.setRequestBody(getRequestBody());
        expectedMqRequest.setIgnoredTags(getIgnoredTags());
        return expectedMqRequest;
    }
}
