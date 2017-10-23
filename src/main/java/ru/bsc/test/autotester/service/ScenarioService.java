package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;

import java.util.List;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface ScenarioService {
    List<Scenario> findAllByProjectIdAndScenarioGroupId(Long projectId, Long scenarioGroupId);

    List<Scenario> executeScenarioList(Project project, List<Scenario> scenarioList);

    Scenario save(Scenario scenario);
    Scenario findOne(long scenarioId);
    void delete(Scenario scenario);

    void parseExpectedServiceRequestsJmba(String expectedRequestsBaseDir, Scenario scenario);

    List<Scenario> findAll(List<Long> scenarioIdList);

    Step cloneStep(Step stepId);
}
