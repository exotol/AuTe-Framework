package ru.bsc.test.autotester.service;

import ru.bsc.test.autotester.model.ExpectedServiceRequest;

import java.util.List;

/**
 * Created by sdoroshin on 29.05.2017.
 *
 */
public interface ExpectedServiceRequestService {
    List<ExpectedServiceRequest> findAllByStepIdOrderBySort(Long id);
    ExpectedServiceRequest save(ExpectedServiceRequest expectedServiceRequest);


}
