package ru.bsc.test.at.executor.wiremock.mockdefinition;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by sdoroshin on 06.09.2017.
 */
@Getter
@Setter
public class RequestList {
    private List<WireMockRequest> requests;
}
