package ru.bsc.test.autotester.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import ru.bsc.test.autotester.repository.ScenarioRepository;
import ru.bsc.test.autotester.ro.ProjectSearchRo;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.autotester.ro.StepRo;
import ru.bsc.test.autotester.service.ProjectService;
import ru.bsc.test.autotester.service.ScenarioService;
import ru.bsc.test.autotester.service.StepService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
public class ScenarioServiceImpl implements ScenarioService {

    private final StepRoMapper stepRoMapper = Mappers.getMapper(StepRoMapper.class);
    private ScenarioRoMapper scenarioRoMapper = Mappers.getMapper(ScenarioRoMapper.class);
    private final ProjectRoMapper projectRoMapper = Mappers.getMapper(ProjectRoMapper.class);

    private final ScenarioRepository scenarioRepository;
    private final StepService stepService;
    private final ProjectService projectService;

    @Value("${projects.path:}")
    private String projectsPath;

    @Autowired
    public ScenarioServiceImpl(ScenarioRepository scenarioRepository, StepService stepService, ProjectService projectService) {
        this.scenarioRepository = scenarioRepository;
        this.stepService = stepService;
        this.projectService = projectService;
    }

    @Override
    public Map<Scenario, List<StepResult>> executeScenarioList(Project project, List<Scenario> scenarioList) {
        AtExecutor atExecutor = new AtExecutor();
        atExecutor.setProjectPath(projectsPath);
        return atExecutor.executeScenarioList(project, scenarioList);
    }

    @Override
    public StepRo addStepToScenario(String projectCode, String scenarioPath, StepRo stepRo) {
        synchronized (projectService) {
            Scenario scenario = findOne(projectCode, scenarioPath);
            if (scenario != null) {
                Step newStep = new Step();
                scenario.getStepList().add(newStep);

                stepRoMapper.updateStep(stepRo, newStep);

                scenarioRepository.saveScenario(projectCode, scenarioPath, scenario);
                return stepRoMapper.stepToStepRo(newStep);
            }
            return null;
        }
    }

    @Override
    public void deleteOne(String projectCode, String scenarioPath) {
        synchronized (projectService) {
            scenarioRepository.delete(projectCode, scenarioPath);
            /*
            List<Project> projectList = projectService.findAll();
            Project project = projectList.stream().filter(p -> Objects.equals(p.getCode(), projectCode)).findAny().orElse(null);
            if (project != null) {
                Scenario scenario = findScenarioByPath(scenarioPath, project.getScenarioList());
                if (scenario != null) {

                    project.setScenarioList(
                            project
                                    .getScenarioList()
                                    .stream()
                                    .filter(scenario1 -> !Objects.equals(scenario1.getId(), scenario.getId()))
                                    .collect(Collectors.toList())
                    );
                    scenarioRepository.saveScenario(scenario, projectList);

                } else {
                    throw new ResourceNotFoundException();
                }
            } else {
                throw new ResourceNotFoundException();
            }
            */
        }
    }

    @Override
    public ScenarioRo updateScenarioFormRo(String projectCode, String scenarioPath, ScenarioRo scenarioRo) {
        synchronized (projectService) {
            List<Project> projectList = projectService.findAll();
            Project project = projectList.stream().filter(project1 -> Objects.equals(project1.getCode(), projectCode)).findAny().orElse(null);
            if (project != null) {
                Scenario scenario = findOne(projectCode, scenarioPath);
                if (scenario != null) {
                    scenarioRoMapper.updateScenario(scenarioRo, scenario);
                    scenario = scenarioRepository.saveScenario(projectCode, scenarioPath, scenario);
                    return projectRoMapper.scenarioToScenarioRo(project.getCode(), project.getName(), scenario);
                }
            }
            return null;
        }
    }

    @Override
    public Scenario findOne(String projectCode, String scenarioPath) {
        synchronized (projectService) {
            return scenarioRepository.findScenario(projectCode, scenarioPath);
        }
    }

    @Override
    public Step cloneStep(String projectCode, String scenarioPath, Step step) {
        synchronized (projectService) {
            List<Project> projectList = projectService.findAll();
            Project project = projectList.stream().filter(p -> Objects.equals(p.getCode(), projectCode)).findAny().orElse(null);
            if (project != null) {
                Scenario scenario = findOne(projectCode, scenarioPath);
                if (scenario != null) {
                    Long maxSortStep = scenario.getStepList().stream().max(Comparator.comparing(Step::getSort)).map(Step::getSort).orElse(0L);
                    Step newStep = step.copy();
                    newStep.setSort(maxSortStep + 50);
                    scenario.getStepList().add(newStep);
                    return stepService.save(newStep, projectList);
                }
            }
            return null;
        }
    }

    @Override
    public List<StepRo> updateStepListFromRo(String projectCode, String scenarioPath, List<StepRo> stepRoList) {
        synchronized (projectService) {
            Scenario scenario = findOne(projectCode, scenarioPath);
            if (scenario != null) {
                stepRoMapper.updateScenarioStepList(stepRoList, scenario);
                scenario = scenarioRepository.saveScenario(projectCode, scenarioPath, scenario);
                return stepRoMapper.convertStepRoListToStepList(scenario.getStepList());
            }
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public List<ScenarioRo> findScenarioByStepRelativeUrl(String projectCode, ProjectSearchRo projectSearchRo) {
        List<Scenario> scenarios = new ArrayList<>();
        if (!StringUtils.isEmpty(projectSearchRo.getRelativeUrl())) {
            scenarios = new ArrayList<>(scenarioRepository.findByRelativeUrl(projectCode, "%" + projectSearchRo.getRelativeUrl() + "%"));
        }
        return projectRoMapper.convertScenarioListToScenarioRoList(scenarios);
    }
}
