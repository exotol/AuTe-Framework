package ru.bsc.test.at.executor.helper;

import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.wiremock.WireMockAdmin;
import ru.bsc.test.at.executor.wiremock.mockdefinition.MockRequest;
import ru.bsc.test.at.executor.wiremock.mockdefinition.WireMockRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sdoroshin on 30.05.2017.
 *
 */
public class ServiceRequestsComparatorHelper {

    private void compareWSRequest(String expectedRequest, String actualRequest, Set<String> ignoredTags) throws Exception {
        Diff diff = DiffBuilder.compare(expectedRequest)
                .withTest(actualRequest)
                .checkForIdentical()
                .ignoreComments()
                .ignoreWhitespace()
                .withDifferenceEvaluator(new IgnoreTagsDifferenceEvaluator(ignoredTags))
                .build();

        if (diff.hasDifferences()) {
            throw new Exception("Service request error (request differences):\n" + diff.toString() + "\n ====== Expected ===== \n" + expectedRequest + "\n ====== Actual ===== \n" + actualRequest);
        }
    }

    public void assertTestCaseWSRequests(Project project, WireMockAdmin wireMockAdmin, String testId, Step step) throws Exception {
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
        mockRequest.getHeaders().put(project.getTestIdHeaderName(), new HashMap<String, String>() {{ put("equalTo", testId); }});
        List<WireMockRequest> actualRequestList = wireMockAdmin.findRequests(mockRequest).getRequests();

        // compare request size
        if (expectedRequestList.size() != actualRequestList.size()) {
            // Вызвать ошибку: не совпадает количество вызовов сервисов
            throw new Exception("Invalid number of service requests: expected: " + expectedRequestList.size() + ", actual: " + actualRequestList.size());
        }

        for (ExpectedServiceRequest expectedRequest: expectedRequestList) {
            WireMockRequest actualRequest = actualRequestList.stream()
                    .filter(wireMockRequest -> wireMockRequest.getUrl().equals(expectedRequest.getServiceName()))
                    .findAny().orElseGet(null);
            if (actualRequest != null) {
                compareWSRequest(
                        expectedRequest.getExpectedServiceRequest(),
                        actualRequest.getBody(),
                        expectedRequest.getIgnoredTags() != null ? new HashSet<>(Arrays.asList(expectedRequest.getIgnoredTags().split(","))) : null
                );
            } else {
                throw new Exception("Service " + expectedRequest.getServiceName() + " not called");
            }
        }
    }
}
