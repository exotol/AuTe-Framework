package ru.bsc.test.autotester.properties;

import ru.bsc.test.at.executor.mq.MqService;

public class AmqpBrokerProperties {
    private MqService mqService;
    private String host;
    private Integer port;
    private String password;
    private String username;
    private Integer ccsid;
    private String channel;
    private String queueManager;
    private Integer transportType;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCcsid() {
        return ccsid;
    }

    public void setCcsid(Integer ccsid) {
        this.ccsid = ccsid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getQueueManager() {
        return queueManager;
    }

    public void setQueueManager(String queueManager) {
        this.queueManager = queueManager;
    }

    public Integer getTransportType() {
        return transportType;
    }

    public void setTransportType(Integer transportType) {
        this.transportType = transportType;
    }
}
