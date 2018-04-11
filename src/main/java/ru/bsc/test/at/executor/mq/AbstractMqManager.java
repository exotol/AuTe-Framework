package ru.bsc.test.at.executor.mq;

import org.apache.commons.lang3.StringUtils;

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

    public void sendTextMessage(String queueName, String message, String testIdHeaderName, String testId) throws Exception {
        Session session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue destination = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        TextMessage newMessage = session.createTextMessage(message);
        if (StringUtils.isNotEmpty(testIdHeaderName) && StringUtils.isNotEmpty(testId)) {
            newMessage.setStringProperty(testIdHeaderName, testId);
        }

        producer.send(newMessage);

        producer.close();
        session.close();
        getConnection().start();
    }

    public Message waitMessage(String queueName, Long timeoutMs, String testIdHeaderName, String testId) throws JMSException {
        Session session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer;
        if (StringUtils.isNotEmpty(testIdHeaderName) && StringUtils.isNotEmpty(testId)) {
            consumer = session.createConsumer(session.createQueue(queueName), testIdHeaderName + "='" + testId + "'");
        } else {
            consumer = session.createConsumer(session.createQueue(queueName));
        }
        return consumer.receive(timeoutMs);
    }
}
