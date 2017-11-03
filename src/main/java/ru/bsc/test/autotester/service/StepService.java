package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.dto.StepDto;
import ru.bsc.test.autotester.service.impl.ScenarioServiceImpl;

import java.util.List;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface StepService {
    List<Step> saveSteps(Long scenarioId, List<StepDto> stepDtoList);

    void deleteStep(Long stepId);
    Step findOne(Long stepId);
    Step save(Step step);

    void setScenarioService(ScenarioService scenarioService);

    List<Step> findByRelativeUrl(Long scenarioId, String relativeUrl);
}
