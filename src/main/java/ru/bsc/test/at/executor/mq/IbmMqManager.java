package ru.bsc.test.at.executor.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import javax.jms.*;
import java.io.IOException;
import java.lang.reflect.Method;

@Slf4j
public class IbmMqManager implements IMqManager {

    private QueueConnectionFactory connectionFactory;

    private QueueConnection connection;

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
    public void connect() throws Exception {
        connection = (QueueConnection) connectionFactory.createConnection(username, password);


        connection.start();
    }

    @Override
    public void sendTextMessage(String queueName, String message) throws Exception {
        QueueSession session = (QueueSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(queueName);
        QueueSender sender = session.createSender(queue);
        Message msg = session.createTextMessage(message);

        fillConnectionFactory();

        sender.send(msg);

        sender.close();
        session.close();
    }

    private void fillConnectionFactory() throws ReflectiveOperationException {
        try {
            connectionFactory = (QueueConnectionFactory) Class.forName("com.ibm.mq.jms.MQQueueConnectionFactory").newInstance();
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException(e.getMessage() + ": set class path for library for Ibm Mq provider", e);
        }

        invoke(connectionFactory, "setHostName", host);
        invoke(connectionFactory, "setPort", port);
        invoke(connectionFactory, "setTransportType", 1);
    }

    private void invoke(Object obj, String methodName, Object val) throws ReflectiveOperationException {
        Method method;
        if (val instanceof Integer) {
            method = ReflectionUtils.findMethod(obj.getClass(), methodName, int.class);
        } else {
            method = ReflectionUtils.findMethod(obj.getClass(), methodName, val.getClass());
        }

        method.invoke(obj, val);
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
