package ru.bsc.test.autotester.mapper;

import org.mapstruct.*;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.autotester.ro.ScenarioRo;

import java.util.List;
import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ScenarioRoMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "sort", ignore = true),
            @Mapping(target = "code", source = "code"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "scenarioGroup", source = "scenarioGroup"),
            @Mapping(target = "beforeScenario", ignore = true),
            @Mapping(target = "afterScenario", ignore = true),
            @Mapping(target = "stepList", ignore = true),
            @Mapping(target = "stepListYamlFile", ignore = true),
            @Mapping(target = "beforeScenarioIgnore", source = "beforeScenarioIgnore"),
            @Mapping(target = "afterScenarioIgnore", source = "afterScenarioIgnore")
    })
    abstract void updateScenarioFromRo(ScenarioRo scenarioRo, @MappingTarget Scenario scenario);

    public Scenario updateScenario(List<Scenario> scenarioList, ScenarioRo scenarioRo, @MappingTarget Scenario scenario) {
        updateScenarioFromRo(scenarioRo, scenario);

        if (scenarioRo.getBeforeScenarioId() != null) {
            scenario.setBeforeScenario(scenarioList.stream()
                    .filter(scenario1 -> Objects.equals(scenario1.getId(), scenarioRo.getBeforeScenarioId()))
                    .findAny()
                    .orElse(null));
        }

        if (scenarioRo.getAfterScenarioId() != null) {
            scenario.setAfterScenario(scenarioList.stream()
                    .filter(scenario1 -> Objects.equals(scenario1.getId(), scenarioRo.getAfterScenarioId()))
                    .findAny()
                    .orElse(null));
        }

        return scenario;
    }
}
