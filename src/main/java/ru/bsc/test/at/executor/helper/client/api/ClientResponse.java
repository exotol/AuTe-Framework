package ru.bsc.test.at.executor.helper.client.api;

import java.util.List;
import java.util.Map;

/**
 * @author Pavel Golovkin
 */
public interface ClientResponse {

  int getStatusCode();
  String getContent();
  Map<String, List<String>> getHeaders();
}
