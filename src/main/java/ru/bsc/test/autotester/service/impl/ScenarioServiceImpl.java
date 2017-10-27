package ru.bsc.test.autotester.service.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.service.AtExecutor;

import ru.bsc.test.autotester.exception.ResourceNotFoundException;
import ru.bsc.test.autotester.mapper.StepRoMapper;
import ru.bsc.test.autotester.repository.ScenarioRepository;
import ru.bsc.test.autotester.repository.impl.ScenarioRepositoryWrapper;
import ru.bsc.test.autotester.ro.StepRo;
import ru.bsc.test.autotester.service.ScenarioService;
import ru.bsc.test.autotester.service.StepService;

import java.util.Comparator;
import java.util.List;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
public class ScenarioServiceImpl implements ScenarioService {

    private final StepRoMapper stepRoMapper = Mappers.getMapper(StepRoMapper.class);

    private final ScenarioRepository scenarioRepository;
    private final StepService stepService;

    @Autowired
    public ScenarioServiceImpl(ScenarioRepository scenarioRepository, StepService stepService) {
        this.scenarioRepository = scenarioRepository;
        this.stepService = stepService;
    }

    @Override
    public List<Scenario> executeScenarioList(Project project, List<Scenario> scenarioList) {
        // TODO: Избавиться от ScenarioRepositoryWrapper.
        // executeScenarioList должен вернуть результат с количеством ошибок,
        // записать в сценарий количество ошибок и время последнего запуска,
        // сохранить обновленные сценаии
        AtExecutor atExecutor = new AtExecutor(new ScenarioRepositoryWrapper(scenarioRepository));
        return atExecutor.executeScenarioList(project, scenarioList);
    }

    @Override
    public Scenario save(Scenario scenario) {
        return scenarioRepository.save(scenario);
    }

    @Override
    public Scenario findOne(long scenarioId) {
        return scenarioRepository.findOne(scenarioId);
    }

    @Override
    public Step cloneStep(Step step) {
        if (step != null) {
            Scenario scenario = step.getScenario();
            Long maxSortStep = scenario.getSteps().stream().max(Comparator.comparing(Step::getSort)).map(Step::getSort).orElse(0L);
            Step newStep = step.clone();
            newStep.setSort(maxSortStep + 50);
            newStep.setScenario(scenario);
            return stepService.save(newStep);
        }
        return null;
    }

    @Override
    @Transactional
    public List<StepRo> updateScenarioListFromRo(Long scenarioId, List<StepRo> stepRoList) {
        Scenario scenario = findOne(scenarioId);
        if (scenario != null) {
            stepRoMapper.updateScenarioStepList(stepRoList, scenario);
            return stepRoMapper.convertStepRoListToStepList(save(scenario).getSteps());
        }
        throw new ResourceNotFoundException();
    }
}
