package ru.bsc.test.autotester.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.*;
import ru.bsc.test.autotester.model.ExpectedServiceRequest;
import ru.bsc.test.autotester.model.ServiceResponse;
import ru.bsc.test.autotester.model.Step;
import ru.bsc.test.autotester.repository.ServiceResponseRepository;
import ru.bsc.test.autotester.service.ExpectedServiceRequestService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 30.05.2017.
 *
 */
@Service
public class ServiceRequestsComparatorHelper {

    private final ExpectedServiceRequestService expectedServiceRequestService;
    private final ServiceResponseRepository serviceResponseRepository;

    @Autowired
    public ServiceRequestsComparatorHelper(ExpectedServiceRequestService expectedServiceRequestService, ServiceResponseRepository serviceResponseRepository) {
        this.expectedServiceRequestService = expectedServiceRequestService;
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
            throw new Exception(diff.toString() + "\n ====== Expected ===== \n" + expectedRequest + "\n ====== Actual ===== \n" + actualRequest);
        }
    }

    public void assertTestCaseWSRequests(String sessionUid, Step step) throws Exception {
        // Список ожидаемых запросов к сервисам
        List<ExpectedServiceRequest> expectedRequestList = expectedServiceRequestService.findAllByStepIdOrderBySort(step.getId());
        if (expectedRequestList.isEmpty()) {
            // TODO проверить прохождение тестов, у которых не настроены ожидаемые вызовы сервисов
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
