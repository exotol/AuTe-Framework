package ru.bsc.test.at.executor.wiremock.mockdefinition;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sdoroshin on 27.07.2017.
 *
 */
@Getter
@Setter
public class MockRequest {
    private String method;
    private String url;
    private Map<String, Map<String, String>> headers = new HashMap<>();
}
