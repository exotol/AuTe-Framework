package ru.bsc.test.at.executor.mq;

import ru.bsc.test.at.executor.model.NameValueProperty;

import java.util.List;

public interface IMqManager {

    void setHost(String host);
    void setPort(int port);
    void setUsername(String username);
    void setPassword(String password);

    void sendTextMessage(String queueName, String message, List<NameValueProperty> mqPropertyList) throws Exception;
}
