package ru.bsc.test.at.executor.mq;


import javax.jms.Session;
import javax.jms.TextMessage;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnection;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQQueueSender;
import com.ibm.mq.jms.MQQueueSession;


public class IbmMqManager implements IMqManager {


    //    public static final String JNDI_PROVIDER = "file:/C:/tmp/jms/";
    //    public static final String CONNECTION_FACTORY = "com.sun.jndi.fscontext.RefFSContextFactory";
    //    public static final String JNDI_ADDRESS = "connectionfactory";

    private MQConnectionFactory connectionFactory = new MQQueueConnectionFactory();

    private String username;
    private String password;
    private String host;
    private int port;
    private int ccid;
    private String channel;
    private String queueManager;
    private int transportType;


    //    public IbmMqManager() throws Exception {
    //        try {
    //                        java.util.Hashtable environment = new java.util.Hashtable();
    //                        environment.put(Context.PROVIDER_URL, JNDI_PROVIDER);
    //                        environment.put(Context.INITIAL_CONTEXT_FACTORY, CONNECTION_FACTORY);
    //                        Context ctx = new InitialDirContext(environment);
    //                        connectionFactory = (MQConnectionFactory) ctx.lookup(JNDI_ADDRESS);
    //
    //        } catch(Exception ex) {
    //            throw new RuntimeException(ex);
    //        }
    //
    //    }

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
    public void setCcid(int ccid) {
        this.ccid = ccid;
    }

    @Override
    public void setCannel(String channel) {
        this.channel = channel;
    }

    @Override
    public void setQueueManager(String queueManager) {
        this.queueManager = queueManager;
    }

    @Override
    public void setTransportType(int transportType) {
        this.transportType = transportType;
    }

    @Override
    public void sendTextMessage(String queueName, String message) throws Exception {

        connectionFactory.setHostName(host);
        connectionFactory.setPort(port);
        connectionFactory.setCCSID(ccid);
        connectionFactory.setChannel(channel);
        connectionFactory.setQueueManager(queueManager);
        connectionFactory.setTransportType(transportType);  // WMQConstants

        MQQueueConnection connection = (MQQueueConnection) connectionFactory.createConnection(username, password);
        MQQueueSession session = (MQQueueSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MQQueue queue = (MQQueue) session.createQueue(queueName);
        MQQueueSender sender = (MQQueueSender) session.createSender(queue);
        TextMessage msg = session.createTextMessage(message);

        connection.start();
        sender.send(msg);

        sender.close();
        session.close();
        connection.close();

    }


}
