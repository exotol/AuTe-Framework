package ru.bsc.test.at.executor.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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

    public void sendTextMessage(String queueName, String message) throws Exception {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicPublish("", queueName, null, message.getBytes());

        channel.close();
        connection.close();
    }
}
