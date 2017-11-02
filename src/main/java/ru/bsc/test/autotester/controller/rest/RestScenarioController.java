package ru.bsc.test.autotester.controller.rest;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.exception.ResourceNotFoundException;
import ru.bsc.test.autotester.mapper.ProjectRoMapper;
import ru.bsc.test.autotester.mapper.StepRoMapper;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.autotester.ro.StepResultRo;
import ru.bsc.test.autotester.ro.StepRo;
import ru.bsc.test.autotester.service.ScenarioService;

import java.util.*;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */

@RestController
@RequestMapping("/rest/scenarios")
public class RestScenarioController {

    private StepRoMapper stepRoMapper = Mappers.getMapper(StepRoMapper.class);
    private ProjectRoMapper projectRoMapper = Mappers.getMapper(ProjectRoMapper.class);
    private final ScenarioService scenarioService;

    @Autowired
    public RestScenarioController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @RequestMapping(value = "{scenarioId}/steps", method = RequestMethod.GET)
    public List<StepRo> findSteps(@PathVariable Long scenarioId) {
        Scenario scenario = scenarioService.findOne(scenarioId);
        if (scenario != null) {
            return stepRoMapper.convertStepRoListToStepList(scenario.getStepList());
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{scenarioId}", method = RequestMethod.GET)
    public ScenarioRo findOne(@PathVariable Long scenarioId) {
        Scenario scenario = scenarioService.findOne(scenarioId);
        if (scenario != null) {
            return projectRoMapper.scenarioToScenarioRo(scenario);
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{scenarioId}", method = RequestMethod.PUT)
    public ScenarioRo saveOne(@PathVariable Long scenarioId, @RequestBody ScenarioRo scenarioRo) {
        scenarioRo = scenarioService.updateScenarioFormRo(scenarioId, scenarioRo);
        if (scenarioRo != null) {
            return scenarioRo;
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{scenarioId}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable Long scenarioId) {
        scenarioService.deleteOne(scenarioId);
    }

    @RequestMapping(value = "{scenarioId}/steps", method = RequestMethod.POST)
    public StepRo createNewStep(@PathVariable Long scenarioId, @RequestBody StepRo stepRo) {
        stepRo = scenarioService.addStepToScenario(scenarioId, stepRo);
        if (stepRo != null) {
            return stepRo;
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{scenarioId}/steps", method = RequestMethod.PUT)
    public List<StepRo> saveStepList(@PathVariable Long scenarioId, @RequestBody List<StepRo> stepRoList) {
        return scenarioService.updateStepListFromRo(scenarioId, stepRoList);
    }

    @RequestMapping(value = "{scenarioId}/exec", method = RequestMethod.POST)
    public List<StepResultRo> executing(@PathVariable Long scenarioId) {
        Scenario scenario = scenarioService.findOne(scenarioId);
        if (scenario != null) {
            Map<Scenario, List<StepResult>> scenarioResultMap =
                    scenarioService.executeScenarioList(scenario.getProject(), Collections.singletonList(scenario));
            if (scenarioResultMap.containsKey(scenario)) {
                return stepRoMapper.convertStepResultListToStepResultRo(scenarioResultMap.get(scenario));
            }
        }
        throw new ResourceNotFoundException();
    }
}
