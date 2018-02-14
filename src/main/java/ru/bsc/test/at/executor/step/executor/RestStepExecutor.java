package ru.bsc.test.at.executor.step.executor;

import org.apache.commons.lang3.StringUtils;
import ru.bsc.test.at.executor.helper.HttpHelper;
import ru.bsc.test.at.executor.helper.ResponseHelper;
import ru.bsc.test.at.executor.model.FieldType;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.RequestBodyType;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepMode;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.model.StepStatus;
import ru.bsc.test.at.executor.wiremock.WireMockAdmin;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.sql.Connection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class RestStepExecutor extends AbstractStepExecutor {

    private final static int POLLING_RETRY_COUNT = 50;

    @Override
    public void execute(WireMockAdmin wireMockAdmin, Connection connection, Stand stand, HttpHelper httpHelper, Map<String, Object> scenarioVariables, String testId, Project project, Step step, StepResult stepResult, String projectPath) throws Exception {

        // 0. Установить ответы сервисов, которые будут использоваться в SoapUI для определения ответа
        setMockResponses(wireMockAdmin, project, testId, step.getMockServiceResponseList());

        // 1. Выполнить запрос БД и сохранить полученные значения
        executeSql(connection, step, scenarioVariables);
        stepResult.setSavedParameters(scenarioVariables.toString());

        // 1.1 Отправить сообщение в очередь
        sendMessageToQuery(project, step, scenarioVariables);

        // 2. Подстановка сохраненных параметров в строку запроса
        String requestUrl = stand.getServiceUrl() + insertSavedValuesToURL(step.getRelativeUrl(), scenarioVariables);
        stepResult.setRequestUrl(requestUrl);

        // 2.1 Подстановка сохраненных параметров в тело запроса
        String requestBody = insertSavedValues(step.getRequest(), scenarioVariables);

        // 2.2 Вычислить функции в теле запроса
        requestBody = evaluateExpressions(requestBody);
        stepResult.setRequestBody(requestBody);

        // 2.3 Подстановка переменных сценария в заголовки запроса
        String requestHeaders = insertSavedValues(step.getRequestHeaders(), scenarioVariables);

        // 2.4 Cyclic sending request, COM-84
        int numberRepetitions = calculateNumberRepetitions(step, scenarioVariables);
        for (int repetitionCounter = 0; repetitionCounter < numberRepetitions; repetitionCounter++) {

            // Polling
            int retryCounter = 0;

            ResponseHelper responseData;
            do {
                retryCounter++;
                // 3. Выполнить запрос
                if (step.getRequestBodyType() == null || RequestBodyType.JSON.equals(step.getRequestBodyType())) {
                    responseData = httpHelper.request(
                            step.getRequestMethod(),
                            requestUrl,
                            requestBody,
                            requestHeaders,
                            project.getTestIdHeaderName(),
                            testId);
                } else {
                    if (step.getFormDataList() == null) {
                        step.setFormDataList(Collections.emptyList());
                    }
                    stepResult.setRequestBody(
                            step.getFormDataList()
                                    .stream()
                                    .map(formData -> {
                                        String result = formData.getFieldName() + " = ";
                                        if (FieldType.TEXT.equals(formData.getFieldType()) || formData.getFieldType() == null) {
                                            result += formData.getValue();
                                        } else {
                                            result += (projectPath == null ? "" : projectPath) + formData.getFilePath();
                                        }
                                        return result;
                                    })
                                    .collect(Collectors.joining("\r\n")));
                    responseData = httpHelper.request(
                            step.getRequestMethod(),
                            projectPath,
                            requestUrl,
                            step.getMultipartFormData(),
                            step.getFormDataList(),
                            requestHeaders,
                            project.getTestIdHeaderName(),
                            testId);
                }

                // Выполнить скрипт
                if (StringUtils.isNotEmpty(step.getScript())) {
                    ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("js");
                    scriptEngine.put("stepStatus", new StepStatus());
                    scriptEngine.put("scenarioVariables", scenarioVariables);
                    scriptEngine.put("response", responseData);

                    scriptEngine.eval(step.getScript());

                    // Привести все переменные сценария к строковому типу
                    scenarioVariables.forEach((s, s2) -> scenarioVariables.replace(s , s2 != null ? String.valueOf((Object)s2) : null));

                    StepStatus stepStatus = (StepStatus) scriptEngine.get("stepStatus");
                    if (StringUtils.isNotEmpty(stepStatus.getException())) {
                        throw new Exception(stepStatus.getException());
                    }
                }

            } while (tryUsePolling(step, responseData.getContent()) && retryCounter <= POLLING_RETRY_COUNT);

            stepResult.setPollingRetryCount(retryCounter);
            stepResult.setActual(responseData.getContent());
            stepResult.setExpected(step.getExpectedResponse());

            // 4. Сохранить полученные значения
            saveValuesByJsonXPath(step, responseData.getContent(), scenarioVariables);

            stepResult.setSavedParameters(scenarioVariables.toString());

            // 4.1 Проверить сохраненные значения
            checkScenarioVariables(step, scenarioVariables);

            // 5. Подставить сохраненые значения в ожидаемый результат
            String expectedResponse = insertSavedValues(step.getExpectedResponse(), scenarioVariables);
            // 5.1. Расчитать выражения <f></f>
            expectedResponse = evaluateExpressions(expectedResponse);
            stepResult.setExpected(expectedResponse);

            // 6. Проверить код статуса ответа
            if ((step.getExpectedStatusCode() != null)
                    && (step.getExpectedStatusCode() != responseData.getStatusCode())) {
                throw new Exception("Expected status code: " + step.getExpectedStatusCode() + ". Actual status code: " + responseData.getStatusCode());

            }

            checkResponseBody(step, expectedResponse, responseData.getContent());
        }

        // 7. Прочитать, что тестируемый сервис отправлял в заглушку.
        parseMockRequests(project, step, wireMockAdmin, scenarioVariables, testId);
    }

    @Override
    public boolean support(Step step) {
        return step.getStepMode() == null || StepMode.REST.equals(step.getStepMode());
    }
}
