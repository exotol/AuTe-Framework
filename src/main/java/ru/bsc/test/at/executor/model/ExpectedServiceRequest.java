package ru.bsc.test.at.executor.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by sdoroshin on 10.05.2017.
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = "code")
public class ExpectedServiceRequest implements Serializable, AbstractModel {
    private static final long serialVersionUID = 2437656620029851514L;

    private String code;
    private String serviceName;
    private String expectedServiceRequest;
    private String expectedServiceRequestFile;
    private String ignoredTags;

    protected ExpectedServiceRequest copy() {
        ExpectedServiceRequest request = new ExpectedServiceRequest();
        request.setServiceName(getServiceName());
        request.setExpectedServiceRequest(getExpectedServiceRequest());
        request.setIgnoredTags(getIgnoredTags());
        return request;
    }
}
