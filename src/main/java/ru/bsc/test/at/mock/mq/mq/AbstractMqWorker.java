package ru.bsc.test.at.mock.mq.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.bsc.test.at.mock.mq.models.MockMessage;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

abstract public class AbstractMqWorker implements Runnable, ExceptionListener {

    private static final Logger logger = LoggerFactory.getLogger(AbstractMqWorker.class);

    final String queueNameFrom;
    final String queueNameTo;
    private List<MockMessage> mockMappingList;

    final String brokerUrl;
    final String username;
    final String password;

    String testIdHeaderName;

    AbstractMqWorker(String queueNameFrom, String queueNameTo, List<MockMessage> mockMappingList, String brokerUrl, String username, String password, String testIdHeaderName) {
        this.queueNameFrom = queueNameFrom;
        this.queueNameTo = queueNameTo;
        this.mockMappingList = mockMappingList;
        this.brokerUrl = brokerUrl;
        this.username = username;
        this.password = password;
        this.testIdHeaderName = testIdHeaderName;
    }

    public void run() {
        runWorker();
    }

    abstract void runWorker();

    public abstract void stop() throws IOException, TimeoutException;

    public synchronized void onException(JMSException ex) {
        logger.error("{}", ex);
    }

    private Document parseXmlDocument(String stringBody) throws IOException {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db.parse(new InputSource(new StringReader(stringBody)));
        } catch (ParserConfigurationException | SAXException e) {
            logger.warn("Cannot parse XML document.", e);
            return null;
        }
    }

    MockMessage findMockMessage(String testId, String stringBody) throws IOException {
        final Document document = parseXmlDocument(stringBody);

        Predicate<MockMessage> documentXpathFilterPredicate = mockMessage1 -> {
            if (mockMessage1.getXpath() == null) {
                return true;
            }
            try {
                Object node = XPathFactory.newInstance().newXPath().evaluate(mockMessage1.getXpath(), document, XPathConstants.NODE);
                return node != null;
            } catch (XPathExpressionException e) {
                return false;
            }
        };

        return mockMappingList
                .stream()
                .filter(mockMessage1 -> Objects.equals(queueNameFrom, mockMessage1.getSourceQueueName()))
                .filter(mockMessage1 -> Objects.equals(testId, mockMessage1.getTestId()))
                .filter(documentXpathFilterPredicate)
                .findAny()
                .orElse(mockMappingList
                        .stream()
                        .filter(mockMessage1 -> Objects.equals(queueNameFrom, mockMessage1.getSourceQueueName()))
                        .filter(mockMessage1 -> mockMessage1.getTestId() == null)
                        .filter(documentXpathFilterPredicate)
                        .findAny()
                        .orElse(null)
                );
    }

    void copyMessageProperties(TextMessage message, TextMessage newMessage, String testId, Queue destination) throws JMSException {
        newMessage.setJMSCorrelationID(message.getJMSMessageID());

        newMessage.setStringProperty(testIdHeaderName, testId);
        newMessage.setJMSDestination(destination);
    }
}
