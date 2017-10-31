package ru.bsc.test.autotester.repository;

import ru.bsc.test.at.executor.model.Step;

/**
 * Created by sdoroshin on 27.10.2017.
 *
 */

public interface StepRepository {

    Step findStep(Long stepId);

    Step saveStep(Step step);
}
