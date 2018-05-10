package ru.bsc.test.at.mock.mq.components;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.bsc.test.at.mock.mq.models.MockMessage;
import ru.bsc.test.at.mock.mq.models.PropertiesYaml;
import ru.bsc.test.at.mock.mq.mq.AbstractMqWorker;
import ru.bsc.test.at.mock.mq.mq.ActiveMQWorker;
import ru.bsc.test.at.mock.mq.mq.IbmMQWorker;
import ru.bsc.test.at.mock.mq.mq.RabbitMQWorker;
import ru.bsc.test.at.mock.mq.yaml.YamlUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

@Component
public class MqRunnerComponent {

    @Value("${mq.manager}")
    private String mqManager;

    @Value("${mq.host}")
    private String mqHost;

    @Value("${mq.port}")
    private Integer mqPort;

    @Value("${mq.username}")
    private String mqUsername;

    @Value("${mq.password}")
    private String mqPassword;

    @Value("${mq.default.destination.queue.name}")
    private String defaultDestinationQueueName;

    @Value("${properties.yaml.file}")
    private String propertiesYamlFile;

    @Value("${test.id.header.name:testIdHeader}")
    private String testIdHeaderName;

    @Value("${mq.requestBufferSize:1000}")
    private int requestBufferSize;

    private final List<MockMessage> mockMappingList = new LinkedList<>();
    private Buffer fifo;
    private Map<String, AbstractMqWorker> queueListenerMap = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        fifo = BufferUtils.synchronizedBuffer(new CircularFifoBuffer(requestBufferSize));
    }

    private static void startListener(AbstractMqWorker mqWorker) {
        Thread brokerThread = new Thread(mqWorker);
        brokerThread.start();
    }

    String addMapping(MockMessage mockMessage) {
        synchronized (mockMappingList) {
            if (StringUtils.isEmpty(mockMessage.getSourceQueueName())) {
                return null;
            }
            mockMessage.setGuid(UUID.randomUUID().toString());
            mockMappingList.add(mockMessage);

            if (!queueListenerMap.containsKey(mockMessage.getSourceQueueName())) {
                AbstractMqWorker newMqListener;

                // TODO: Переделать с использованием Enum
                if ("RABBIT_MQ".equals(mqManager)) {
                    newMqListener = new RabbitMQWorker(
                            mockMessage.getSourceQueueName(),
                            defaultDestinationQueueName,
                            mockMappingList,
                            fifo,
                            mqHost,
                            mqUsername,
                            mqPassword,
                            mqPort,
                            testIdHeaderName);
                } else if ("ACTIVE_MQ".equals(mqManager)) {
                    newMqListener = new ActiveMQWorker(
                            mockMessage.getSourceQueueName(),
                            defaultDestinationQueueName,
                            mockMappingList,
                            fifo,
                            mqHost,
                            mqUsername,
                            mqPassword,
                            testIdHeaderName);
                } else {
                    newMqListener = new IbmMQWorker(
                            mockMessage.getSourceQueueName(),
                            defaultDestinationQueueName,
                            mockMappingList,
                            fifo,
                            mqHost,
                            mqUsername,
                            mqPassword,
                            mqPort,
                            testIdHeaderName);
                }

                queueListenerMap.put(mockMessage.getSourceQueueName(), newMqListener);
                startListener(newMqListener);
            }

            return mockMessage.getGuid();
        }
    }

    void deleteMapping(String mappingGuid) throws IOException, TimeoutException {
        synchronized (mockMappingList) {
            MockMessage mapping = mockMappingList
                    .stream()
                    .filter(mockMessage -> Objects.equals(mockMessage.getGuid(), mappingGuid)).findAny()
                    .orElse(null);
            mockMappingList.remove(mapping);

            if (mapping != null) {
                long count = mockMappingList.stream().filter(mockMessage -> Objects.equals(mockMessage.getSourceQueueName(), mapping.getSourceQueueName())).count();
                if (count == 0) {
                    AbstractMqWorker mqWorker = queueListenerMap.get(mapping.getSourceQueueName());
                    if (mqWorker != null) {
                        mqWorker.stop();
                        queueListenerMap.remove(mapping.getSourceQueueName());
                    }
                }
            }
        }
    }

    Buffer getFifo() {
        return fifo;
    }

    List<MockMessage> getMockMappingList() {
        synchronized (mockMappingList) {
            return mockMappingList;
        }
    }

    @PostConstruct
    public void initMappings() throws IOException {
        if (!StringUtils.isEmpty(propertiesYamlFile)) {
            PropertiesYaml properties = YamlUtils.loadAs(new File(propertiesYamlFile), PropertiesYaml.class);
            if (properties != null && properties.getMockMessageList() != null) {
                properties.getMockMessageList().forEach(this::addMapping);
            }
        }
    }
}
