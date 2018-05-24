package ru.bsc.test.at.executor.exception;

/**
 * Exception when scenario can't be continued
 */
public class ScenarioStopException extends RuntimeException {

  public ScenarioStopException(String message) {
    super(message);
  }
}
