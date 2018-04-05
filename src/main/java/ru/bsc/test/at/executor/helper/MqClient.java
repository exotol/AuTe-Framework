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

    public void sendMessage(String queueName, String messageBody) throws Exception {
        mqManager.sendTextMessage(queueName, messageBody);
    }

    public void waitMessage(String queueName, Long timeout) throws Exception {
        mqManager.;
    }

    @Override
    public void close() throws IOException {
        mqManager.close();
    }
}
