package ru.bsc.test.at.executor.step.executor.scriptengine;

import java.util.Map;

/**
 * Interface for performing inner script written on programming language.
 *
 * @author Pavel Golovkin
 */
public interface ScriptEngine {

	/**
	 * Executes procedure with no return value. You can define is it Ok or Not using {@link ScriptEngineProcedureResult#isOk()}.
	 * @param script script to execute
	 * @param variables script's variables
	 * @return procedure execution result
	 */
	ScriptEngineProcedureResult executeProcedure(String script, Map<String, ? super Object> variables);

	/**
	 * Executes function with return value. And you can define was execution Ok or Not {@link ScriptEngineFunctionResult#isOk()}
	 * @param script script to execute
	 * @param variables script's variables
	 * @return script execution result in {@link ScriptEngineFunctionResult#getResult()}
	 */
	ScriptEngineFunctionResult executeFunction(String script, Map<String, ? super Object> variables);
}
