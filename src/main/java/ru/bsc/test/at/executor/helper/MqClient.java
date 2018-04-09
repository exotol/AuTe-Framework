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

    public void sendMessage(String queueName, String messageBody) throws Exception {
        mqManager.sendTextMessage(queueName, messageBody);
    }

    public Message waitMessage(String queueName, Long timeout) throws Exception {
        return mqManager.waitMessage(queueName, timeout);
    }

    @Override
    public void close() throws IOException {
        mqManager.close();
    }
}
