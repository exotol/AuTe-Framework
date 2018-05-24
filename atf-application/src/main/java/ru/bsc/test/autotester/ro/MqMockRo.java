package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MqMockRo {
    private String code;
    private String sourceQueueName;
    private String httpUrl;
    private String xpath;
    private List<MqMockResponseRo> responses;

    @Deprecated
    private String responseBody;
    @Deprecated
    private String destinationQueueName;
}
