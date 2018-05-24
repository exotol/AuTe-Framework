package ru.bsc.test.at.executor.helper.client.impl.http;

import java.util.Map;

import lombok.AllArgsConstructor;
import ru.bsc.test.at.executor.helper.client.api.ClientRequest;

/**
 * @author Pavel Golovkin
 */
@AllArgsConstructor
public class ClientHttpRequest implements ClientRequest {
  protected final String url;
  protected final Object body;
  protected final HTTPMethod method;
  protected final Map headers;
  protected final String testId;
  protected final String testIdHeaderName;

  @Override
  public String getResource() {
    return url;
  }

  @Override
  public Object getBody() {
    return body;
  }

  @Override
  public Map getHeaders() {
    return headers;
  }

  @Override
  public String getTestId() {
    return testId;
  }

  @Override
  public String getTestIdHeaderName() {
    return testIdHeaderName;
  }

  public HTTPMethod getMethod() {
    return method;
  }


}
