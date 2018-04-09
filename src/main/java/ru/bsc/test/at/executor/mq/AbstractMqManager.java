package ru.bsc.test.at.executor.mq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.Closeable;

abstract public class AbstractMqManager implements Closeable {

    abstract Connection getConnection();

    public void sendTextMessage(String queueName, String message) throws Exception {
        Session session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue destination = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        TextMessage newMessage = session.createTextMessage(message);

        producer.send(newMessage);

        producer.close();
        session.close();
        getConnection().start();
    }

    public Message waitMessage(String queueName, Long timeoutMs) throws JMSException {
        Session session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(session.createQueue(queueName));
        return consumer.receive(timeoutMs);
    }
}
