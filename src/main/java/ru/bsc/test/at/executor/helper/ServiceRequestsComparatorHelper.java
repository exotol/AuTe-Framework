package ru.bsc.test.at.executor.helper;

import org.xml.sax.SAXParseException;
import org.xmlunit.XMLUnitException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;
import ru.bsc.test.at.executor.ei.wiremock.WireMockAdmin;
import ru.bsc.test.at.executor.ei.wiremock.model.MockRequest;
import ru.bsc.test.at.executor.ei.wiremock.model.WireMockRequest;
import ru.bsc.test.at.executor.exception.ComparisonException;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Step;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static ru.bsc.test.at.executor.step.executor.AbstractStepExecutor.evaluateExpressions;
import static ru.bsc.test.at.executor.step.executor.AbstractStepExecutor.insertSavedValues;

/**
 * Created by sdoroshin on 30.05.2017.
 *
 */
public class ServiceRequestsComparatorHelper {

    public static final String IGNORE = "\\u002A"+"ignore"+"\\u002A";
    public static final String CLEAR_STR_PATTERN = "(\r\n|\n\r|\r|\n)";

    private void compareWSRequest(String expectedRequest, String actualRequest, Set<String> ignoredTags) throws ComparisonException {
        try{
            compareWSRequestAsXml(expectedRequest, actualRequest, ignoredTags);
        }catch (XMLUnitException uException){
            // определяем, что упало при парсинге XML, далее сравниваем как строку
            if(uException.getCause() instanceof SAXParseException){
                compareWSRequestAsString(expectedRequest, actualRequest);
            }
        }
    }

    private void compareWSRequestAsXml(String expectedRequest, String actualRequest, Set<String> ignoredTags) throws ComparisonException {
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

    private void compareWSRequestAsString(String expectedRequest, String actualRequest) throws ComparisonException {
        String[] split = expectedRequest.replaceAll(CLEAR_STR_PATTERN, "").split(IGNORE);
        actualRequest = actualRequest.replaceAll(CLEAR_STR_PATTERN, "");

        if(split.length == 1 && !Objects.equals(defaultIfNull(split[0],""), defaultIfNull(actualRequest,""))){
            throw new ComparisonException(null, expectedRequest, actualRequest);
        }

        int i = 0;
        boolean notEquals = false;
        for (String s : split){
            i = actualRequest.indexOf(s, i);
            if (i < 0) {
                notEquals = true;
                break;
            }
        }

        if (notEquals) {
            throw new ComparisonException(null, expectedRequest, actualRequest);
        }

    }

    public void assertTestCaseWSRequests(Project project, Map<String, Object> scenarioVariables, WireMockAdmin wireMockAdmin, String testId, Step step) throws Exception {
        if (wireMockAdmin == null) {
            return;
        }
        // Список ожидаемых запросов к сервисам
        List<ExpectedServiceRequest> expectedRequestList = step.getExpectedServiceRequests();
        if (expectedRequestList.isEmpty()) {
            return;
        }

        // Список актуальных запросов к сервисам
        MockRequest mockRequest = new MockRequest();
        mockRequest.getHeaders().put(project.getTestIdHeaderName(), createEqualToHeader(testId));
        List<WireMockRequest> actualRequestList = wireMockAdmin.findRestRequests(mockRequest).getRequests();

        // compare request size
        if (expectedRequestList.size() != actualRequestList.size()) {
            // Вызвать ошибку: не совпадает количество вызовов сервисов
            throw new Exception(String.format(
                    "Invalid number of service requests: expected: %d, actual: %d",
                    expectedRequestList.size(),
                    actualRequestList.size()
            ));
        }

        for (ExpectedServiceRequest expectedRequest: expectedRequestList) {
            WireMockRequest actualRequest = actualRequestList.stream()
                    .filter(wireMockRequest -> wireMockRequest.getUrl().equals(expectedRequest.getServiceName()))
                    .findAny().orElse(null);
            if (actualRequest != null) {
                actualRequestList.remove(actualRequest);
                String expectedServiceRequest = insertSavedValues(expectedRequest.getExpectedServiceRequest(), scenarioVariables);
                expectedServiceRequest = evaluateExpressions(expectedServiceRequest, scenarioVariables, null);
                compareWSRequest(
                        expectedServiceRequest,
                        actualRequest.getBody(),
                        expectedRequest.getIgnoredTags() != null ?
                                new HashSet<>(Arrays.stream(expectedRequest.getIgnoredTags()
                                        .split(","))
                                        .map(String::trim)
                                        .collect(Collectors.toList())) : null
                );
            } else {
                throw new Exception(String.format("Service %s is not called", expectedRequest.getServiceName()));
            }
        }
    }

    private Map<String, String> createEqualToHeader(String testId) {
        Map<String, String> header = new HashMap<>();
        header.put("equalTo", testId);
        return header;
    }
}
