package ru.bsc.test.autotester.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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
import ru.bsc.test.autotester.model.ExecutionResult;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.report.AbstractReportGenerator;
import ru.bsc.test.autotester.report.AllureReportGenerator;
import ru.bsc.test.autotester.repository.ScenarioRepository;
import ru.bsc.test.autotester.ro.ProjectSearchRo;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.autotester.ro.StartScenarioInfoRo;
import ru.bsc.test.autotester.ro.StepRo;
import ru.bsc.test.autotester.service.ProjectService;
import ru.bsc.test.autotester.service.ScenarioService;
import ru.bsc.test.autotester.utils.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.zip.ZipOutputStream;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
@Slf4j
public class ScenarioServiceImpl implements ScenarioService {
    private final StepRoMapper stepRoMapper;
    private final ScenarioRoMapper scenarioRoMapper;
    private final ProjectRoMapper projectRoMapper;

    private final ScenarioRepository scenarioRepository;
    private final ProjectService projectService;
    private final EnvironmentProperties environmentProperties;

    @Autowired
    public ScenarioServiceImpl(
            ScenarioRepository scenarioRepository,
            ProjectService projectService,
            EnvironmentProperties environmentProperties,
            StepRoMapper stepRoMapper,
            ScenarioRoMapper scenarioRoMapper,
            ProjectRoMapper projectRoMapper
    ) {
        this.scenarioRepository = scenarioRepository;
        this.projectService = projectService;
        this.environmentProperties = environmentProperties;
        this.stepRoMapper = stepRoMapper;
        this.scenarioRoMapper = scenarioRoMapper;
        this.projectRoMapper = projectRoMapper;
    }

    private final ConcurrentMap<String, ExecutionResult> runningScriptsMap = new ConcurrentHashMap<>();
    private final Set<String> stopExecutingSet = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    @Override
    public StartScenarioInfoRo startScenarioExecutingList(Project project, List<Scenario> scenarioList) {
        loadBeforeAndAfterScenarios(project, scenarioList);
        StartScenarioInfoRo startScenarioInfoRo = new StartScenarioInfoRo();
        AtExecutor atExecutor = new AtExecutor();
        atExecutor.setProjectPath(environmentProperties.getProjectsDirectoryPath() + "/" + project.getCode() + "/");
        ExecutionResult executionResult = new ExecutionResult();
        Map<Scenario, List<StepResult>> resultMap = new HashMap<>();
        executionResult.setScenarioStepResultListMap(resultMap);
        final String runningUuid = UUID.randomUUID().toString();
        startScenarioInfoRo.setRunningUuid(runningUuid);
        runningScriptsMap.put(runningUuid, executionResult);

        new Thread(() -> atExecutor.executeScenarioList(project, scenarioList,
                resultMap,
                () -> {
                    boolean stop = stopExecutingSet.contains(runningUuid);
                    if (stop) {
                        stopExecutingSet.remove(runningUuid);
                    }
                    return stop;
                },
                scenarioResultListMap -> {
                    executionResult.setFinished(true);
                    synchronized (projectService) {
                        scenarioResultListMap.forEach((scenario, stepResults) -> {
                            String scenarioPath = (StringUtils.isEmpty(scenario.getScenarioGroup()) ?
                                                   "" :
                                                   scenario.getScenarioGroup() + "/") + scenario.getCode();
                            try {
                                Scenario scenarioToUpdate = scenarioRepository.findScenario(
                                        project.getCode(),
                                        scenarioPath
                                );
                                scenarioToUpdate.setFailed(
                                        stepResults
                                                .stream()
                                                .anyMatch(stepResult ->
                                                        StepResult.RESULT_FAIL.equals(stepResult.getResult()))

                                );
                                scenarioRepository.saveScenario(
                                        project.getCode(),
                                        scenarioPath,
                                        scenarioToUpdate,
                                        false
                                );
                            } catch (IOException e) {
                                log.error("", e);
                            }
                        });
                    }
                }
        )).start();
        return startScenarioInfoRo;
    }

    @Override
    public void stopExecuting(String executingUuid) {
        stopExecutingSet.add(executingUuid);
    }

