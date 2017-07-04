package ru.bsc.test.autotester.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsc.test.at.executor.model.ScenarioGroup;
import ru.bsc.test.autotester.repository.ScenarioGroupRepository;
import ru.bsc.test.autotester.service.ScenarioGroupService;

import java.util.List;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
public class ScenarioGroupServiceImpl implements ScenarioGroupService {

    private final ScenarioGroupRepository scenarioGroupRepository;

    @Autowired
    public ScenarioGroupServiceImpl(ScenarioGroupRepository scenarioGroupRepository) {
        this.scenarioGroupRepository = scenarioGroupRepository;
    }

    @Override
    public ScenarioGroup save(ScenarioGroup scenario) {
        return scenarioGroupRepository.save(scenario);
    }

    @Override
    public ScenarioGroup findOne(long scenarioGroupId) {
        return scenarioGroupRepository.findOne(scenarioGroupId);
    }

    @Override
    public List<ScenarioGroup> save(List<ScenarioGroup> scenarioGroupList) {
        return scenarioGroupRepository.save(scenarioGroupList);
    }

}
