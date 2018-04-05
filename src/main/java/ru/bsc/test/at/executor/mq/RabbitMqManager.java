package ru.bsc.test.at.executor.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

class RabbitMqManager implements IMqManager {

    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    // TODO: Сделать final
    private Connection connection;

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

    @Override
    public void connect() throws Exception {
        connection = connectionFactory.newConnection();
    }

    public void sendTextMessage(String queueName, String message) throws Exception {
        Channel channel = connection.createChannel();
        channel.basicPublish("", queueName, null, message.getBytes());

        channel.close();
    }



    @Override
    public void waitMessage(String queueName, Long timeoutMs) {

        Channel channelFrom = connection.createChannel();

        String consumerTag = channelFrom.basicConsume();
    }

    @Override
    public void close() throws IOException {
        connection.close();
    }
}
