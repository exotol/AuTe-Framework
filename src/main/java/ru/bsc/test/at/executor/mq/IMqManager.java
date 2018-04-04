package ru.bsc.test.at.executor.mq;

import java.io.Closeable;

public interface IMqManager extends Closeable {

    void setHost(String host);
    void setPort(int port);
    void setUsername(String username);
    void setPassword(String password);

    void connect() throws Exception;

    void sendTextMessage(String queueName, String message) throws Exception;
}
