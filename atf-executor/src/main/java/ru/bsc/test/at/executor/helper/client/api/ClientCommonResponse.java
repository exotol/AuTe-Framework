package ru.bsc.test.at.executor.helper.client.api;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author Pavel Golovkin
 */
@AllArgsConstructor
public class ClientCommonResponse implements ClientResponse {
  private final int statusCode;
  private final String content;
  private final Map<String, List<String>> headers;

  @Override
  public int getStatusCode() {
    return statusCode;
  }

  @Override
  public String getContent() {
    return content;
  }

  @Override
  public Map<String, List<String>> getHeaders() {
    return headers;
  }
}
