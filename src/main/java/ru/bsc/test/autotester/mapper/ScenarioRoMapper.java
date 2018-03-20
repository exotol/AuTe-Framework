package ru.bsc.test.autotester.mapper;

import org.mapstruct.*;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.autotester.ro.ScenarioRo;

@Mapper(config = Config.class)
public abstract class ScenarioRoMapper {

    @Mappings({
            @Mapping(target = "code", source = "code"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "stepList", ignore = true),
            @Mapping(target = "beforeScenarioIgnore", source = "beforeScenarioIgnore"),
            @Mapping(target = "afterScenarioIgnore", source = "afterScenarioIgnore"),
            @Mapping(target = "failed", source = "failed")
    })
    abstract void updateScenarioFromRo(ScenarioRo scenarioRo, @MappingTarget Scenario scenario);

    public Scenario updateScenario(ScenarioRo scenarioRo, @MappingTarget Scenario scenario) {
        updateScenarioFromRo(scenarioRo, scenario);

        return scenario;
    }
}
