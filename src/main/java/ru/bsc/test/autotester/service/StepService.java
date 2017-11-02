package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.ro.StepRo;

import java.util.List;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface StepService {
    Step findOne(Long stepId);
    Step save(Step step, List<Project> projectList);

    StepRo updateFromRo(Long stepId, StepRo stepRo);
}
