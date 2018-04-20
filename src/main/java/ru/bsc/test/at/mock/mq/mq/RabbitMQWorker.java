package ru.bsc.test.at.mock.mq.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.commons.collections.Buffer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bsc.test.at.mock.mq.http.HttpClient;
import ru.bsc.test.at.mock.mq.models.MockMessage;
import ru.bsc.test.at.mock.mq.models.MockedRequest;
import ru.bsc.velocity.transformer.VelocityTransformer;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class RabbitMQWorker extends AbstractMqWorker {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQWorker.class);

    private final Buffer fifo;
    private Channel channelFrom;
    private Channel channelTo;
    private com.rabbitmq.client.Connection connection;
    private int port;

    public RabbitMQWorker(String queueNameFrom, String queueNameTo, List<MockMessage> mockMappingList, Buffer fifo, String brokerUrl, String username, String password, int port, String testIdHeaderName) {
        super(queueNameFrom, queueNameTo, mockMappingList, brokerUrl, username, password, testIdHeaderName);
        this.fifo = fifo;
        this.port = port;
    }

    @Override
    void runWorker() {
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(brokerUrl);
            connectionFactory.setPort(port);
            connectionFactory.setUsername(username);
            connectionFactory.setPassword(password);

            connection = connectionFactory.newConnection();
            channelFrom = connection.createChannel();
            channelTo = connection.createChannel();

            try {
                // Wait for a message
                waitMessage();
            } catch (Exception e) {
                logger.error("Caught: {}", e);
            }
        } catch (Exception e) {
            logger.error("Caught: {}", e);
        }
    }

    @Override
    public void stop() throws IOException, TimeoutException {
        if (channelFrom.isOpen()) {
            channelFrom.close();
        }
        if (channelTo.isOpen()) {
            channelTo.close();
        }
        connection.close();
    }

    private void waitMessage() throws JMSException, IOException {
        channelFrom.basicConsume(queueNameFrom, true, new DefaultConsumer(channelFrom) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String stringBody = new String(body, "UTF-8");
                logger.info(" [x] Received '{}'", stringBody);

                MockedRequest mockedRequest = new MockedRequest();
                //noinspection unchecked
                fifo.add(mockedRequest);
                mockedRequest.setRequestBody(stringBody);
                mockedRequest.setSourceQueue(queueNameFrom);

                // Найти соответствие по названию очереди и testIdProperty
                String testId = properties.getHeaders().get(testIdHeaderName) == null ? null : properties.getHeaders().get(testIdHeaderName).toString();
                mockedRequest.setTestId(testId);

                MockMessage mockMessage = findMockMessage(testId, stringBody);
                if (mockMessage != null) {
                    mockedRequest.setMappingGuid(mockMessage.getGuid());
                    byte[] response;

                    if (StringUtils.isNotEmpty(mockMessage.getResponseBody())) {
                        response = new VelocityTransformer().transform(stringBody, null, mockMessage.getResponseBody()).getBytes();
                    } else if (StringUtils.isNotEmpty(mockMessage.getHttpUrl())) {
                        HttpClient httpClient = new HttpClient();
                        response = httpClient.sendPost(mockMessage.getHttpUrl(), new String(body, "UTF-8"), testIdHeaderName, testId).getBytes();
                        mockedRequest.setHttpRequestUrl(mockMessage.getHttpUrl());
                    } else {
                        response = body;
                    }

                    mockedRequest.setDestinationQueue(mockMessage.getDestinationQueueName());

                    //noinspection ConstantConditions
                    if (isNotEmpty(mockMessage.getDestinationQueueName()) && response != null) {
                        mockedRequest.setResponseBody(new String(response, "UTF-8"));

                        Channel channel = connection.createChannel();
                        channel.basicPublish("", mockMessage.getDestinationQueueName(), properties, response);
                        logger.info(" [x] Send >>> {} '{}'", mockMessage.getDestinationQueueName(), new String(response, "UTF-8"));
                        try {
                            channel.close();
                        } catch (TimeoutException e) {
                            logger.error("Caught: {}", e);
                        }
                    }
                } else {
                    // Переслать сообщение в очередь "по-умолчанию".
                    mockedRequest.setDestinationQueue(queueNameTo);
                    if (isNotEmpty(queueNameTo)) {
                        mockedRequest.setResponseBody(stringBody);
                        channelTo.basicPublish("", queueNameTo, properties, body);
                        logger.info(" [x] Send >>> {} '{}'", queueNameTo, new String(body, "UTF-8"));
                    } else {
                        logger.info(" [x] Send >>> ***black hole***");
                    }
                }
            }
        });
    }
}
