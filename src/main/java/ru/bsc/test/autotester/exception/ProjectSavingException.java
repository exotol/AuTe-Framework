package ru.bsc.test.autotester.exception;

/**
 * Created by smakarov
 * 06.03.2018 15:14
 */
public class ProjectSavingException extends RuntimeException {
    public ProjectSavingException(String message) {
        super(message);
    }

    public ProjectSavingException(String message, Throwable cause) {
        super(message, cause);
    }
}
