package ru.bsc.test.autotester.controller.rest;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.exception.ResourceNotFoundException;
import ru.bsc.test.autotester.mapper.ProjectRoMapper;
import ru.bsc.test.autotester.mapper.ScenarioRoMapper;
import ru.bsc.test.autotester.mapper.StepRoMapper;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.autotester.ro.StepResultRo;
import ru.bsc.test.autotester.ro.StepRo;
import ru.bsc.test.autotester.service.ProjectService;
import ru.bsc.test.autotester.service.ScenarioService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */

@RestController
@RequestMapping("/rest/scenarios")
public class RestScenarioController {

    private StepRoMapper stepRoMapper = Mappers.getMapper(StepRoMapper.class);
    private ScenarioRoMapper scenarioRoMapper = Mappers.getMapper(ScenarioRoMapper.class);
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
            return stepRoMapper.convertStepRoListToStepList(scenario.getSteps());
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
        Scenario scenario = scenarioService.findOne(scenarioId);
        if (scenario != null) {
            scenarioRoMapper.updateScenario(scenarioRo, scenario);
            scenario = scenarioService.save(scenario);
            return projectRoMapper.scenarioToScenarioRo(scenario);
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{scenarioId}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable Long scenarioId) {
        Scenario scenario = scenarioService.findOne(scenarioId);
        if (scenario != null) {
            Project project = scenario.getProject();
            project.setScenarios(project.getScenarios().stream().filter(scenario1 -> !Objects.equals(scenario1.getId(), scenario.getId())).collect(Collectors.toList()));
            projectService.save(project);
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{scenarioId}/steps", method = RequestMethod.POST)
    public StepRo createNewStep(@PathVariable Long scenarioId, @RequestBody StepRo stepRo) {
        Scenario scenario = scenarioService.findOne(scenarioId);
        if (scenario != null) {

            Step newStep = new Step();
            newStep.setScenario(scenario);
            scenario.getSteps().add(newStep);
            stepRoMapper.updateStep(stepRo, newStep);

            scenario = scenarioService.save(scenario);

            return stepRoMapper.stepToStepRo(scenario.getSteps().stream().max(Comparator.comparingLong(Step::getId)).orElse(newStep));
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{scenarioId}/steps", method = RequestMethod.PUT)
    public List<StepRo> saveStepList(@PathVariable Long scenarioId, @RequestBody List<StepRo> stepRoList) {
        return scenarioService.updateScenarioListFromRo(scenarioId, stepRoList);
    }

    @RequestMapping(value = "{scenarioId}/exec", method = RequestMethod.POST)
    public List<StepResultRo> executing(@PathVariable Long scenarioId) {
        Scenario scenario = scenarioService.findOne(scenarioId);
        if (scenario != null) {

            List<Scenario> scenarioExecuteList = Collections.singletonList(scenario);
            List<Scenario> scenarioResultList = scenarioService.executeScenarioList(scenario.getProject(), scenarioExecuteList);

            if (!scenarioResultList.isEmpty()) {
                Scenario executedScenario = scenarioResultList.get(0);

                return stepRoMapper.convertStepResultListToStepResultRo(executedScenario.getStepResults());
            }
        }
        throw new ResourceNotFoundException();
    }
}
