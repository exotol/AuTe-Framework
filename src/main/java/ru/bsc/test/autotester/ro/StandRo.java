package ru.bsc.test.autotester.ro;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
public class StandRo implements AbstractRo {

    private static final long serialVersionUID = 4390819697062478918L;

    private String serviceUrl;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String wireMockUrl;

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getWireMockUrl() {
        return wireMockUrl;
    }

    public void setWireMockUrl(String wireMockUrl) {
        this.wireMockUrl = wireMockUrl;
    }
}
