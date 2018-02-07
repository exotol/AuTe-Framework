package ru.bsc.test.at.executor.mq;

public interface IMqManager {

    void setHost(String host);
    void setPort(int port);
    void setUsername(String username);
    void setPassword(String password);

    void sendTextMessage(String queueName, String message) throws Exception;
}
