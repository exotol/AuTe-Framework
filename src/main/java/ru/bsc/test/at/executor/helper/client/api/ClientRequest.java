package ru.bsc.test.at.executor.helper.client.api;

import java.util.Map;

/**
 * @author Pavel Golovkin
 */
public interface ClientRequest {
  String getResource();
  Object getBody();
  Map getHeaders();
  String getTestId();
  String getTestIdHeaderName();
}
