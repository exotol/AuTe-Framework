package ru.bsc.test.at.executor.service;

import ru.bsc.test.at.executor.model.ServiceResponse;

import java.util.List;

/**
 * Created by sdoroshin on 21.06.2017.
 *
 */
public interface ServiceResponseRepository {

    Long deleteByServiceNameAndProjectCode(String item, String projectCode);
    ServiceResponse save(ServiceResponse serviceResponse);
    List<ServiceResponse> findAllBySessionUidAndServiceNameInAndIsCalledOrderById(String sessionUid, List<String> expectedServicesName, Long isCalled);
}
