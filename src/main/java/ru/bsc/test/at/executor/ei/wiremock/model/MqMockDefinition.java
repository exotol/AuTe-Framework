package ru.bsc.test.at.executor.ei.wiremock.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MqMockDefinition {
    private String guid;

    private String sourceQueueName;
    private String testId;

    private List<MqMockDefinitionResponse> responses = new ArrayList<>();
    private String httpUrl;
    private String xpath;
}
