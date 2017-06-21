package ru.bsc.test.at.executor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by sdoroshin on 10.05.2017.
 *
 */
@Entity
@Table(name = "AT_SERVICE_RESPONSE")
public class ServiceResponse implements Serializable {

    public ServiceResponse() {
    }

    public ServiceResponse(String sessionUid, String serviceName, String response, Long sort, String projectCode) {
        this.sessionUid = sessionUid;
        this.serviceName = serviceName;
        this.response = response;
        this.sort = sort;
        this.projectCode = projectCode;
    }

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_SERVICE_RESPONSE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "SESSION_UID")
    private String sessionUid;

    @Column(name = "SERVICE_NAME")
    private String serviceName;

    @Column(name = "RESPONSE")
    private String response;

    @Column(name = "SORT")
    private Long sort;

    @Column(name = "ACTUAL_REQUEST")
    private String actualRequest;

    @Column(name = "IS_CALLED")
    private Long isCalled = 0L;

    @Column(name = "PROJECT_CODE")
    private String projectCode;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSessionUid() {
        return sessionUid;
    }
    public void setSessionUid(String sessionUid) {
        this.sessionUid = sessionUid;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getResponse() {
        return response;
    }
    public void setResponse(String response) {
        this.response = response;
    }
    public Long getSort() {
        return sort;
    }
    public void setSort(Long sort) {
        this.sort = sort;
    }
    public String getActualRequest() {
        return actualRequest;
    }
    public void setActualRequest(String actualRequest) {
        this.actualRequest = actualRequest;
    }
    public Long getIsCalled() {
        return isCalled;
    }
    public void setIsCalled(Long isCalled) {
        this.isCalled = isCalled;
    }
    public String getProjectCode() {
        return projectCode;
    }
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
}
