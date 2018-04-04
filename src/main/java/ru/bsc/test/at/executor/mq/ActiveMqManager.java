package ru.bsc.test.at.executor.mq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.io.IOException;

@Slf4j
class ActiveMqManager implements IMqManager {

    private final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    private String host;
    private int port;
    private Connection connection;

    public void setHost(String host) {
        this.host = host;
        connectionFactory.setBrokerURL("tcp://" + host + ":" + port);
    }

    public void setPort(int port) {
        this.port = port;
        connectionFactory.setBrokerURL("tcp://" + host + ":" + port);
    }

    public void setUsername(String username) {
        connectionFactory.setUserName(username);
    }

    public void setPassword(String password) {
        connectionFactory.setPassword(password);
    }

    @Override
    public void connect() throws Exception {
        connection = connectionFactory.createConnection();
        connection.start();
    }


    public void sendTextMessage(String queueName, String message) throws Exception {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        TextMessage textMessage = session.createTextMessage(message);
        producer.send(textMessage);

        session.close();
        connection.close();
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (JMSException e) {
            log.error("JMSException {}", e);
        }
    }
}
