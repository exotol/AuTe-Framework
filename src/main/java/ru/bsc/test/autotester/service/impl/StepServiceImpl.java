package ru.bsc.test.autotester.service.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.mapper.StepRoMapper;
import ru.bsc.test.autotester.repository.StepRepository;
import ru.bsc.test.autotester.ro.StepRo;
import ru.bsc.test.autotester.service.ProjectService;
import ru.bsc.test.autotester.service.StepService;

import java.io.IOException;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
public class StepServiceImpl implements StepService {

    private final StepRepository stepRepository;
    private final ProjectService projectService;

    private StepRoMapper stepRoMapper = Mappers.getMapper(StepRoMapper.class);

    @Autowired
    public StepServiceImpl(StepRepository stepRepository, ProjectService projectService) {
        this.stepRepository = stepRepository;
        this.projectService = projectService;
    }

    @Override
    public Step findOne(String projectCode, String scenarioPath, String stepCode) throws IOException {
        synchronized (projectService) {
            return stepRepository.findStep(projectCode, scenarioPath, stepCode);
        }
    }

    @Override
    public Step save(String projectCode, String scenarioPath, String stepCode, Step step) throws IOException {
        synchronized (projectService) {
            return stepRepository.saveStep(projectCode, scenarioPath, stepCode, step);
        }
    }

    @Override
    public StepRo updateFromRo(String projectCode, String scenarioPath, String stepCode, StepRo stepRo) throws IOException {
        synchronized (projectService) {
            Step newStep = stepRoMapper.updateStep(stepRo);
            newStep.setCode(stepCode);
            newStep = save(projectCode, scenarioPath, stepCode, newStep);

            return stepRoMapper.stepToStepRo(newStep);
            /*
            Scenario scenario = scenarioService.findOne(projectCode, scenarioPath);
            Step existsStep = scenario.getStepList()
                    .stream()
                    .filter(step1 -> Objects.equals(step1.getCode(), stepRo.getCode()))
                    .findAny()
                    .orElse(null);

            if (existsStep != null) {

                scenario.getStepList().set(scenario.getStepList().indexOf(existsStep), newStep);
                scenarioService.save(projectCode, scenarioPath, scenario);
                return stepRoMapper.stepToStepRo(newStep);
            }
            return null;
            */
        }
    }
}
