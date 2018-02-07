package ru.bsc.test.at.executor.model;

import ru.bsc.test.at.executor.mq.MqService;

@SuppressWarnings("WeakerAccess")
public class AmqpBroker extends AbstractModel {

    private MqService mqService;
    private String host;
    private Integer port;
    private String username;
    private String password;
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

    public AmqpBroker copy() {
        AmqpBroker copy = new AmqpBroker();
        copy.setMqService(getMqService());
        copy.setHost(getHost());
        copy.setPort(getPort());
        copy.setUsername(getUsername());
        copy.setPassword(getPassword());
        copy.setCcsid(getCcsid());
        copy.setQueueManager(getQueueManager());
        copy.setChannel(getChannel());
        copy.setTransportType(getTransportType());
        return copy;
    }
}
