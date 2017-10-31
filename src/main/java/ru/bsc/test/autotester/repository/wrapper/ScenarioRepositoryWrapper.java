package ru.bsc.test.autotester.repository.wrapper;

import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.service.ScenarioRepository;

/**
 * Created by sdoroshin on 21.06.2017.
 *
 */
public class ScenarioRepositoryWrapper implements ScenarioRepository {

    private ru.bsc.test.autotester.repository.ScenarioRepository scenarioRepository;
    public ScenarioRepositoryWrapper(ru.bsc.test.autotester.repository.ScenarioRepository scenarioRepository) {
        this.scenarioRepository = scenarioRepository;
    }

    @Override
    public Scenario save(Scenario scenario) {
        return scenarioRepository.saveScenario(scenario);
    }
}
