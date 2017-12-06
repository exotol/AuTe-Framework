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

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */

@RestController
@RequestMapping("/rest/projects/{projectCode}/scenarios")
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

    @RequestMapping(value = "{scenarioPath}/steps", method = RequestMethod.GET)
    public List<StepRo> findSteps(@PathVariable String projectCode, @PathVariable String scenarioPath) throws IOException {
        Scenario scenario = scenarioService.findOne(projectCode, scenarioPath);
        if (scenario != null) {
            return stepRoMapper.convertStepListToStepRoList(scenario.getStepList());
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{scenarioPath}", method = RequestMethod.GET)
    public ScenarioRo findOne(@PathVariable String projectCode, @PathVariable String scenarioPath) throws IOException {
        Scenario scenario = scenarioService.findOne(projectCode, scenarioPath);
        if (scenario != null) {
            return projectRoMapper.scenarioToScenarioRo(projectCode, "", scenario);
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{scenarioId}", method = RequestMethod.PUT)
    public ScenarioRo saveOne(@PathVariable String projectCode, @PathVariable String scenarioPath, @RequestBody ScenarioRo scenarioRo) throws IOException {
        scenarioRo = scenarioService.updateScenarioFormRo(projectCode, scenarioPath, scenarioRo);
        if (scenarioRo != null) {
            return scenarioRo;
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{scenarioId}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable String projectCode, @PathVariable String scenarioPath) throws IOException {
        scenarioService.deleteOne(projectCode, scenarioPath);
    }

    @RequestMapping(value = "{scenarioId}/steps", method = RequestMethod.POST)
    public StepRo createNewStep(@PathVariable String projectCode, @PathVariable String scenarioPath, @RequestBody StepRo stepRo) throws IOException {
        stepRo = scenarioService.addStepToScenario(projectCode, scenarioPath, stepRo);
        if (stepRo != null) {
            return stepRo;
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{scenarioId}/steps", method = RequestMethod.PUT)
    public List<StepRo> saveStepList(@PathVariable String projectCode, @PathVariable String scenarioPath, @RequestBody List<StepRo> stepRoList) throws IOException {
        return scenarioService.updateStepListFromRo(projectCode, scenarioPath, stepRoList);
    }

    @RequestMapping(value = "{scenarioId}/exec", method = RequestMethod.POST)
    public List<StepResultRo> executing(@PathVariable String projectCode, @PathVariable String scenarioPath) throws IOException {
        Scenario scenario = scenarioService.findOne(projectCode, scenarioPath);
        Project project = projectService.findOne(projectCode);
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
