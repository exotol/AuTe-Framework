package ru.bsc.test.autotester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bsc.test.at.executor.model.Scenario;

import java.util.List;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Repository
public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    List<Scenario> findAllByProjectIdAndScenarioGroupIdOrderByScenarioGroupIdDescNameAsc(Long projectId, Long scenarioGroupId);

    @Query("SELECT s FROM Scenario s JOIN s.steps st JOIN s.project p where p.id=:projectId and st.relativeUrl like :relativeUrl")
    List<Scenario> findByRelativeUrl(@Param("projectId") Long projectId, @Param("relativeUrl") String relativeUrl);
}
