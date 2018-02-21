package ru.bsc.test.at.executor.exception;

import org.xmlunit.diff.Diff;

/**
 * Created by smakarov
 * 20.02.2018 10:52
 */
public class ComparisonException extends RuntimeException {
    public ComparisonException(Diff diff, String expectedRequest, String actualRequest) {
        super(String.format(
                "Service request error (request differences):%s\n\tExpected: %s\n\tActual: %s\n",
                diff,
                expectedRequest,
                actualRequest
        ));
    }
}
