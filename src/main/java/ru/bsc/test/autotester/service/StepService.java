package ru.bsc.test.autotester.service;

import ru.bsc.test.autotester.model.Step;

import java.util.List;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface StepService {
    List<Step> findAllByScenarioId(Long scenarioId);
    List<Step> saveSteps(List<Step> steps);
    void deleteStep(Long stepId);
    Step findOne(Long stepId);
    Step save(Step step);
}
