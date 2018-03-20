package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MqMockResponseRo {
    private String code;
    private String responseBody;
    private String sourceQueueName;
    private String httpUrl;
    private String destinationQueueName;
    private String xpath;
}
