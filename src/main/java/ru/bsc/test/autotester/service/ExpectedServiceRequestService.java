package ru.bsc.test.autotester.service;

import ru.bsc.test.autotester.model.ExpectedServiceRequest;
import ru.bsc.test.autotester.model.Step;

import java.util.List;

/**
 * Created by sdoroshin on 29.05.2017.
 *
 */
public interface ExpectedServiceRequestService {
    List<ExpectedServiceRequest> findAllByStepIdOrderBySort(Long id);
    List<ExpectedServiceRequest> save(List<ExpectedServiceRequest> expectedServiceRequestList);
    ExpectedServiceRequest save(ExpectedServiceRequest expectedServiceRequest);
    ExpectedServiceRequest findOne(Long expectedServiceRequestId);
    void delete(ExpectedServiceRequest request);
}
