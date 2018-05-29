package ru.bsc.test.at.executor.step.executor.scriptengine;

/**
 * Script engine function execution result
 *
 * @author Pavel Golovkin
 */
public interface ScriptEngineProcedureResult {

	boolean isOk();

	String getException();
}
