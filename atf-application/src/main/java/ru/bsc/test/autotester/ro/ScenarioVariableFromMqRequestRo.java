package ru.bsc.test.autotester.ro;

import lombok.Data;

@Data
public class ScenarioVariableFromMqRequestRo {
    private String sourceQueue;
    private String xpath;
    private String variableName;
}
