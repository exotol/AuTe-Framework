package ru.bsc.test.at.executor.wiremock.mockdefinition;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by sdoroshin on 27.07.2017.
 */
@Getter
@Setter
@SuppressWarnings("WeakerAccess")
public class MockResponse {
    private Integer status = 200;
    private String body;
    private Map<String, String> headers;
}
