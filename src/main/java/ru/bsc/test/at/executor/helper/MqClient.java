package ru.bsc.test.at.executor.helper;

import ru.bsc.test.at.executor.model.AmqpBroker;
import ru.bsc.test.at.executor.mq.AbstractMqManager;
import ru.bsc.test.at.executor.mq.MqManagerFactory;

import javax.jms.Message;
import java.io.Closeable;
import java.io.IOException;

public class MqClient implements Closeable {

    private final AbstractMqManager mqManager;

    public MqClient(AmqpBroker amqpBroker) throws Exception {
        mqManager = MqManagerFactory.getMqManager(
                amqpBroker.getMqService(),
                amqpBroker.getHost(),
                amqpBroker.getPort(),
                amqpBroker.getUsername(),
                amqpBroker.getPassword()
        );
    }

    public void sendMessage(String queueName, String messageBody, String testIdHeaderName, String testId) throws Exception {
        mqManager.sendTextMessage(queueName, messageBody, testIdHeaderName, testId);
    }

    public Message waitMessage(String queueName, Long timeout, String testIdHeaderName, String testId) throws Exception {
        return mqManager.waitMessage(queueName, timeout, testIdHeaderName, testId);
    }

    @Override
    public void close() throws IOException {
        mqManager.close();
    }
}
