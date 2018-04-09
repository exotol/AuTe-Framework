package ru.bsc.test.at.executor.mq;

import javax.jms.JMSException;

public final class MqManagerFactory {

    private MqManagerFactory() { }

    public static AbstractMqManager getMqManager(MqService mqService, String host, Integer port, String username, String password) throws JMSException, ReflectiveOperationException {
        if (MqService.ACTIVE_MQ.equals(mqService)) {
            return new ActiveMqManager(host, port, username, password);
        } else if (MqService.RABBIT_MQ.equals(mqService)) {
            return new RabbitMqManager(host, port, username, password);
        } else if(MqService.IBM_MQ.equals(mqService)) {
            return new IbmMqManager(host, port, username, password);
        } else {
            throw new UnsupportedOperationException("MqService " + mqService + " is not supported");
        }
    }
}
