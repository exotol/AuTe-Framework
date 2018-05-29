package ru.bsc.test.at.executor.step.executor.scriptengine;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class ScriptEngineExecutionResult implements ScriptEngineProcedureResult, ScriptEngineFunctionResult {
    private String exception;
    private String result;

    @Override
    public boolean isOk() {
        return StringUtils.isEmpty(exception);
    }
}