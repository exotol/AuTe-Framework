package ru.bsc.test.at.executor.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import java.io.IOException;

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
