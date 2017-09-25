package ru.bsc.test.autotester.mapper;

import org.mapstruct.*;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.ro.ScenarioRo;
import ru.bsc.test.autotester.ro.StepResultRo;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ScenarioRoMapper {

    /*
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "scenarioGroup", source = "scenarioGroup"),
            @Mapping(target = "stepResults", source = "stepResults"),
            @Mapping(target = "lastRunAt", source = "lastRunAt"),
            @Mapping(target = "lastRunFailures", source = "lastRunFailures"),
            @Mapping(target = "beforeScenario", source = "beforeScenario"),
            @Mapping(target = "afterScenario", source = "afterScenario"),
            @Mapping(target = "steps", ignore = true),
            @Mapping(target = "beforeScenarioIgnore", source = "beforeScenarioIgnore"),
            @Mapping(target = "afterScenarioIgnore", source = "afterScenarioIgnore"),
            @Mapping(target = "stand", source = "stand")
    })
    abstract ScenarioRo convertScenarioToScenarioRo(Scenario scenario);
    */

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "project", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "scenarioGroup", ignore = true),
            @Mapping(target = "stepResults", ignore = true),
            @Mapping(target = "lastRunAt", source = "lastRunAt"),
            @Mapping(target = "lastRunFailures", source = "lastRunFailures"),
            @Mapping(target = "beforeScenario", ignore = true),
            @Mapping(target = "afterScenario", ignore = true),
            @Mapping(target = "steps", ignore = true),
            @Mapping(target = "beforeScenarioIgnore", source = "beforeScenarioIgnore"),
            @Mapping(target = "afterScenarioIgnore", source = "afterScenarioIgnore"),
            @Mapping(target = "stand", ignore = true)
    })
    abstract void updateScenarioFromRo(ScenarioRo scenarioRo, @MappingTarget Scenario scenario);

    public void updateScenario(ScenarioRo scenarioRo, @MappingTarget Scenario scenario) {
        updateScenarioFromRo(scenarioRo, scenario);


    }

    /*
    @Mappings({
            @Mapping(target = "step", source = "step"),
            @Mapping(target = "result", source = "result"),
            @Mapping(target = "details", source = "result"),
            @Mapping(target = "expected", source = "result"),
            @Mapping(target = "actual", source = "result"),
            @Mapping(target = "requestUrl", source = "result"),
            @Mapping(target = "requestBody", source = "result"),
            @Mapping(target = "pollingRetryCount", source = "result"),
            @Mapping(target = "savedParameters", source = "result"),
            @Mapping(target = "description", source = "result")
    })
    abstract StepResultRo convertStepResultToStepResultRo(StepResult stepResult);

    abstract List<StepResultRo> convertResultListToResultListRo(List<StepResult> stepResultList);
    */
}
