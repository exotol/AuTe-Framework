package ru.bsc.test.at.executor.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import ru.bsc.test.at.executor.model.NameValueProperty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
class RabbitMqManager implements IMqManager {

    private final ConnectionFactory connectionFactory = new ConnectionFactory();

    public void setHost(String host) {
        connectionFactory.setHost(host);
    }

    public void setPort(int port) {
        connectionFactory.setPort(port);
    }

    public void setUsername(String username) {
        connectionFactory.setUsername(username);
    }

    public void setPassword(String password) {
        connectionFactory.setPassword(password);
    }

    public void sendTextMessage(String queueName, String message, List<NameValueProperty> mqPropertyList) throws Exception {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        AMQP.BasicProperties.Builder propertiesBuilder = new AMQP.BasicProperties().builder()
                .contentType("")
                .messageId("");

        Map<String, Object> headers = new HashMap<>();

        if (mqPropertyList != null) {
            mqPropertyList.forEach(pair -> {
                if ("messageId".equals(pair.getName())) {
                    propertiesBuilder.messageId(pair.getValue());
                } else if ("contentType".equals(pair.getName())) {
                    propertiesBuilder.contentType(pair.getValue());
                } else if ("contentEncoding".equals(pair.getName())) {
                    propertiesBuilder.contentEncoding(pair.getValue());
                } else if ("correlationId".equals(pair.getName())) {
                    propertiesBuilder.correlationId(pair.getValue());
                } else if ("replyTo".equals(pair.getName())) {
                    propertiesBuilder.replyTo(pair.getValue());
                } else if ("timestamp".equals(pair.getName())) {
                    try {
                        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy,HH:mm:ss SSS");
                        propertiesBuilder.timestamp(formatter.parse(pair.getValue()));
                    } catch (ParseException e) {
                        log.error("{}", e);
                    }
                } else {
                    headers.put(pair.getName(), pair.getValue());
                }
            });
        }
        propertiesBuilder.headers(headers);

        channel.basicPublish("", queueName, propertiesBuilder.build(), message.getBytes());

        channel.close();
        connection.close();
    }
}
