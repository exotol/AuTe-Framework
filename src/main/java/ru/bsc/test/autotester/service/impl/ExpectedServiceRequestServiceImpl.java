package ru.bsc.test.autotester.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsc.test.autotester.model.ExpectedServiceRequest;
import ru.bsc.test.autotester.repository.ExpectedServiceRequestRepository;
import ru.bsc.test.autotester.service.ExpectedServiceRequestService;

import java.util.List;

/**
 * Created by sdoroshin on 29.05.2017.
 *
 */
@Service
public class ExpectedServiceRequestServiceImpl implements ExpectedServiceRequestService {

    private final ExpectedServiceRequestRepository expectedServiceRequestRepository;

    @Autowired
    public ExpectedServiceRequestServiceImpl(ExpectedServiceRequestRepository expectedServiceRequestRepository) {
        this.expectedServiceRequestRepository = expectedServiceRequestRepository;
    }

    @Override
    public ExpectedServiceRequest save(ExpectedServiceRequest expectedServiceRequest) {
        return expectedServiceRequestRepository.save(expectedServiceRequest);
    }

    @Override
    public List<ExpectedServiceRequest> findAllByStepIdOrderBySort(Long id) {
        return expectedServiceRequestRepository.findAllByStepIdOrderBySort(id);
    }
}
