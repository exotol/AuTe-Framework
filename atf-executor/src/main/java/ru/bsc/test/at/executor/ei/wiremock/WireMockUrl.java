package ru.bsc.test.at.executor.ei.wiremock;

/**
 * @author Pavel Golovkin
 */
interface WireMockUrl {

  String DELETE_REST_MAPPING_URL = "/__admin/mappings/";
  String ADD_REST_MAPPING_URL = "/__admin/mappings/";
  String FIND_REST_REQUEST_URL = "/__admin/requests/find";
  String FIND_MQ_REQUEST_LIST_URL = "/mq-mock/__admin/request-list";
  String ADD_MQ_MAPPING_URL = "/mq-mock/__admin/add-mapping";
}
