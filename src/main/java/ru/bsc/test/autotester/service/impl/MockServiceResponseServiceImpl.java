package ru.bsc.test.autotester.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsc.test.at.executor.model.MockServiceResponse;
import ru.bsc.test.autotester.repository.MockServiceResponseRepository;
import ru.bsc.test.autotester.service.MockServiceResponseService;

import java.util.List;

/**
 * Created by sdoroshin on 28.07.2017.
 *
 */
@Service
public class MockServiceResponseServiceImpl implements MockServiceResponseService {

    private final MockServiceResponseRepository mockServiceResponseRepository;

    @Autowired
    public MockServiceResponseServiceImpl(MockServiceResponseRepository mockServiceResponseRepository) {
        this.mockServiceResponseRepository = mockServiceResponseRepository;
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
        mockServiceResponseRepository.delete(mockServiceResponse);
    }
}
