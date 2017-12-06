package ru.bsc.test.autotester.repository;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Step;

import java.io.IOException;
import java.util.List;

/**
 * Created by sdoroshin on 27.10.2017.
 *
 */

public interface StepRepository {

    Step findStep(String projectCode, String scenarioPath, String stepCode) throws IOException;

    Step saveStep(String projectCode, String scenarioPath, String stepCode, Step step) throws IOException;
}
