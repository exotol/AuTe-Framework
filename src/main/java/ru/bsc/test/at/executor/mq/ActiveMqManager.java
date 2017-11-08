package ru.bsc.test.at.executor.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

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

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue(queueName);

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // Create a messages
        TextMessage textMessage = session.createTextMessage(message);

        // Tell the producer to send the message
        System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
        producer.send(textMessage);

        // Clean up
        session.close();
        connection.close();
    }
}
