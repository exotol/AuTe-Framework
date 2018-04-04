package ru.bsc.test.at.executor.model;

import lombok.Getter;
import lombok.Setter;
import ru.bsc.test.at.executor.mq.MqService;

import java.io.Serializable;

@Getter
@Setter
public class AmqpBroker implements AbstractModel, Serializable {
    private static final long serialVersionUID = 2205108669600359668L;

    private MqService mqService;
    private String host;
    private Integer port;
    private String username;
    private String password;

    public AmqpBroker copy() {
        AmqpBroker copy = new AmqpBroker();
        copy.setMqService(getMqService());
        copy.setHost(getHost());
        copy.setPort(getPort());
        copy.setUsername(getUsername());
        copy.setPassword(getPassword());
        return copy;
    }
}
