package ru.bsc.test.at.executor.mq;

public final class MqManagerFactory {

    private MqManagerFactory() { }

    public static IMqManager getMqManager(MqService mqService) {
        if (MqService.ACTIVE_MQ.equals(mqService)) {
            return new ActiveMqManager();
        } else if (MqService.RABBIT_MQ.equals(mqService)) {
            return new RabbitMqManager();
        } else if(MqService.IBM_MQ.equals(mqService)) {
            return new IbmMqManager();
        } else {
            throw new UnsupportedOperationException("MqService " + mqService + " is not supported");
        }
    }
}
