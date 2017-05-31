package ru.bsc.test.autotester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bsc.test.autotester.model.Project;
import ru.bsc.test.autotester.model.Scenario;

import java.util.List;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
