package ru.bsc.test.autotester.service;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.ro.ProjectSearchRo;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.autotester.ro.StartScenarioInfoRo;
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

    Step cloneStep(String projectCode, String scenarioPath, String stepCode) throws IOException;

    List<StepRo> updateStepListFromRo(String projectCode, String scenarioPath, List<StepRo> stepRoList) throws IOException;

    StartScenarioInfoRo startScenarioExecutingList(Project project, List<Scenario> scenarioList);

    void stopExecuting(String executingUuid);

    List<String> getExecutingList();

    Map<Scenario, List<StepResult>> getResult(String executingUuid);

    StepRo addStepToScenario(String projectCode, String scenarioPath, StepRo stepRo) throws IOException;

    void deleteOne(String projectCode, String scenarioPath) throws IOException;

    List<ScenarioRo> findScenarioByStepRelativeUrl(String projectCode, ProjectSearchRo projectSearchRo);

    void save(String projectCode, String scenarioPath, Scenario scenario) throws IOException;

    StepRo updateStepFromRo(String projectCode, String scenarioPath, String stepCode, StepRo stepRo) throws IOException;

    List<Scenario> findAllByProject(String projectCode);

    ScenarioRo addScenarioToProject(String projectCode, ScenarioRo scenarioRo) throws IOException;
}
