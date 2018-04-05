package ru.bsc.test.at.executor.ei.wiremock.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicAuthCredentials {
    private String username;
    private String password;
}
