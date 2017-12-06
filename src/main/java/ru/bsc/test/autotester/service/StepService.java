package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.ro.StepRo;

import java.io.IOException;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface StepService {
    Step findOne(String projectCode, String scenarioPath, String stepCode) throws IOException;
    Step save(String projectCode, String scenarioPath, String stepCode, Step step) throws IOException;

    StepRo updateFromRo(String projectCode, String scenarioPath, String stepCode, StepRo stepRo) throws IOException;
}
