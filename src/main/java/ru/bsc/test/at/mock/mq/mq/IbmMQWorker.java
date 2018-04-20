package ru.bsc.test.at.mock.mq.mq;

import org.apache.commons.collections.Buffer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import ru.bsc.test.at.mock.mq.http.HttpClient;
import ru.bsc.test.at.mock.mq.models.MockMessage;
import ru.bsc.test.at.mock.mq.models.MockedRequest;
import ru.bsc.velocity.transformer.VelocityTransformer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@SuppressWarnings("Duplicates")
public class IbmMQWorker extends AbstractMqWorker {

    private static final Logger logger = LoggerFactory.getLogger(IbmMQWorker.class);

    private final Buffer fifo;
    private Integer port;

    public IbmMQWorker(String queueNameFrom, String queueNameTo, List<MockMessage> mockMappingList, Buffer fifo, String brokerUrl, String username, String password, Integer port, String testIdHeaderName) {
        super(queueNameFrom, queueNameTo, mockMappingList, brokerUrl, username, password, testIdHeaderName);
        this.fifo = fifo;
        this.port = port;
    }



    @Override
    void runWorker() {
        ConnectionFactory connectionFactory;
        try {
            connectionFactory = createConnectionFactory(brokerUrl, port);
        } catch (ReflectiveOperationException e) {
            logger.error("{}", e);
            return;
        }

        try {
            // connectionFactory = ;

            Connection connection = connectionFactory.createConnection(username, password);
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageConsumer consumer = session.createConsumer(session.createQueue(queueNameFrom));

            try {
                while (!Thread.currentThread().isInterrupted()) {

                    // Wait for a message
                    logger.info("Wait messages from {}", queueNameFrom);
                    Message receivedMessage = consumer.receive();
                    logger.info("Received message, JMSMessageID: {}", receivedMessage.getJMSMessageID());

                    Enumeration names = receivedMessage.getPropertyNames();
                    List<Object> list = new LinkedList<>();
                    while (names.hasMoreElements()) {
                        Object name = names.nextElement();
                        list.add(name);
                    }

                    MockedRequest mockedRequest = new MockedRequest();
                    //noinspection unchecked
                    fifo.add(mockedRequest);
                    mockedRequest.setSourceQueue(queueNameFrom);

                    if (!(receivedMessage instanceof TextMessage)) {
                        mockedRequest.setRequestBody("<not text message>");
                        continue;
                    }
                    TextMessage message = (TextMessage)receivedMessage;
                    String stringBody = message.getText();
                    mockedRequest.setRequestBody(stringBody);
                    logger.info(" [x] Received <<< {} {}", queueNameFrom, stringBody);

                    String testId = message.getStringProperty(testIdHeaderName);
                    mockedRequest.setTestId(testId);

                    MockMessage mockMessage = findMockMessage(testId, stringBody);
                    if (mockMessage != null) {
                        mockedRequest.setMappingGuid(mockMessage.getGuid());

                        // Выполнение инструкции из моков
                        byte[] response;

                        if (StringUtils.isNotEmpty(mockMessage.getResponseBody())) {
                            response = new VelocityTransformer().transform(stringBody, null, mockMessage.getResponseBody()).getBytes();
                        } else if (StringUtils.isNotEmpty(mockMessage.getHttpUrl())) {
                            HttpClient httpClient = new HttpClient();
                            response = httpClient.sendPost(mockMessage.getHttpUrl(), message.getText(), testIdHeaderName, testId).getBytes();
                            mockedRequest.setHttpRequestUrl(mockMessage.getHttpUrl());
                        } else {
                            response = stringBody.getBytes();
                        }

                        mockedRequest.setDestinationQueue(mockMessage.getDestinationQueueName());

                        if (isNotEmpty(mockMessage.getDestinationQueueName()) && response != null) {

                            mockedRequest.setResponseBody(new String(response, "UTF-8"));

                            Queue destination = session.createQueue(mockMessage.getDestinationQueueName());
                            MessageProducer producer = session.createProducer(destination);
                            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                            TextMessage newMessage = session.createTextMessage(new String(response, "UTF-8"));
                            copyMessageProperties(message, newMessage, testId, destination);

                            // Переслать сообщение в очередь-назначение
                            producer.send(newMessage);

                            producer.close();
                            logger.info(" [x] Send >>> {} '{}'", mockMessage.getDestinationQueueName(), message.getText(), "UTF-8");
                        }
                    } else {
                        // Переслать сообщение в очередь "по-умолчанию".
                        mockedRequest.setDestinationQueue(queueNameTo);
                        if (isNotEmpty(queueNameTo)) {
                            mockedRequest.setResponseBody(stringBody);

                            Queue destination = session.createQueue(queueNameTo);
                            MessageProducer producer = session.createProducer(destination);
                            TextMessage newMessage = session.createTextMessage(message.getText());

                            copyMessageProperties(message, newMessage, testId, destination);

                            // Переслать сообщение в очередь-назначение
                            producer.send(newMessage);
                            producer.close();
                            logger.info(" [x] Send >>> {} '{}'", queueNameTo, message.getText(), "UTF-8");
                        } else {
                            logger.info(" [x] Send >>> ***black hole***");
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Caught: {}", e);
            }

            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            logger.error("Caught: {}", e);
        }
    }

    @Override
    public void stop() throws IOException, TimeoutException {
        // Do nothing
    }

    private QueueConnectionFactory createConnectionFactory(String host, Integer port) throws ReflectiveOperationException {
        QueueConnectionFactory connectionFactory;
        try {
            connectionFactory = (QueueConnectionFactory) Class.forName("com.ibm.mq.jms.MQQueueConnectionFactory").newInstance();
        } catch (ClassNotFoundException e) {
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
}
