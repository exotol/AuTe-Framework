package ru.bsc.test.autotester.repository.impl;

import ru.bsc.test.at.executor.model.ServiceResponse;
import ru.bsc.test.at.executor.service.ServiceResponseRepository;

import java.util.List;

/**
 * Created by sdoroshin on 21.06.2017.
 *
 */
public class ServiceResponseRepositoryWrapper implements ServiceResponseRepository {

    private ru.bsc.test.autotester.repository.ServiceResponseRepository serviceResponseRepository;
    public ServiceResponseRepositoryWrapper(ru.bsc.test.autotester.repository.ServiceResponseRepository serviceResponseRepository) {
        this.serviceResponseRepository = serviceResponseRepository;
    }


    @Override
    public Long deleteByServiceNameAndProjectCode(String item, String projectCode) {
        return serviceResponseRepository.deleteByServiceNameAndProjectCode(item, projectCode);
    }

    @Override
    public ServiceResponse save(ServiceResponse serviceResponse) {
        return serviceResponseRepository.save(serviceResponse);
    }

    @Override
    public List<ServiceResponse> findAllBySessionUidAndServiceNameInAndIsCalledOrderById(String sessionUid, List<String> expectedServicesName, Long isCalled) {
        return serviceResponseRepository.findAllBySessionUidAndServiceNameInAndIsCalledOrderById(sessionUid, expectedServicesName, isCalled);
    }
}
