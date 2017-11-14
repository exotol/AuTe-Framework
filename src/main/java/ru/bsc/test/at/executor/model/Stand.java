package ru.bsc.test.at.executor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by sdoroshin on 12.07.2017.
 *
 */
@SuppressWarnings("WeakerAccess")
@Entity
@Table(name = "AT_STAND")
public class Stand {

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_STAND", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    @JsonBackReference
    private Project project;

    @Column(name = "SERVICE_URL", length = 400)
    private String serviceUrl;

    @Column(name = "DB_URL")
    private String dbUrl;
    @Column(name = "DB_USER")
    private String dbUser;
    @Column(name = "DB_PASSWORD")
    private String dbPassword;
    private String wireMockUrl;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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

    public Stand copy() {
        Stand stand = new Stand();
        stand.setProject(getProject());
        stand.setServiceUrl(getServiceUrl());
        stand.setDbUrl(getDbUrl());
        stand.setDbUser(getDbUser());
        stand.setDbPassword(getDbPassword());
        stand.setWireMockUrl(getWireMockUrl());
        return stand;
    }
}
