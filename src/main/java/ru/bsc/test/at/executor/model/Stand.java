package ru.bsc.test.at.executor.model;

/**
 * Created by sdoroshin on 12.07.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
public class Stand extends AbstractModel implements Cloneable {

    private Project project;
    private String serviceUrl;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String wireMockUrl;

    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }
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

    @Override
    public Stand clone() throws CloneNotSupportedException {
        super.clone();
        Stand cloned = new Stand();
        cloned.setProject(getProject());
        cloned.setServiceUrl(getServiceUrl());
        cloned.setDbUrl(getDbUrl());
        cloned.setDbUser(getDbUser());
        cloned.setDbPassword(getDbPassword());
        cloned.setWireMockUrl(getWireMockUrl());

        return cloned;
    }
}
