package ru.bsc.test.at.executor.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.jms.admin.RMQConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import javax.jms.Connection;
import javax.jms.JMSException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Slf4j
class RabbitMqManager extends AbstractMqManager {

    private final Connection connection;
    private final com.rabbitmq.client.Connection senderConnection;

    RabbitMqManager(String host, int port, String username, String password) throws JMSException {
        RMQConnectionFactory cf = new RMQConnectionFactory();
        cf.setHost(host);
        cf.setPort(port);
        cf.setUsername(username);
        cf.setPassword(password);
        connection = cf.createConnection();

        try {
            // TODO Это костыль для отправки сообщений в RabbitMQ.
            // Сообщения не отправляются в очередь через стандартные интерфейсы JMS, если у очереди указан параметр x-dead-letter-exchange
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(host);
            connectionFactory.setPort(port);
            connectionFactory.setUsername(username);
            connectionFactory.setPassword(password);
            senderConnection = connectionFactory.newConnection();
        } catch (Exception e) {
            log.error("{}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendTextMessage(String queueName, String message, Map<String, Object> properties, String testIdHeaderName, String testId) throws Exception {
        Channel channel = senderConnection.createChannel();

        AMQP.BasicProperties.Builder propertiesBuilder = new AMQP.BasicProperties().builder();

        Map<String, Object> headers = new HashMap<>();

        if (properties != null) {
            properties.forEach((name, value) -> {
                String stringValue = value instanceof String ? (String) value : null;
                if ("messageId".equals(name)) {
                    propertiesBuilder.messageId(stringValue);
                } else if ("contentType".equals(name)) {
                    propertiesBuilder.contentType(stringValue);
                } else if ("contentEncoding".equals(name)) {
                    propertiesBuilder.contentEncoding(stringValue);
                } else if ("correlationId".equals(name)) {
                    propertiesBuilder.correlationId(stringValue);
                } else if ("replyTo".equals(name)) {
                    propertiesBuilder.replyTo(stringValue);
                } else if ("timestamp".equals(name)) {
                    try {
                        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy,HH:mm:ss SSS");
                        propertiesBuilder.timestamp(formatter.parse(stringValue));
                    } catch (ParseException e) {
                        log.error("{}", e);
                    }
                } else {
                    headers.put(name, value);
                }
            });
        }

        channel.basicPublish("", queueName, propertiesBuilder.headers(headers).build(), message.getBytes());
        channel.close();
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
