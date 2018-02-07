package ru.bsc.test.at.executor.model;

import ru.bsc.test.at.executor.mq.MqService;

@SuppressWarnings("WeakerAccess")
public class AmqpBroker extends AbstractModel {

    private MqService mqService;
    private String host;
    private Integer port;
    private String username;
    private String password;

    public MqService getMqService() {
        return mqService;
    }

    public void setMqService(MqService mqService) {
        this.mqService = mqService;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
