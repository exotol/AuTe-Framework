package ru.bsc.test.at.mock.mq.mq;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.bsc.test.at.mock.mq.models.MockMessage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RunWith(ConcurrentTestRunner.class)
public class AbstractMqWorkerTest {


    private AbstractMqWorker testWorker;
    private final List<MockMessage> list = new LinkedList<>();

    @Before
    public void init() {

        list.add(MockMessage.builder().sourceQueueName("queue6").testId("testId").build());
        list.add(MockMessage.builder().sourceQueueName("queue5").testId("testId").build());
        list.add(MockMessage.builder().sourceQueueName("queue4").testId("testId").build());
        list.add(MockMessage.builder().sourceQueueName("queue3").testId("testId").build());
        list.add(MockMessage.builder().sourceQueueName("queue2").testId("testId").build());
        list.add(MockMessage.builder().sourceQueueName("queue").testId("testId").build());

        testWorker = new TestAbstractMqWorker("queue", null, list, null, null, null, null);
    }

    @Test()
    @ThreadCount(1000)
    public void listConcurrentAccessTest() {
        for (int i = 0; i < 10; i++) {
            MockMessage message = testWorker.findMockMessage("testId", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><note></note>");
            synchronized (list) {
                list.add(MockMessage.builder().sourceQueueName("q").testId("t").build());
            }
            Assert.assertNotNull(message);
        }
    }

    class TestAbstractMqWorker extends AbstractMqWorker {
        TestAbstractMqWorker(String queueNameFrom, String queueNameTo, List<MockMessage> mockMappingList, String brokerUrl, String username, String password, String testIdHeaderName) {
            super(queueNameFrom, queueNameTo, mockMappingList, brokerUrl, username, password, testIdHeaderName);
        }

        @Override
        void runWorker() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void stop() throws IOException, TimeoutException {
            throw new UnsupportedOperationException();
        }
    }
}