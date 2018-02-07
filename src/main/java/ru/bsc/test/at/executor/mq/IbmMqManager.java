package ru.bsc.test.at.executor.mq;


import com.ibm.mq.jms.*;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.Session;
import javax.jms.TextMessage;


public class IbmMqManager implements IMqManager {

    private MQConnectionFactory connectionFactory = new MQQueueConnectionFactory();

    private String username;
    private String password;
    private String host;
    private int port;

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void sendTextMessage(String queueName, String message) throws Exception {

        connectionFactory.setHostName(host);
        connectionFactory.setPort(port);
        connectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);

        MQQueueConnection connection = (MQQueueConnection) connectionFactory.createConnection(username, password);
        MQQueueSession session = (MQQueueSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MQQueue queue = (MQQueue) session.createQueue(queueName);
        MQQueueSender sender = (MQQueueSender) session.createSender(queue);
        TextMessage msg = session.createTextMessage(message);

        connection.start();
        sender.send(msg);

        sender.close();
        session.close();
        connection.close();

    }


}
