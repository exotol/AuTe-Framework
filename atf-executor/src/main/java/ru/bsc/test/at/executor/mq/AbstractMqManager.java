package ru.bsc.test.at.executor.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.jms.*;
import java.io.Closeable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@Slf4j
abstract public class AbstractMqManager implements Closeable {

    abstract Connection getConnection();

    public void sendTextMessage(String queueName, String message, Map<String, Object> properties, String testIdHeaderName, String testId) throws Exception {
        Session session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue destination = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        TextMessage newMessage = session.createTextMessage(message);
        if (StringUtils.isNotEmpty(testIdHeaderName) && StringUtils.isNotEmpty(testId)) {
            newMessage.setStringProperty(testIdHeaderName, testId);
        }

        if (properties != null) {
            properties.forEach((name, value) -> {
                String stringValue = value instanceof String ? (String) value : null;
                try {
                    if (StringUtils.isNotEmpty(name) && value != null) {
                        if ("messageId".equals(name)) {
                            newMessage.setJMSMessageID(stringValue);
                        } else if ("correlationId".equals(name)) {
                            newMessage.setJMSCorrelationID(stringValue);
                        } else if ("replyTo".equals(name)) {
                            newMessage.setJMSReplyTo(session.createQueue(stringValue));
                        } else if ("timestamp".equals(name)) {
                            try {
                                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy,HH:mm:ss SSS");
                                newMessage.setJMSTimestamp(formatter.parse(stringValue).getTime());
                            } catch (ParseException e) {
                                log.error("{}", e);
                            }
                        } else {
                            newMessage.setObjectProperty(name, value);
                        }
                    }
                } catch (JMSException e) {
                    log.error("Set JMS object property error: {}", e);
                }
            });
        }

        producer.send(newMessage);

        producer.close();
        session.close();
    }

    public Message waitMessage(String queueName, Long timeoutMs, String testIdHeaderName, String testId) throws JMSException {
        Session session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer;
        if (StringUtils.isNotEmpty(testIdHeaderName) && StringUtils.isNotEmpty(testId)) {
            consumer = session.createConsumer(session.createQueue(queueName), testIdHeaderName + "='" + testId + "'");
        } else {
            consumer = session.createConsumer(session.createQueue(queueName));
        }
        return consumer.receive(timeoutMs);
    }
}
