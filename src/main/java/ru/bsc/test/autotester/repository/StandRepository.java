package ru.bsc.test.autotester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bsc.test.at.executor.model.Stand;

/**
 * Created by sdoroshin on 12.07.2017.
 *
 */
@Repository
public interface StandRepository extends JpaRepository<Stand, Long> {
}
