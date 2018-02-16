package ru.bsc.test.at.executor.wiremock.mockdefinition;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;

/**
 * Created by sdoroshin on 27.07.2017.
 *
 */
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MockDefinition {

    private String id;
    private String uuid;
    private Long priority;
    private MockRequest request;
    private MockResponse response;

    public MockDefinition() {
    }

    public MockDefinition(Long priority, String testIdHeaderName, String testId) {
        this.priority = priority;
        request = new MockRequest();
        HashMap<String, String> equalTo = new HashMap<>();
        equalTo.put("equalTo", testId);
        request.getHeaders().put(testIdHeaderName, equalTo);
        response = new MockResponse();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public MockRequest getRequest() {
        return request;
    }

    public void setRequest(MockRequest request) {
        this.request = request;
    }

    public MockResponse getResponse() {
        return response;
    }

    public void setResponse(MockResponse response) {
        this.response = response;
    }
}
