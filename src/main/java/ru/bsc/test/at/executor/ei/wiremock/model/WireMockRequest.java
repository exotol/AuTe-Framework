package ru.bsc.test.at.executor.ei.wiremock.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sdoroshin on 06.09.2017.
 *
 */
@Getter
@Setter
public class WireMockRequest {
    private String method;
    private String url;
    private Map<String, String> headers = new HashMap<>();
    private String body;
}
