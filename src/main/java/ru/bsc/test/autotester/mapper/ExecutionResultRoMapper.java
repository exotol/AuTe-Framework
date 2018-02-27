package ru.bsc.test.autotester.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bsc.test.autotester.model.ExecutionResult;
import ru.bsc.test.autotester.ro.ExecutionResultRo;
import ru.bsc.test.autotester.ro.ScenarioResultRo;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = Config.class)
public abstract class ExecutionResultRoMapper {

    @Autowired
    private ProjectRoMapper projectRoMapper;
    @Autowired
    private StepRoMapper stepRoMapper;

    @Mappings({
            @Mapping(target = "finished", source = "finished"),
            @Mapping(target = "scenarioResultList", ignore = true),
    })

    /* default */
    abstract ExecutionResultRo executionResultToRo(ExecutionResult executionResult);

    public ExecutionResultRo map(ExecutionResult executionResult) {
        ExecutionResultRo executionResultRo = executionResultToRo(executionResult);
        if (executionResultRo == null) {
            return null;
        }

        if (executionResult != null) {
            List<ScenarioResultRo> scenarioResultList = executionResult.getScenarioStepResultListMap()
                    .entrySet()
                    .stream()
                    .map(scenarioListEntry -> new ScenarioResultRo()
                            .withScenario(projectRoMapper.scenarioToScenarioRo("", scenarioListEntry.getKey()))
                            .withStepResultRo(stepRoMapper.convertStepResultListToStepResultRo(scenarioListEntry.getValue()))
                            .withTotalSteps(scenarioListEntry
                                    .getKey()
                                    .getStepList()
                                    .stream()
                                    .filter(step -> !step.getDisabled())
                                    .mapToInt(value -> value.getStepParameterSetList() != null ? (value.getStepParameterSetList().size() == 0 ? 1 : value.getStepParameterSetList().size()) : 1).sum())
                    )
                    .collect(Collectors.toList());
            executionResultRo.setScenarioResultList(scenarioResultList);
        }

        return executionResultRo;
    }

}
