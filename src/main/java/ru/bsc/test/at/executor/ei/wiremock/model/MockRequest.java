package ru.bsc.test.at.executor.ei.wiremock.model;

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
    private BasicAuthCredentials basicAuthCredentials;
    private Map<String, Map<String, String>> headers = new HashMap<>();
    private MatchesXPath  bodyPatterns[];
}
