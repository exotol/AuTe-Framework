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

    Scenario findOne(long scenarioId);

    ScenarioRo updateScenarioFormRo(Long scenarioId, ScenarioRo scenarioRo);
    Step cloneStep(Step stepId);
    List<StepRo> updateStepListFromRo(Long scenarioId, List<StepRo> stepRoList);

    Map<Scenario, List<StepResult>> executeScenarioList(Project project, List<Scenario> scenarioList);

    StepRo addStepToScenario(Long scenarioId, StepRo stepRo);

    void deleteOne(Long project);
    List<StepRo> updateScenarioListFromRo(Long scenarioId, List<StepRo> stepRoList);

    List<ScenarioRo> findScenarioByStepRelativeUrl(Long projectId, ProjectSearchRo projectSearchRo);
}
