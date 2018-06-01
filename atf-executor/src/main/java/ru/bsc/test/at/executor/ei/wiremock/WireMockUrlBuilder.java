package ru.bsc.test.at.executor.ei.wiremock;

import org.springframework.util.Assert;

import static ru.bsc.test.at.executor.ei.wiremock.WireMockUrl.*;

/**
 * @author Pavel Golovkin
 */
public class WireMockUrlBuilder {

  private String baseUrl;

  WireMockUrlBuilder(String baseUrl) {
    Assert.notNull(baseUrl, "WireMock base url must not be null");
    this.baseUrl = baseUrl;
  }

  String deleteRestMappingUrl() {
    return baseUrl + DELETE_REST_MAPPING_URL;
  }

  String addRestMappingUrl() {
    return baseUrl + ADD_REST_MAPPING_URL;
  }

  String findRestRequestListUrl() {
    return baseUrl + FIND_REST_REQUEST_URL;
  }

  String findMQRequestListUrl() {
    return baseUrl + FIND_MQ_REQUEST_LIST_URL;
  }

  String addMQMappingUrl() {
    return baseUrl + ADD_MQ_MAPPING_URL;
  }
}
