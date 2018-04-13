package ru.bsc.test.autotester.report;

import lombok.Getter;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.StepResult;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class AbstractReportGenerator {

    private final Map<Scenario, List<StepResult>> scenarioStepResultMap = new LinkedHashMap<>();

    public void add(Scenario scenario, List<StepResult> stepResultList) {
        scenarioStepResultMap.put(scenario, stepResultList);
    }

    public void clear() {
        scenarioStepResultMap.clear();
    }

    public abstract void generate(File directory) throws Exception;
}
