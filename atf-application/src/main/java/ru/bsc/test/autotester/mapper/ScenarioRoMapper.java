package ru.bsc.test.autotester.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.autotester.ro.ScenarioRo;

@Mapper(config = Config.class)
public abstract class ScenarioRoMapper {

    @Mappings({
            @Mapping(target = "stepList", ignore = true)
    })
    abstract void updateScenarioFromRo(ScenarioRo scenarioRo, @MappingTarget Scenario scenario);

    public Scenario updateScenario(ScenarioRo scenarioRo, @MappingTarget Scenario scenario) {
        updateScenarioFromRo(scenarioRo, scenario);

        return scenario;
    }
}
