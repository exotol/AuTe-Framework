package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by smakarov
 * 21.05.2018 15:39
 */
@Getter
@Setter
public class MqMockResponseRo {
    private String responseBody;
    private String destinationQueueName;
    private String responseFile;
}
