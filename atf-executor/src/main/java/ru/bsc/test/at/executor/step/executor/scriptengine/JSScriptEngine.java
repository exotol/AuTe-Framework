package ru.bsc.test.at.executor.step.executor.scriptengine;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @author Pavel Golovkin
 */
@Slf4j
public class JSScriptEngine implements ScriptEngine {

	private static final String STEP_STATUS = "stepStatus";
	private static final String RESPONSE = "response";
	private static final String SCENARIO_VARIABLES = "scenarioVariables";

	private static final ScriptEngineExecutionResult ERROR_RESULT = new ScriptEngineExecutionResult();
	static {
		ERROR_RESULT.setException("error executing script");
	}

	private static final ScriptEngineExecutionResult EMPTY_RESULT = new ScriptEngineExecutionResult();

	private javax.script.ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("js");

	public JSScriptEngine() {
		scriptEngine.put(STEP_STATUS, new ScriptEngineExecutionResult());
		scriptEngine.put(RESPONSE, null);
	}

	@Override
	public ScriptEngineProcedureResult executeProcedure(String script, Map<String, ? super Object> variables) {
		log.debug("Execute script procedure {} with variables {}", script, variables);
		scriptEngine.put(SCENARIO_VARIABLES, variables);
		ScriptEngineExecutionResult scriptEngineExecutionResult;
		try {
			scriptEngine.eval(script);
		} catch (ScriptException ex) {
			log.error("Error evaluating script procedure {}", script, ex);
			return ERROR_RESULT;
		}
		scriptEngineExecutionResult = (ScriptEngineExecutionResult) scriptEngine.get(STEP_STATUS);
		if (!scriptEngineExecutionResult.isOk()) {
			log.error("Error executing script procedure", scriptEngineExecutionResult.getException());
		}

		return scriptEngineExecutionResult;
	}

	@Override
	public ScriptEngineFunctionResult executeFunction(String script, Map<String, ? super Object> variables) {
		log.debug("Execute script function {} with variables {}", script, variables);
		ScriptEngineExecutionResult scriptEngineExecutionResult;
		scriptEngine.put(SCENARIO_VARIABLES, variables);
		try {
			Object result = scriptEngine.eval(script);
			if (result != null) {
				log.debug("Script execution result {}", result);
				scriptEngineExecutionResult = new ScriptEngineExecutionResult();
				scriptEngineExecutionResult.setResult(String.valueOf(result));
				return scriptEngineExecutionResult;
			}
			return EMPTY_RESULT;
		} catch (ScriptException ex) {
			log.error("Error evaluating script function {}", script, ex);
			return ERROR_RESULT;
		}
	}
}
