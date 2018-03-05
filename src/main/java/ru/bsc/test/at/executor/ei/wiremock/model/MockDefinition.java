package ru.bsc.test.at.executor.ei.wiremock.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

/**
 * Created by sdoroshin on 27.07.2017.
 *
 */
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MockDefinition {
    private String id;
    private String uuid;
    private Long priority;
    private MockRequest request;
    private MockResponse response;

    public MockDefinition(Long priority, String testIdHeaderName, String testId) {
        this.priority = priority;
        request = new MockRequest();
        HashMap<String, String> equalTo = new HashMap<>();
        equalTo.put("equalTo", testId);
        request.getHeaders().put(testIdHeaderName, equalTo);
        response = new MockResponse();
    }
}
