package ru.bsc.test.autotester.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bsc.test.at.executor.model.Step;
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
                    .map(scenarioListEntry -> ScenarioResultRo.builder()
                            .scenario(projectRoMapper.scenarioToScenarioRo("", scenarioListEntry.getKey()))
                            .stepResultList(stepRoMapper.convertStepResultListToStepResultRo(scenarioListEntry.getValue()))
                            .totalSteps(scenarioListEntry
                                    .getKey()
                                    .getStepList()
                                    .stream()
                                    .filter(step -> !step.getDisabled())
                                    .map(Step::getStepParameterSetList)
                                    .mapToInt(list -> list != null ? (list.size() == 0 ? 1 : list.size()) : 1)
                                    .sum()
                            ).build()
                    )
                    .collect(Collectors.toList());
            executionResultRo.setScenarioResultList(scenarioResultList);
        }

        return executionResultRo;
    }

}
