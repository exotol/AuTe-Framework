package ru.bsc.test.autotester.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.repository.ExpectedServiceRequestRepository;
import ru.bsc.test.autotester.service.ExpectedServiceRequestService;
import ru.bsc.test.autotester.service.StepService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 29.05.2017.
 *
 */
@Service
public class ExpectedServiceRequestServiceImpl implements ExpectedServiceRequestService {

    private final ExpectedServiceRequestRepository expectedServiceRequestRepository;
    private final StepService stepService;

    @Autowired
    public ExpectedServiceRequestServiceImpl(ExpectedServiceRequestRepository expectedServiceRequestRepository, StepService stepService) {
        this.expectedServiceRequestRepository = expectedServiceRequestRepository;
        this.stepService = stepService;
    }

    @Override
    public ExpectedServiceRequest save(ExpectedServiceRequest expectedServiceRequest) {
        return expectedServiceRequestRepository.save(expectedServiceRequest);
    }

    @Override
    public List<ExpectedServiceRequest> save(List<ExpectedServiceRequest> expectedServiceRequestList) {
        return expectedServiceRequestRepository.save(expectedServiceRequestList);
    }

    @Override
    public ExpectedServiceRequest findOne(Long expectedServiceRequestId) {
        return expectedServiceRequestRepository.findOne(expectedServiceRequestId);
    }

    @Override
    public void delete(ExpectedServiceRequest request) {
        Step step = request.getStep();
        step.setExpectedServiceRequests(step.getExpectedServiceRequests().stream()
                .filter(expectedServiceRequest -> !Objects.equals(expectedServiceRequest.getId(), request.getId()))
                .collect(Collectors.toList())
        );
        stepService.save(step);
    }
}
