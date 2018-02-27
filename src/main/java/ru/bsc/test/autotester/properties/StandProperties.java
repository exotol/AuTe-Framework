package ru.bsc.test.autotester.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandProperties {
    private String serviceUrl;
    private String wireMockUrl;
    private DataBaseProperties dataBase;
    private AmqpBrokerProperties amqpBroker;
}
