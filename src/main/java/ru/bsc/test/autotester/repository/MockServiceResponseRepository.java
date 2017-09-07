package ru.bsc.test.autotester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bsc.test.at.executor.model.MockServiceResponse;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Repository
public interface MockServiceResponseRepository extends JpaRepository<MockServiceResponse, Long> {
}