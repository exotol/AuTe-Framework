package ru.bsc.test.at.executor.helper.client.impl.mq;

import lombok.AllArgsConstructor;
import ru.bsc.test.at.executor.helper.client.api.ClientRequest;

import java.util.Map;

/**
 * @author Pavel Golovkin
 */
@AllArgsConstructor
public class ClientMQRequest implements ClientRequest {
  private final String queueName;
  private final String messageBody;
  private final Map headers;
  private final String testId;
  private final String testIdHeaderName;

  @Override
  public String getResource() {
    return queueName;
  }

  @Override
  public Object getBody() {
    return messageBody;
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
}
