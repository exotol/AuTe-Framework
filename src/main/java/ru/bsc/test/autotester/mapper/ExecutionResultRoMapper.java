package ru.bsc.test.autotester.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.diff.Diff;
import ru.bsc.test.autotester.diff.DiffMatchPatch;
import ru.bsc.test.autotester.model.ExecutionResult;
import ru.bsc.test.autotester.ro.ExecutionResultRo;
import ru.bsc.test.autotester.ro.ScenarioResultRo;

import java.util.LinkedList;
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

        addDiffToResult(executionResultRo);
        return executionResultRo;
    }

    private void addDiffToResult(ExecutionResultRo executionResultRo) {
        executionResultRo.getScenarioResultList().forEach(rl -> rl.getStepResultList().forEach(stepResultRo -> {
            if (stepResultRo.getActual() == null || stepResultRo.getExpected() == null) {
                return;
            }

            DiffMatchPatch dmp = new DiffMatchPatch();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser parser = new JsonParser();
            try {
                JsonElement elA = parser.parse(stepResultRo.getActual());
                String actualJson = gson.toJson(elA);
                stepResultRo.setActual(actualJson);
            } catch (Exception e) {
            }

            try {
                JsonElement elE = parser.parse(stepResultRo.getExpected());
                String expectedJson = gson.toJson(elE);
                stepResultRo.setExpected(expectedJson);
            } catch (Exception e) {
            }

            LinkedList<Diff> diff = dmp.diff_main(stepResultRo.getExpected(), stepResultRo.getActual());
            stepResultRo.setDiff(diff);

        }));
    }


}
