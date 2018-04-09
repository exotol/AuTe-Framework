package ru.bsc.test.at.executor.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.jms.admin.RMQConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;
import ru.bsc.test.at.executor.model.NameValueProperty;

import javax.jms.Connection;
import javax.jms.JMSException;
import java.io.IOException;

@Slf4j
class RabbitMqManager extends AbstractMqManager {

    private final Connection connection;

    RabbitMqManager(String host, int port, String username, String password) throws JMSException {
        RMQConnectionFactory cf = new RMQConnectionFactory();
        cf.setHost(host);
        cf.setPort(port);
        cf.setUsername(username);
        cf.setPassword(password);
        connection = cf.createConnection();
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
            log.error("{}", e);
        }
    }
}
