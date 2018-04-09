package ru.bsc.test.at.executor.mq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import ru.bsc.test.at.executor.model.NameValueProperty;

import javax.jms.Connection;
import javax.jms.JMSException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
class ActiveMqManager extends AbstractMqManager {

    private final Connection connection;

    ActiveMqManager(String host, int port, String username, String password) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://" + host + ":" + port);
        connectionFactory.setUserName(username);
        connectionFactory.setPassword(password);

        connection = connectionFactory.createConnection();
        connection.start();
    }

    @Override
    Connection getConnection() {
        return connection;
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