    @Override
    public List<String> getExecutingList() {
        return new LinkedList<>(runningScriptsMap.keySet());
    }

    @Override
    public ExecutionResult getResult(String executingUuid) {
        return runningScriptsMap.get(executingUuid);
    }

    @Override
    public void getReport(String executingUuid, ZipOutputStream outputStream) throws Exception {
        getReportList(Collections.singletonList(executingUuid), outputStream);
    }

    @Override
    public void getReportList(List<String> executionUuidList, ZipOutputStream zipOutputStream) throws Exception {
        AbstractReportGenerator reportGenerator = new AllureReportGenerator();
        int[] reportCounter = { 0 };
        runningScriptsMap.forEach((s, executionResult) -> {
            if (executionUuidList.contains(s)) {
                executionResult.getScenarioStepResultListMap().forEach(reportGenerator::add);
                reportCounter[0]++;
            }
        });
        if (reportCounter[0] == 0) {
            throw new ResourceNotFoundException();
        }

        String tmpRandom = UUID.randomUUID().toString();
        File tmpDirectory = new File("." + File.separator + "tmp" + File.separator + tmpRandom);
        if (tmpDirectory.mkdirs()) {
            reportGenerator.generate(tmpDirectory);

            FileUtils.deleteDirectory(new File(tmpDirectory, "results-directory"));
            ZipUtils.pack(tmpDirectory, zipOutputStream);
        } else {
            throw new FileAlreadyExistsException(tmpDirectory.getAbsolutePath());
        }
    }

    @Override
    public StepRo addStepToScenario(String projectCode, String scenarioPath, StepRo stepRo) throws IOException {
        synchronized (projectService) {
            Scenario scenario = findOne(projectCode, scenarioPath);
            if (scenario != null) {
                Step newStep = stepRoMapper.convertStepRoToStep(stepRo);
                scenario.getStepList().add(newStep);
                scenarioRepository.saveScenario(projectCode, scenarioPath, scenario, false);
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
                scenario = scenarioRepository.saveScenario(projectCode, scenarioPath, scenario, true);
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
                scenario = scenarioRepository.saveScenario(projectCode, scenarioPath, scenario, false);
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
    public StepRo updateStepFromRo(String projectCode, String scenarioPath, String stepCode, StepRo stepRo) throws IOException {
        synchronized (projectService) {
            Scenario scenario = scenarioRepository.findScenario(projectCode, scenarioPath);
            Step existsStep = scenario.getStepList().stream()
                    .filter(step -> Objects.equals(step.getCode(), stepCode))
                    .findAny()
                    .orElse(null);
            stepRoMapper.updateStep(stepRo, existsStep);
            scenarioRepository.saveScenario(projectCode, scenarioPath, scenario, false);
            return stepRoMapper.stepToStepRo(existsStep);
        }
    }

    @Override
    public List<Scenario> findAllByProject(String projectCode) {
        return scenarioRepository.findScenarios(projectCode);
    }

    @Override
    public ScenarioRo addScenarioToProject(String projectCode, ScenarioRo scenarioRo) throws IOException {
        synchronized (this) {
            Scenario newScenario = scenarioRoMapper.updateScenario(scenarioRo, new Scenario());
            newScenario = scenarioRepository.saveScenario(projectCode, null, newScenario, false);
            return projectRoMapper.scenarioToScenarioRo(projectCode, newScenario);
        }
    }

    private void loadBeforeAndAfterScenarios(Project project, List<Scenario> scenarioList) {
        for (Scenario scenario : scenarioList) {
            try {
                if (!scenario.getBeforeScenarioIgnore() && project.getBeforeScenarioPath() != null) {
                    project.getScenarioList().add(scenarioRepository.findScenario(
                            project.getCode(),
                            project.getBeforeScenarioPath()
                    ));
                }
            } catch (IOException e) {
                log.error("Error while loading before scenario", e);
            }
            try {
                if (!scenario.getAfterScenarioIgnore() && project.getAfterScenarioPath() != null) {
                    project.getScenarioList().add(scenarioRepository.findScenario(
                            project.getCode(),
                            project.getAfterScenarioPath()
                    ));
                }
            } catch (IOException e) {
                log.error("Error while loading after scenario", e);
            }
        }
    }
}
