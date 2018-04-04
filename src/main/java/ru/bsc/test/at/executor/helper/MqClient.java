package ru.bsc.test.at.executor.helper;

import ru.bsc.test.at.executor.model.AmqpBroker;
import ru.bsc.test.at.executor.mq.IMqManager;
import ru.bsc.test.at.executor.mq.MqManagerFactory;

import java.io.Closeable;
import java.io.IOException;

public class MqClient implements Closeable {

    private final IMqManager mqManager;

    public MqClient(AmqpBroker amqpBroker) throws Exception {
        mqManager = MqManagerFactory.getMqManager(amqpBroker.getMqService());
        mqManager.setHost(amqpBroker.getHost());
        mqManager.setPort(amqpBroker.getPort());
        mqManager.setUsername(amqpBroker.getUsername());
        mqManager.setPassword(amqpBroker.getPassword());

        mqManager.connect();
    }

    void sendMessage(String mqName, String message) throws Exception {
        mqManager.sendTextMessage(mqName, message);
    }

    @Override
    public void close() throws IOException {
        mqManager.close();
    }
}
