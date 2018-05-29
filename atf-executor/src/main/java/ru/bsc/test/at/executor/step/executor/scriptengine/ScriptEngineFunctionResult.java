package ru.bsc.test.at.executor.step.executor.scriptengine;

/**
 * Script function execution result
 *
 * @author Pavel Golovkin
 */
public interface ScriptEngineFunctionResult {

	boolean isOk();

	String getResult();
}
