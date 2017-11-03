package ru.bsc.test.autotester.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.dto.StepDto;
import ru.bsc.test.autotester.repository.StepRepository;
import ru.bsc.test.autotester.service.ScenarioService;
import ru.bsc.test.autotester.service.StepService;

import java.util.List;
import java.util.Objects;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
public class StepServiceImpl implements StepService {

    private final StepRepository stepRepository;
    private ScenarioService scenarioService;

    public void setScenarioService(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @Autowired
    public StepServiceImpl(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    @Override
    public List<Step> saveSteps(Long scenarioId, List<StepDto> stepDtoList) {
        if (scenarioId != null) {
            Scenario scenario = scenarioService.findOne(scenarioId);
            scenario.getSteps().forEach(step -> {
                StepDto stepDto = stepDtoList.stream()
                        .filter(stepDtoItem -> Objects.equals(stepDtoItem.getId(), step.getId()))
                        .findFirst().orElse(null);
                if (stepDto != null) {
                    stepDto.copyProperties(step);
                }
            });
            scenario = scenarioService.save(scenario);
            return scenario.getSteps();
        } else {
            throw new RuntimeException("scenarioId cannot be null");
        }
    }

    @Override
    public void deleteStep(Long stepId) {
        stepRepository.delete(stepId);
    }

    @Override
    public Step findOne(Long stepId) {
        return stepRepository.findOne(stepId);
    }

    @Override
    public Step save(Step step) {
        return stepRepository.save(step);
    }

    @Override
    public List<Step> findByRelativeUrl(Long scenarioId, String relativeUrl) {
        return stepRepository.findAllByScenarioIdAndRelativeUrlContaining(scenarioId, relativeUrl);
    }
}
