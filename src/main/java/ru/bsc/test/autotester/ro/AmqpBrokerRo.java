package ru.bsc.test.autotester.ro;

public class AmqpBrokerRo extends AbstractRo {

    private static final long serialVersionUID = -6295730897412089910L;
    private Long id;
    private String mqService;
    private String host;
    private Integer port;
    private String username;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMqService() {
        return mqService;
    }

    public void setMqService(String mqService) {
        this.mqService = mqService;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
