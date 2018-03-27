package ru.bsc.test.at.executor.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class ScenarioVariableFromMqRequest implements Serializable, AbstractModel {

    private static final long serialVersionUID = 5716534964482145701L;

    private String sourceQueue;
    private String xpath;
    private String variableName;
}
