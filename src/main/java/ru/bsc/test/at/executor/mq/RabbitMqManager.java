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
        //getting a connection
        Connection connection = connectionFactory.newConnection();

        //creating a channel
        Channel channel = connection.createChannel();

        //declaring a queue for this channel. If queue does not exist,
        //it will be created on the server.
        channel.queueDeclare(queueName, false, false, false, null);
        channel.basicPublish("",queueName, null, message.getBytes());

        channel.close();
        connection.close();
    }
}
