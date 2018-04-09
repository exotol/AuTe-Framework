package ru.bsc.test.at.executor.helper;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;
import ru.bsc.test.at.executor.ei.mqmocker.MqMockerAdmin;
import ru.bsc.test.at.executor.ei.mqmocker.model.MockedRequest;
import ru.bsc.test.at.executor.exception.ComparisonException;
import ru.bsc.test.at.executor.model.ExpectedMqRequest;
import ru.bsc.test.at.executor.model.ScenarioVariableFromMqRequest;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.service.AtExecutor;

import javax.script.ScriptException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class MqMockHelper {

    public void assertMqRequests(MqMockerAdmin mqMockerAdmin, String testId, Step step, Map<String, Object> scenarioVariables, Integer mqCheckCount, Long mqCheckInterval) throws Exception {
        if (mqMockerAdmin == null) {
            return;
        }

        if (step.getExpectedMqRequestList() == null || step.getExpectedMqRequestList().isEmpty()) {
            return;
        }

        List<ExpectedMqRequest> expectedMqRequestList = new LinkedList<>();
        step.getExpectedMqRequestList().forEach(expectedMqRequest -> {

            long count = 0;
            try {
                count = AtExecutor.parseLongOrVariable(scenarioVariables, AtExecutor.evaluateExpressions(expectedMqRequest.getCount(), scenarioVariables, null), 1);
            } catch (ScriptException e) {
                log.error("{}", e);
            }
            for (int i = 0; i < count; i++) {
                expectedMqRequestList.add(expectedMqRequest);
            }
        });

        List<MockedRequest> actualMqRequestList = mqMockerAdmin.getRequestListByTestId(testId);

        for (int counter = 0; counter < Math.min(mqCheckCount != null ? mqCheckCount : 10, 30) && expectedMqRequestList.size() != actualMqRequestList.size(); counter++) {
            Thread.sleep(Math.min(mqCheckInterval != null ? mqCheckInterval : 500L, 5000L));
            actualMqRequestList = mqMockerAdmin.getRequestListByTestId(testId);
        }

        if (expectedMqRequestList.size() != actualMqRequestList.size()) {
            // Вызвать ошибку: не совпадает количество вызовов сервисов
            throw new Exception(String.format(
                    "Invalid number of MQ requests: expected: %d, actual: %d",
                    expectedMqRequestList.size(),
                    actualMqRequestList.size()
            ));
        }

        if (step.getScenarioVariableFromMqRequestList() != null) {
            for (ScenarioVariableFromMqRequest variable : step.getScenarioVariableFromMqRequestList()) {
                MockedRequest actual = actualMqRequestList.stream()
                        .filter(mockedRequest -> Objects.equals(mockedRequest.getSourceQueue(), variable.getSourceQueue()))
                        .findAny()
                        .orElse(null);
                if (actual != null) {

                    // TODO Вынести работу с xml-документом в отдельный класс
                    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = builderFactory.newDocumentBuilder();
                    Document xmlDocument = builder.parse(new InputSource(new StringReader(actual.getRequestBody())));
                    XPath xPath = XPathFactory.newInstance().newXPath();
                    String valueFromMock = xPath.compile(variable.getXpath()).evaluate(xmlDocument);

                    scenarioVariables.put(variable.getVariableName(), valueFromMock);
                }
            }
        }

        for (ExpectedMqRequest expectedMqRequest : expectedMqRequestList) {
            MockedRequest actualRequest = actualMqRequestList.stream()
                    .filter(mockedRequest -> mockedRequest.getSourceQueue().equals(expectedMqRequest.getSourceQueue()))
                    .findAny().orElse(null);
            if (actualRequest != null) {
                actualMqRequestList.remove(actualRequest);

                compareRequest(
                        AtExecutor.evaluateExpressions(AtExecutor.insertSavedValues(expectedMqRequest.getRequestBody(), scenarioVariables), scenarioVariables, null),
                        actualRequest.getRequestBody(),
                        expectedMqRequest.getIgnoredTags() != null ?
                                new HashSet<>(Arrays.stream(expectedMqRequest.getIgnoredTags()
                                        .split(","))
                                        .map(String::trim)
                                        .collect(Collectors.toList())) : null
                );
            } else {
                throw new Exception(String.format("Queue %s is not called", expectedMqRequest.getSourceQueue()));
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
