package ru.bsc.test.autotester.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.bsc.test.autotester.model.ExecutionResult;
import ru.bsc.test.autotester.ro.ExecutionResultRo;
import ru.bsc.test.autotester.ro.ScenarioResultRo;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ExecutionResultRoMapper {

    private ProjectRoMapper projectRoMapper = Mappers.getMapper(ProjectRoMapper.class);
    private StepRoMapper stepRoMapper = Mappers.getMapper(StepRoMapper.class);

    @Mappings({
            @Mapping(target = "finished", source = "finished"),
            @Mapping(target = "scenarioResultList", ignore = true),
    })
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
