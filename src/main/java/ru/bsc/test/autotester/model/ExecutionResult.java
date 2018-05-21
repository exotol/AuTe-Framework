package ru.bsc.test.autotester.model;

import lombok.Getter;
import lombok.Setter;
import ru.bsc.test.at.executor.model.ScenarioResult;

import java.util.List;

@Getter
@Setter
public class ExecutionResult {
    private List<ScenarioResult> scenarioResults;
    private boolean finished = false;
}
