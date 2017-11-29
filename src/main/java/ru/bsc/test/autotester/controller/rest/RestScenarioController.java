package ru.bsc.test.autotester.controller.rest;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.exception.ResourceNotFoundException;
import ru.bsc.test.autotester.mapper.ProjectRoMapper;
import ru.bsc.test.autotester.mapper.StepRoMapper;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.autotester.ro.StepResultRo;
import ru.bsc.test.autotester.ro.StepRo;
import ru.bsc.test.autotester.service.ProjectService;
import ru.bsc.test.autotester.service.ScenarioService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */

@RestController
@RequestMapping("/rest/project/{projectCode}/scenarios")
public class RestScenarioController {

    private StepRoMapper stepRoMapper = Mappers.getMapper(StepRoMapper.class);
    private ProjectRoMapper projectRoMapper = Mappers.getMapper(ProjectRoMapper.class);
    private final ScenarioService scenarioService;
    private final ProjectService projectService;

    @Autowired
    public RestScenarioController(ScenarioService scenarioService, ProjectService projectService) {
        this.scenarioService = scenarioService;
        this.projectService = projectService;
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
    public ScenarioRo findOne(@PathVariable String projectCode, @PathVariable Long scenarioId) {
        Scenario scenario = scenarioService.findOne(scenarioId);
        if (scenario != null) {
            return projectRoMapper.scenarioToScenarioRo(projectCode, "", scenario);
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{scenarioId}", method = RequestMethod.PUT)
    public ScenarioRo saveOne(@PathVariable String projectCode, @PathVariable Long scenarioId, @RequestBody ScenarioRo scenarioRo) {
        scenarioRo = scenarioService.updateScenarioFormRo(projectCode, scenarioId, scenarioRo);
        if (scenarioRo != null) {
            return scenarioRo;
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{scenarioId}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable String projectCode, @PathVariable Long scenarioId) {
        scenarioService.deleteOne(projectCode, scenarioId);
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
    public List<StepResultRo> executing(@PathVariable String projectCode, @PathVariable Long scenarioId) {
        Scenario scenario = scenarioService.findOne(scenarioId);
        Project project = projectService.findOneByCode(projectCode);
        if (scenario != null) {
            Map<Scenario, List<StepResult>> scenarioResultMap =
                    scenarioService.executeScenarioList(project, Collections.singletonList(scenario));
            if (scenarioResultMap.containsKey(scenario)) {
                return stepRoMapper.convertStepResultListToStepResultRo(scenarioResultMap.get(scenario));
            }
        }
        throw new ResourceNotFoundException();
    }
}
