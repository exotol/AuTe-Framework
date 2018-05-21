package ru.bsc.test.at.executor.step.executor;

import org.apache.commons.lang3.StringUtils;
import ru.bsc.test.at.executor.ei.wiremock.WireMockAdmin;
import ru.bsc.test.at.executor.helper.client.api.ClientResponse;
import ru.bsc.test.at.executor.helper.client.impl.http.HttpClient;
import ru.bsc.test.at.executor.helper.client.impl.mq.ClientMQRequest;
import ru.bsc.test.at.executor.helper.client.impl.mq.MqClient;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepMode;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.model.StepStatus;

import javax.jms.Message;
import javax.jms.TextMessage;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.sql.Connection;
import java.util.Map;

import static ru.bsc.test.at.executor.service.AtExecutor.parseLongOrVariable;

public class MqStepExecutor extends AbstractStepExecutor {

    private final static int POLLING_RETRY_COUNT = 50;

    @Override
    public void execute(WireMockAdmin wireMockAdmin, Connection connection, Stand stand, HttpClient httpClient, MqClient mqClient, Map<String, Object> scenarioVariables, String testId, Project project, Step step, StepResult stepResult, String projectPath) throws Exception {

        if (mqClient == null) {
            throw new Exception("JMS is not configured in env.yml");
        }

        // 0. Установить ответы сервисов, которые будут использоваться в SoapUI для определения ответа
        setMockResponses(wireMockAdmin, project, testId, step.getMockServiceResponseList());

        // 1. Выполнить запрос БД и сохранить полученные значения
        executeSql(connection, step, scenarioVariables, stepResult);
        stepResult.setSavedParameters(scenarioVariables.toString());

        // 1.1 Отправить сообщение в очередь
        sendMessagesToQuery(project, step, scenarioVariables, mqClient, project.getTestIdHeaderName(), testId);

        // 2. Подстановка сохраненных параметров в строку запроса
        String requestUrl = stand.getServiceUrl() + insertSavedValuesToURL(step.getRelativeUrl(), scenarioVariables);
        stepResult.setRequestUrl(requestUrl);

        // 2.1 Подстановка сохраненных параметров в тело запроса
        String requestBody = insertSavedValues(step.getRequest(), scenarioVariables);

        // 2.2 Вычислить функции в теле запроса
        requestBody = evaluateExpressions(requestBody, scenarioVariables, null);
        stepResult.setRequestBody(requestBody);

        // 2.4 Cyclic sending request, COM-84
        int numberRepetitions = calculateNumberRepetitions(step, scenarioVariables);

        for (int repetitionCounter = 0; repetitionCounter < numberRepetitions; repetitionCounter++) {

            // Polling
            int retryCounter = 0;

            ClientResponse response = null;
            do {
                retryCounter++;

                // 3. Выполнить запрос (отправить сообщение в очередь)
                ClientMQRequest clientMQRequest = new ClientMQRequest(step.getMqOutputQueueName(), requestBody, null, testId, project.getUseRandomTestId() ? project.getTestIdHeaderName() : null);
                mqClient.request(clientMQRequest);

                if (StringUtils.isNotBlank(step.getMqInputQueueName())) {
                    long calculatedSleep = parseLongOrVariable(scenarioVariables, evaluateExpressions(step.getMqTimeoutMs(), scenarioVariables, null), 1000);
                    response = mqClient.waitMessage(step.getMqInputQueueName(), Math.min(calculatedSleep, 60000L), project.getUseRandomTestId() ? project.getTestIdHeaderName() : null, testId);
                }


                // Выполнить скрипт
                if (StringUtils.isNotEmpty(step.getScript())) {
                    ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("js");
                    scriptEngine.put("stepStatus", new StepStatus());
                    scriptEngine.put("scenarioVariables", scenarioVariables);
                    scriptEngine.put("response", null);

                    scriptEngine.eval(step.getScript());

                    // Привести все переменные сценария к строковому типу
                    scenarioVariables.forEach((s, s2) -> scenarioVariables.replace(s , s2 != null ? String.valueOf((Object)s2) : null));

                    StepStatus stepStatus = (StepStatus) scriptEngine.get("stepStatus");
                    if (StringUtils.isNotEmpty(stepStatus.getException())) {
                        throw new Exception(stepStatus.getException());
                    }
                }

            } while (tryUsePolling(step, response) && retryCounter <= POLLING_RETRY_COUNT);

            String content = response.getContent();
            stepResult.setPollingRetryCount(retryCounter);
            stepResult.setActual(content);
            stepResult.setExpected(step.getExpectedResponse());

            // 4. Сохранить полученные значения
            saveValuesByJsonXPath(step, content, scenarioVariables);

            stepResult.setSavedParameters(scenarioVariables.toString());

            // 4.1 Проверить сохраненные значения
            checkScenarioVariables(step, scenarioVariables);

            // 5. Подставить сохраненые значения в ожидаемый результат
            String expectedResponse = insertSavedValues(step.getExpectedResponse(), scenarioVariables);
            // 5.1. Расчитать выражения <f></f>
            expectedResponse = evaluateExpressions(expectedResponse, scenarioVariables, null);
            stepResult.setExpected(expectedResponse);

            checkResponseBody(step, expectedResponse, content);
        }

        // 7. Прочитать, что тестируемый сервис отправлял в заглушку.
        parseMockRequests(project, step, wireMockAdmin, scenarioVariables, testId);
    }

    @Override
    public boolean support(Step step) {
        return StepMode.JMS.equals(step.getStepMode());
    }
}
