package ru.bsc.test.at.executor.helper.client.impl.http;

import ru.bsc.test.at.executor.model.Step;

import java.util.Map;

/**
 * @author Pavel Golovkin
 */
public final class ClientHttpRequestWithVariables extends ClientHttpRequest {
  private final Map<String, Object> scenarioVariables;
  private final String projectPath;
  private final Step step;

  public ClientHttpRequestWithVariables(String url, Object body, HTTPMethod method, Map headers, String testId, String testIdName, Map<String, Object> scenarioVariables, String projectPath, Step step) {
    super(url, body, method, headers, testId, testIdName);
    this.scenarioVariables = scenarioVariables;
    this.projectPath = projectPath;
    this.step = step;
  }

  public Map<String, Object> getScenarioVariables() {
    return scenarioVariables;
  }

  public String getProjectPath() {
    return projectPath;
  }

  public Step getStep() {
    return step;
  }
}
