package ru.bsc.test.at.executor.mq;

public interface IMqManager {

    void setHost(String host);
    void setPort(int port);
    void setUsername(String username);
    void setPassword(String password);
    void setCcid(int ccid);
    void setCannel(String channel);
    void setQueueManager(String queueManager);
    void setTransportType(int transportType);

    void sendTextMessage(String queueName, String message) throws Exception;
}
