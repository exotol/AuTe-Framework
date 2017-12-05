package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.ro.ProjectSearchRo;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.ro.StepRo;

import java.util.List;
import java.util.Map;

/**
 * Created by sdoroshin on 03.03.2017.
 *
 */
public interface ScenarioService {

    Scenario findOne(String projectCode, String scenarioPath);

    ScenarioRo updateScenarioFormRo(String projectCode, String scenarioPath, ScenarioRo scenarioRo);
    Step cloneStep(String projectCode, String scenarioPath, Step step);
    List<StepRo> updateStepListFromRo(String projectCode, String scenarioPath, List<StepRo> stepRoList);

    Map<Scenario, List<StepResult>> executeScenarioList(Project project, List<Scenario> scenarioList);

    StepRo addStepToScenario(String projectCode, String scenarioPath, StepRo stepRo);

    void deleteOne(String projectCode, String scenarioPath);

    List<ScenarioRo> findScenarioByStepRelativeUrl(String projectCode, ProjectSearchRo projectSearchRo);
}
