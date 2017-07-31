package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.MockServiceResponse;

import java.util.List;

/**
 * Created by sdoroshin on 28.07.2017.
 *
 */
public interface MockServiceResponseService {

    void save(List<MockServiceResponse> mockServiceResponseList);

    MockServiceResponse findOne(Long mockServiceId);

    void delete(MockServiceResponse mockServiceResponse);
}
