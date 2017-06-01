package ru.bsc.test.autotester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bsc.test.autotester.model.ServiceResponse;

import java.util.List;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Repository
public interface ServiceResponseRepository extends JpaRepository<ServiceResponse, Long> {
    List<ServiceResponse> findAllBySessionUidAndIsCalledOrderById(String sessionUid, Long isCalled);

    @Transactional
    Long deleteByServiceName(String serviceName);

    List<ServiceResponse> findAllBySessionUidAndServiceNameInAndIsCalledOrderById(String sessionUid, List<String> expectedServicesName, Long isCalled);
}
