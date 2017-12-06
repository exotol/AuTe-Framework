package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.ro.ProjectSearchRo;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.ro.StepRo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface ScenarioService {

    Scenario findOne(String projectCode, String scenarioPath) throws IOException;

    ScenarioRo updateScenarioFormRo(String projectCode, String scenarioPath, ScenarioRo scenarioRo) throws IOException;
    Step cloneStep(String projectCode, String scenarioPath, Step step) throws IOException;
    List<StepRo> updateStepListFromRo(String projectCode, String scenarioPath, List<StepRo> stepRoList) throws IOException;

    Map<Scenario, List<StepResult>> executeScenarioList(Project project, List<Scenario> scenarioList);

    StepRo addStepToScenario(String projectCode, String scenarioPath, StepRo stepRo) throws IOException;

    void deleteOne(String projectCode, String scenarioPath) throws IOException;

    List<ScenarioRo> findScenarioByStepRelativeUrl(String projectCode, ProjectSearchRo projectSearchRo);

    void save(String projectCode, String scenarioPath, Scenario scenario) throws IOException;

    List<Scenario> findAllByProject(String projectCode);

    ScenarioRo addScenarioToProject(String projectCode, ScenarioRo scenarioRo) throws IOException;
}
