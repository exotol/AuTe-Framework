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

    public MockDefinition() {
    }

    public MockDefinition(Long priority, String testIdHeaderName, String testId) {
        this.priority = priority;
        request = new MockRequest();
        request.getHeaders().put(testIdHeaderName, new HashMap<String, String>() {{ put("equalTo", testId); }});
        response = new MockResponse();
    }

    private String id;
    private String uuid;
    private Long priority;
    private MockRequest request;
    private MockResponse response;

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
