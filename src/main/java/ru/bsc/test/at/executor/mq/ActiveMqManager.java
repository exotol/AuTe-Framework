package ru.bsc.test.at.executor.mq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import ru.bsc.test.at.executor.model.NameValueProperty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
class ActiveMqManager implements IMqManager {

    private final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    private String host;
    private int port;

    public void setHost(String host) {
        this.host = host;
        connectionFactory.setBrokerURL("tcp://" + host + ":" + port);
    }

    public void setPort(int port) {
        this.port = port;
        connectionFactory.setBrokerURL("tcp://" + host + ":" + port);
    }

    public void setUsername(String username) {
        connectionFactory.setUserName(username);
    }

    public void setPassword(String password) {
        connectionFactory.setPassword(password);
    }

    public void sendTextMessage(String queueName, String message, List<NameValueProperty> mqPropertyList) throws Exception {
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        TextMessage textMessage = session.createTextMessage(message);

        // TODO Вынести назначение свойств сообщений в отдельный метод
        //noinspection Duplicates
        if (mqPropertyList != null) {
            mqPropertyList.forEach(pair -> {
                try {
                    if ("messageId".equals(pair.getName())) {
                        textMessage.setJMSMessageID(pair.getValue());
                    } else if ("correlationId".equals(pair.getName())) {
                        textMessage.setJMSCorrelationID(pair.getValue());
                    } else if ("replyTo".equals(pair.getName())) {
                        textMessage.setJMSReplyTo(session.createQueue(pair.getValue()));
                    } else if ("timestamp".equals(pair.getName())) {
                        try {
                            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy,HH:mm:ss aaa");
                            textMessage.setJMSTimestamp(formatter.parse(pair.getValue()).getTime());
                        } catch (ParseException e) {
                            log.error("{}", e);
                        }
                    } else {
                        textMessage.setStringProperty(pair.getName(), pair.getValue());
                    }
                } catch (JMSException e) {
                    log.error("Set text message property error: {}", e);
                }
            });
        }
        producer.send(textMessage);

        session.close();
        connection.close();
    }
}
