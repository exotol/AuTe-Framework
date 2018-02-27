package ru.bsc.test.autotester.model;

import lombok.Getter;
import lombok.Setter;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.StepResult;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ExecutionResult {
    private Map<Scenario, List<StepResult>> scenarioStepResultListMap;
    private boolean finished = false;
}
