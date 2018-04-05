package ru.bsc.test.at.executor.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;
import ru.bsc.test.at.executor.model.NameValueProperty;

import javax.jms.*;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
public class IbmMqManager implements IMqManager {

    private QueueConnectionFactory connectionFactory;

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
    public void sendTextMessage(String queueName, String message, List<NameValueProperty> mqPropertyList) throws Exception {
        fillConnectionFactory();

        QueueConnection connection = (QueueConnection) connectionFactory.createConnection(username, password);
        QueueSession session = (QueueSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(queueName);
        QueueSender sender = session.createSender(queue);
        Message msg = session.createTextMessage(message);

        // TODO Вынести назначение свойств сообщений в отдельный метод
        //noinspection Duplicates
        if (mqPropertyList != null) {
            mqPropertyList.forEach(pair -> {
                try {
                    if ("messageId".equals(pair.getName())) {
                        msg.setJMSMessageID(pair.getValue());
                    } else if ("correlationId".equals(pair.getName())) {
                        msg.setJMSCorrelationID(pair.getValue());
                    } else if ("replyTo".equals(pair.getName())) {
                        msg.setJMSReplyTo(session.createQueue(pair.getValue()));
                    } else if ("timestamp".equals(pair.getName())) {
                        try {
                            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy,HH:mm:ss aaa");
                            msg.setJMSTimestamp(formatter.parse(pair.getValue()).getTime());
                        } catch (ParseException e) {
                            log.error("{}", e);
                        }
                    } else {
                        msg.setStringProperty(pair.getName(), pair.getValue());
                    }
                } catch (JMSException e) {
                    log.error("Set text message property error: {}", e);
                }
            });
        }

        connection.start();

        sender.send(msg);

        sender.close();
        session.close();
        connection.close();
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
}
