package ru.bsc.test.at.executor.helper;

import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;
import ru.bsc.test.at.executor.model.ServiceResponse;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.service.ServiceResponseRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 30.05.2017.
 *
 */
@SuppressWarnings("Duplicates")
public class ServiceRequestsComparatorHelper {

    private final ServiceResponseRepository serviceResponseRepository;

    public ServiceRequestsComparatorHelper(ServiceResponseRepository serviceResponseRepository) {
        this.serviceResponseRepository = serviceResponseRepository;
    }

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

    public void assertTestCaseWSRequests(String sessionUid, Step step) throws Exception {
        // Список ожидаемых запросов к сервисам
        List<ExpectedServiceRequest> expectedRequestList = step.getExpectedServiceRequests();
        if (expectedRequestList.isEmpty()) {
            return;
        }
        List<String> expectedServicesName = expectedRequestList.stream().map(ExpectedServiceRequest::getServiceName).collect(Collectors.toList());

        // Список актуальных запросов к сервисам
        List<ServiceResponse> actualRequestList = serviceResponseRepository.findAllBySessionUidAndServiceNameInAndIsCalledOrderById(sessionUid, expectedServicesName, 1L);

        // compare request size
        if (expectedRequestList.size() != actualRequestList.size()) {
            // Вызвать ошибку: не совпадает количество вызовов сервисов
            throw new Exception("Invalid number of service requests: expected: " + expectedRequestList.size() + ", actual: " + actualRequestList.size());
        }

        int actualIndex = 0;
        for (ExpectedServiceRequest expectedRequest : expectedRequestList) {
            String actualRequestBody = actualRequestList.get(actualIndex).getActualRequest();
            compareWSRequest(
                    expectedRequest.getExpectedServiceRequest(),
                    actualRequestBody,
                    expectedRequest.getIgnoredTags() != null ? new HashSet<>(Arrays.asList(expectedRequest.getIgnoredTags().split(","))) : null
            );
        }
    }
}
