package ru.bsc.test.autotester.service.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.mapper.StepRoMapper;
import ru.bsc.test.autotester.repository.StepRepository;
import ru.bsc.test.autotester.ro.StepRo;
import ru.bsc.test.autotester.service.ProjectService;
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
    private final ProjectService projectService;

    private StepRoMapper stepRoMapper = Mappers.getMapper(StepRoMapper.class);

    @Autowired
    public StepServiceImpl(StepRepository stepRepository, ProjectService projectService) {
        this.stepRepository = stepRepository;
        this.projectService = projectService;
    }

    @Override
    public Step findOne(Long stepId) {
        synchronized (projectService) {
            return stepRepository.findStep(stepId);
        }
    }

    @Override
    public Step save(Step step, List<Project> projectList) {
        synchronized (projectService) {
            return stepRepository.saveStep(step, projectList);
        }
    }

    @Override
    public StepRo updateFromRo(Long stepId, StepRo stepRo) {
        synchronized (projectService) {
            List<Project> projectList = projectService.findAll();
            Step step = findOne(projectList, stepId);
            if (step != null) {
                stepRoMapper.updateStep(stepRo, step);
                stepRepository.saveStep(step, projectList);
                return stepRoMapper.stepToStepRo(step);
            }
            return null;
        }
    }

    private Step findOne(List<Project> projectList, Long stepId) {
        return projectList.stream()
                .flatMap(project -> project.getScenarioList().stream())
                .flatMap(scenario -> scenario.getStepList().stream())
                .filter(step -> Objects.equals(step.getId(), stepId))
                .findAny()
                .orElse(null);
    }
}
