package ru.bsc.test.autotester.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.service.AtExecutor;

import ru.bsc.test.autotester.exception.ResourceNotFoundException;
import ru.bsc.test.autotester.mapper.ProjectRoMapper;
import ru.bsc.test.autotester.mapper.ScenarioRoMapper;
import ru.bsc.test.autotester.mapper.StepRoMapper;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.repository.ScenarioRepository;
import ru.bsc.test.autotester.ro.ProjectSearchRo;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.autotester.ro.StepRo;
import ru.bsc.test.autotester.service.ProjectService;
import ru.bsc.test.autotester.service.ScenarioService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
public class ScenarioServiceImpl implements ScenarioService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ScenarioServiceImpl.class);

    private final StepRoMapper stepRoMapper = Mappers.getMapper(StepRoMapper.class);
    private ScenarioRoMapper scenarioRoMapper = Mappers.getMapper(ScenarioRoMapper.class);
    private final ProjectRoMapper projectRoMapper = Mappers.getMapper(ProjectRoMapper.class);

    private final ScenarioRepository scenarioRepository;
    private final ProjectService projectService;
    private final EnvironmentProperties environmentProperties;

    @Autowired
    public ScenarioServiceImpl(ScenarioRepository scenarioRepository, ProjectService projectService, EnvironmentProperties environmentProperties) {
        this.scenarioRepository = scenarioRepository;
        this.projectService = projectService;
        this.environmentProperties = environmentProperties;
    }

    private Map<String, Map<Scenario, List<StepResult>>> runningScriptsMap = new HashMap<>();

    @Override
    public Map<Scenario, List<StepResult>> executeScenarioList(Project project, List<Scenario> scenarioList) {
        AtExecutor atExecutor = new AtExecutor();
        atExecutor.setProjectPath(environmentProperties.getProjectsDirectoryPath() + "/" + project.getCode() + "/");
        Map<Scenario, List<StepResult>> resultMap = new HashMap<>();
        String runningUuid = UUID.randomUUID().toString();
        runningScriptsMap.put(runningUuid, resultMap);
        Map<Scenario, List<StepResult>> map = atExecutor.executeScenarioList(project, scenarioList);
        synchronized (projectService) {
            map.forEach((scenario, stepResults) -> {
                String scenarioPath = (StringUtils.isEmpty(scenario.getScenarioGroup()) ? "" : scenario.getScenarioGroup() + "/") + scenario.getCode();
                try {
                    Scenario scenarioToUpdate = scenarioRepository.findScenario(project.getCode(), scenarioPath);
                    scenarioToUpdate.setFailed(
                            stepResults
                                    .stream()
                                    .filter(stepResult -> StepResult.RESULT_FAIL.equals(stepResult.getResult()))
                                    .count() > 0
                    );
                    scenarioRepository.saveScenario(project.getCode(), scenarioPath, scenarioToUpdate);
                } catch (IOException e) {
                    LOGGER.error("", e);
                }
            });
        }
        return map;
    }

    @Override
    public StepRo addStepToScenario(String projectCode, String scenarioPath, StepRo stepRo) throws IOException {
        synchronized (projectService) {
            Scenario scenario = findOne(projectCode, scenarioPath);
            if (scenario != null) {
                Step newStep = stepRoMapper.convertStepRoToStep(stepRo);
                scenario.getStepList().add(newStep);
                scenarioRepository.saveScenario(projectCode, scenarioPath, scenario);
                return stepRoMapper.stepToStepRo(newStep);
            }
            return null;
        }
    }

    @Override
    public void deleteOne(String projectCode, String scenarioPath) throws IOException {
        synchronized (projectService) {
            scenarioRepository.delete(projectCode, scenarioPath);
        }
    }

    @Override
    public ScenarioRo updateScenarioFormRo(String projectCode, String scenarioPath, ScenarioRo scenarioRo) throws IOException {
        synchronized (projectService) {
            Scenario scenario = scenarioRepository.findScenario(projectCode, scenarioPath);
            if (scenario != null) {
                scenario = scenarioRoMapper.updateScenario(scenarioRo, scenario);
                scenario = scenarioRepository.saveScenario(projectCode, scenarioPath, scenario);
                return projectRoMapper.scenarioToScenarioRo(projectCode, scenario);
            }
            return null;
        }
    }

    @Override
    public Scenario findOne(String projectCode, String scenarioPath) throws IOException {
        synchronized (projectService) {
            return scenarioRepository.findScenario(projectCode, scenarioPath);
        }
    }

    @Override
    public Step cloneStep(String projectCode, String scenarioPath, String stepCode) throws IOException {
        synchronized (projectService) {
            Scenario scenario = scenarioRepository.findScenario(projectCode, scenarioPath);
            Step existsStep = scenario.getStepList().stream()
                    .filter(step -> Objects.equals(step.getCode(), stepCode))
                    .findAny()
                    .orElse(null);
            if (existsStep != null) {
                Step newStep = existsStep.copy();
                scenario.getStepList().add(newStep);
                return newStep;
            }
            return null;
        }
    }

    @Override
    public List<StepRo> updateStepListFromRo(String projectCode, String scenarioPath, List<StepRo> stepRoList) throws IOException {
        synchronized (projectService) {
            Scenario scenario = findOne(projectCode, scenarioPath);
            if (scenario != null) {
                stepRoMapper.updateScenarioStepList(stepRoList, scenario);
                scenario = scenarioRepository.saveScenario(projectCode, scenarioPath, scenario);
                return stepRoMapper.convertStepListToStepRoList(scenario.getStepList());
            }
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public List<ScenarioRo> findScenarioByStepRelativeUrl(String projectCode, ProjectSearchRo projectSearchRo) {
        List<Scenario> scenarios = new ArrayList<>();
        if (!StringUtils.isEmpty(projectSearchRo.getRelativeUrl())) {
            scenarios = new ArrayList<>(scenarioRepository.findByRelativeUrl(projectCode, projectSearchRo.getRelativeUrl()));
        }
        return projectRoMapper.convertScenarioListToScenarioRoList(scenarios);
    }

    @Override
    public void save(String projectCode, String scenarioPath, Scenario scenario) throws IOException {
        scenarioRepository.saveScenario(projectCode, scenarioPath, scenario);
    }

    @Override
    public StepRo updateStepFromRo(String projectCode, String scenarioPath, String stepCode, StepRo stepRo) throws IOException {
        synchronized (projectService) {
            Scenario scenario = scenarioRepository.findScenario(projectCode, scenarioPath);
            Step existsStep = scenario.getStepList().stream()
                    .filter(step -> Objects.equals(step.getCode(), stepCode))
                    .findAny()
                    .orElse(null);
            stepRoMapper.updateStep(stepRo, existsStep);
            scenarioRepository.saveScenario(projectCode, scenarioPath, scenario);
            // existsStep = scenarioRepository.saveStep(projectCode, scenarioPath, stepCode, existsStep);
            return stepRoMapper.stepToStepRo(existsStep);
        }
    }

    @Override
    public List<Scenario> findAllByProject(String projectCode) {
        Project project = projectService.findOne(projectCode);
        return project.getScenarioList();
    }

    @Override
    public ScenarioRo addScenarioToProject(String projectCode, ScenarioRo scenarioRo) throws IOException {
        synchronized (this) {
            Scenario newScenario = scenarioRoMapper.updateScenario(scenarioRo, new Scenario());
            newScenario = scenarioRepository.saveScenario(projectCode, null, newScenario);
            return projectRoMapper.scenarioToScenarioRo(projectCode, newScenario);
        }
    }
}
