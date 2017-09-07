package ru.bsc.test.at.executor.wiremock.mockdefinition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by sdoroshin on 06.09.2017.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestList {
    private List<WireMockRequest> requests;

    public List<WireMockRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<WireMockRequest> requests) {
        this.requests = requests;
    }
}
