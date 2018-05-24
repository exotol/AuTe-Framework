package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sdoroshin on 14.09.2017.
 */
@Getter
@Setter
public class StandRo implements AbstractRo {
    private static final long serialVersionUID = 4390819697062478918L;

    private String serviceUrl;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String wireMockUrl;
}
