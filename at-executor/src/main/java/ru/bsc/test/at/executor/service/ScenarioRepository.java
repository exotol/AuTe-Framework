package ru.bsc.test.at.executor.service;

import ru.bsc.test.at.executor.model.Scenario;

/**
 * Created by sdoroshin on 21.06.2017.
 *
 */
public interface ScenarioRepository {
    Scenario save(Scenario scenario);
}
