package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmqpBrokerRo implements AbstractRo {
    private static final long serialVersionUID = -6295730897412089910L;

    private String mqService;
    private String host;
    private Integer port;
    private String username;
    private String password;
}
