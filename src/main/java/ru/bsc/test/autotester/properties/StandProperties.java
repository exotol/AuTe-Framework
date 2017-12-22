package ru.bsc.test.autotester.properties;

public class StandProperties {
    private String serviceUrl;
    private String wireMockUrl;
    private DataBaseProperties dataBase;
    private AmqpBrokerProperties amqpBroker;

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getWireMockUrl() {
        return wireMockUrl;
    }

    public void setWireMockUrl(String wireMockUrl) {
        this.wireMockUrl = wireMockUrl;
    }

    public DataBaseProperties getDataBase() {
        return dataBase;
    }

    public void setDataBase(DataBaseProperties dataBase) {
        this.dataBase = dataBase;
    }

    public AmqpBrokerProperties getAmqpBroker() {
        return amqpBroker;
    }

    public void setAmqpBroker(AmqpBrokerProperties amqpBroker) {
        this.amqpBroker = amqpBroker;
    }
}
