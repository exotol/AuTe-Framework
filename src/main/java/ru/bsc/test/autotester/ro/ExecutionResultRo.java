package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExecutionResultRo implements AbstractRo {
    private List<ScenarioResultRo> scenarioResultList;
    private boolean finished;
}
