package ru.bsc.test.at.executor.helper.client.impl.mq;

import javax.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import ru.bsc.test.at.executor.helper.client.api.Client;
import ru.bsc.test.at.executor.helper.client.api.ClientCommonResponse;
import ru.bsc.test.at.executor.helper.client.api.ClientResponse;
import ru.bsc.test.at.executor.model.AmqpBroker;
import ru.bsc.test.at.executor.mq.AbstractMqManager;
import ru.bsc.test.at.executor.mq.MqManagerFactory;

import javax.jms.Message;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class MqClient implements Client<ClientMQRequest, MqClient.ClientVoidResponse> {

    private final AbstractMqManager mqManager;

    public MqClient(AmqpBroker amqpBroker) throws Exception {
        mqManager = MqManagerFactory.getMqManager(
                amqpBroker.getMqService(),
                amqpBroker.getHost(),
                amqpBroker.getPort(),
                amqpBroker.getUsername(),
                amqpBroker.getPassword()
        );
    }

    @Override
    public ClientVoidResponse request(ClientMQRequest request) throws Exception {
        mqManager.sendTextMessage(request.getResource(), (String) request.getBody(), request.getHeaders(), request.getTestIdHeaderName(), request.getTestId());
        return new ClientVoidResponse();
    }

    public ClientCommonResponse waitMessage(String queueName, Long timeout, String testIdHeaderName, String testId) throws Exception {
        Message message = mqManager.waitMessage(queueName, timeout, testIdHeaderName, testId); if (message == null) {
            throw new Exception("No reply message");
        }

        if (message instanceof TextMessage) {
            return new ClientCommonResponse(0, ((TextMessage) message).getText(),null);
        } else {
            throw new Exception("Received message is not TextMessage instance");
        }
    }

    @Override
    public void close() throws IOException {
        try {
            mqManager.close();
        } catch (IOException e) {
            log.error("Error closing MQ connection", e);
            throw e;
        }
    }

    class ClientVoidResponse implements ClientResponse {
        @Override
        public int getStatusCode() {
            return 0;
        }

        @Override
        public String getContent() {
            return null;
        }

        @Override
        public Map<String, List<String>> getHeaders() {
            return null;
        }
    }
}