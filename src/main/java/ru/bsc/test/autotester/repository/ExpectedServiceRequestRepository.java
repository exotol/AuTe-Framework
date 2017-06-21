package ru.bsc.test.autotester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;

import java.util.List;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Repository
public interface ExpectedServiceRequestRepository extends JpaRepository<ExpectedServiceRequest, Long> {
    List<ExpectedServiceRequest> findAllByStepIdOrderBySort(Long stepId);
}
