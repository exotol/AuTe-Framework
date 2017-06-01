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

    @Transactional
    Long deleteByServiceNameAndProjectCode(String item, String projectCode);

    List<ServiceResponse> findAllBySessionUidAndServiceNameInAndIsCalledOrderById(String sessionUid, List<String> expectedServicesName, Long isCalled);
}
