package ru.bsc.test.at.executor.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import javax.jms.*;
import java.io.IOException;
import java.lang.reflect.Method;

@Slf4j
public class IbmMqManager extends AbstractMqManager {

    private QueueConnection connection;

    IbmMqManager(String host, int port, String username, String password) throws JMSException, ReflectiveOperationException {
        ConnectionFactory connectionFactory = fillConnectionFactory(host, port);
        connection = (QueueConnection) connectionFactory.createConnection(username, password);
        connection.start();
    }

    @Override
    Connection getConnection() {
        return connection;
    }

    private ConnectionFactory fillConnectionFactory(String host, int port) throws ReflectiveOperationException {
        ConnectionFactory connectionFactory;
        try {
            connectionFactory = (QueueConnectionFactory) Class.forName("com.ibm.mq.jms.MQQueueConnectionFactory").newInstance();
        } catch (ClassNotFoundException e) {
            log.error("Error creating connection factory", e);
            throw new ClassNotFoundException(e.getMessage() + ": set class path for library for Ibm Mq provider", e);
        }

        invoke(connectionFactory, "setHostName", host);
        invoke(connectionFactory, "setPort", port);
        invoke(connectionFactory, "setTransportType", 1);

        return connectionFactory;
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
