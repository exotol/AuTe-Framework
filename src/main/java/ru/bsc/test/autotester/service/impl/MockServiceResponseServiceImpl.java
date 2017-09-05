package ru.bsc.test.autotester.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsc.test.at.executor.model.MockServiceResponse;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.repository.MockServiceResponseRepository;
import ru.bsc.test.autotester.service.MockServiceResponseService;
import ru.bsc.test.autotester.service.StepService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 28.07.2017.
 *
 */
@Service
public class MockServiceResponseServiceImpl implements MockServiceResponseService {

    private final MockServiceResponseRepository mockServiceResponseRepository;
    private final StepService stepService;

    @Autowired
    public MockServiceResponseServiceImpl(MockServiceResponseRepository mockServiceResponseRepository, StepService stepService) {
        this.mockServiceResponseRepository = mockServiceResponseRepository;
        this.stepService = stepService;
    }

    @Override
    public void save(List<MockServiceResponse> mockServiceResponseList) {
        mockServiceResponseRepository.save(mockServiceResponseList);
    }

    @Override
    public MockServiceResponse findOne(Long mockServiceId) {
        return mockServiceResponseRepository.findOne(mockServiceId);
    }

    @Override
    public void delete(MockServiceResponse mockServiceResponse) {
        Step step = mockServiceResponse.getStep();
        step.setMockServiceResponseList(step.getMockServiceResponseList().stream()
                .filter(mockServiceResponse1 -> !Objects.equals(mockServiceResponse1.getId(), mockServiceResponse.getId()))
                .collect(Collectors.toList())
        );
        stepService.save(step);
    }
}
