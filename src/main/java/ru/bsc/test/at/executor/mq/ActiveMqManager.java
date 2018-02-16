package ru.bsc.test.at.executor.mq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

class ActiveMqManager implements IMqManager {

    private final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    private String host;
    private int port;

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

    public void sendTextMessage(String queueName, String message) throws Exception {
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        TextMessage textMessage = session.createTextMessage(message);
        producer.send(textMessage);

        session.close();
        connection.close();
    }
}
