package ru.bsc.test.autotester.mapper;

import org.mapstruct.*;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.autotester.ro.ScenarioRo;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ScenarioRoMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "sort", ignore = true),
            @Mapping(target = "project", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "scenarioGroup", ignore = true),
            @Mapping(target = "lastRunAt", source = "lastRunAt"),
            @Mapping(target = "lastRunFailures", source = "lastRunFailures"),
            @Mapping(target = "beforeScenario", ignore = true),
            @Mapping(target = "afterScenario", ignore = true),
            @Mapping(target = "stepList", ignore = true),
            @Mapping(target = "stepListYamlFile", ignore = true),
            @Mapping(target = "beforeScenarioIgnore", source = "beforeScenarioIgnore"),
            @Mapping(target = "afterScenarioIgnore", source = "afterScenarioIgnore"),
            @Mapping(target = "stand", ignore = true)
    })
    abstract void updateScenarioFromRo(ScenarioRo scenarioRo, @MappingTarget Scenario scenario);

    public Scenario updateScenario(ScenarioRo scenarioRo, @MappingTarget Scenario scenario) {
        updateScenarioFromRo(scenarioRo, scenario);

        scenario.setScenarioGroup(scenarioRo.getScenarioGroup() == null || scenarioRo.getScenarioGroup().getId() == null ? null :
                scenario.getProject().getScenarioGroups().stream()
                        .filter(scenarioGroup -> Objects.equals(scenarioGroup.getId(), scenarioRo.getScenarioGroup().getId()))
                        .findAny()
                        .orElse(null));

        scenario.setBeforeScenario(scenario.getProject().getScenarioList().stream()
                .filter(scenario1 -> Objects.equals(scenario1.getId(), scenarioRo.getBeforeScenarioId()))
                .findAny()
                .orElse(null));

        scenario.setAfterScenario(scenario.getProject().getScenarioList().stream()
                .filter(scenario1 -> Objects.equals(scenario1.getId(), scenarioRo.getAfterScenarioId()))
                .findAny()
                .orElse(null));

        scenario.setStand(scenarioRo.getStand() == null || scenarioRo.getStand().getId() == null ? null :
                scenario.getProject().getStandList().stream()
                        .filter(stand -> Objects.equals(stand.getId(), scenarioRo.getStand().getId()))
                        .findAny()
                        .orElse(null)
        );

        return scenario;
    }
}
