package ru.bsc.test.autotester.properties;

import lombok.Getter;
import lombok.Setter;
import ru.bsc.test.at.executor.mq.MqService;

@Getter
@Setter
@SuppressWarnings("WeakerAccess")
public class AmqpBrokerProperties {
    private MqService mqService;
    private String host;
    private Integer port;
    private String password;
    private String username;
}
