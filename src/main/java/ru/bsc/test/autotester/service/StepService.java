package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.Step;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface StepService {
    void deleteStep(Long stepId);
    Step findOne(Long stepId);
    Step save(Step step);
}
