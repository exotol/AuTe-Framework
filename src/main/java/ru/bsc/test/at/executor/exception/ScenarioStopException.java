package ru.bsc.test.at.executor.exception;

/**
 * Exception when scenario can't be continued
 */
public class ScenarioStopException extends Exception {

  public ScenarioStopException(String message) {
    super(message);
  }
}
