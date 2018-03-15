package ru.bsc.test.at.executor.helper;

import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;
import ru.bsc.test.at.executor.ei.mqmocker.MqMockerAdmin;
import ru.bsc.test.at.executor.ei.mqmocker.model.MockedRequest;
import ru.bsc.test.at.executor.exception.ComparisonException;
import ru.bsc.test.at.executor.model.ExpectedMqRequest;
import ru.bsc.test.at.executor.model.Step;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MqMockHelper {


    public void assertMqRequests(MqMockerAdmin mqMockerAdmin, String testId, Step step) throws Exception {
        if (mqMockerAdmin == null) {
            return;
        }

        List<ExpectedMqRequest> expectedMqRequestList = step.getExpectedMqRequestList();
        if (expectedMqRequestList == null || expectedMqRequestList.isEmpty()) {
            return;
        }

        List<MockedRequest> actualMqRequestList = mqMockerAdmin.getRequestListByTestId(testId);

        // TODO Это УДАЛИТЬ. Только для дебага
        // actualMqRequestList.add(mqMockerAdmin.getRequestListByTestId(null).get(mqMockerAdmin.getRequestListByTestId(null).size() - 1));

        if (expectedMqRequestList.size() != actualMqRequestList.size()) {
            // Вызвать ошибку: не совпадает количество вызовов сервисов
            throw new Exception(String.format(
                    "Invalid number of MQ requests: expected: %d, actual: %d",
                    expectedMqRequestList.size(),
                    actualMqRequestList.size()
            ));
        }

        for (ExpectedMqRequest expectedMqRequest : expectedMqRequestList) {
            MockedRequest actualRequest = actualMqRequestList.stream()
                    .filter(mockedRequest -> mockedRequest.getSourceQueue().equals(expectedMqRequest.getSourceQueue()))
                    .findAny().orElse(null);
            if (actualRequest != null) {
                actualMqRequestList.remove(actualRequest);

                compareRequest(
                        expectedMqRequest.getRequestBody(),
                        actualRequest.getRequestBody(),
                        expectedMqRequest.getIgnoredTags() != null ?
                                new HashSet<>(Arrays.stream(expectedMqRequest.getIgnoredTags()
                                        .split(","))
                                        .map(String::trim)
                                        .collect(Collectors.toList())) : null
                );
            } else {
                throw new Exception(String.format("Service %s is not called", expectedMqRequest.getSourceQueue()));
            }
        }
    }

    private void compareRequest(String expectedRequest, String actualRequest, Set<String> ignoredTags) throws ComparisonException {
        Diff diff = DiffBuilder.compare(expectedRequest)
                .withTest(actualRequest)
                .checkForIdentical()
                .ignoreComments()
                .ignoreWhitespace()
                .withDifferenceEvaluator(new IgnoreTagsDifferenceEvaluator(ignoredTags))
                .build();

        if (diff.hasDifferences()) {
            throw new ComparisonException(diff, expectedRequest, actualRequest);
        }
    }
}
