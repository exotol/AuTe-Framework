package ru.bsc.test.at.executor.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by sdoroshin on 12.07.2017.
 */
@Data
public class Stand implements AbstractModel, Serializable {
    private static final long serialVersionUID = 1616628096472011795L;

    private String serviceUrl;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String wireMockUrl;

    public Stand copy() {
        Stand stand = new Stand();
        stand.setServiceUrl(getServiceUrl());
        stand.setDbUrl(getDbUrl());
        stand.setDbUser(getDbUser());
        stand.setDbPassword(getDbPassword());
        stand.setWireMockUrl(getWireMockUrl());
        return stand;
    }
}
