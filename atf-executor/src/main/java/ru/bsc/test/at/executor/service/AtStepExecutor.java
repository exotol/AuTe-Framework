package ru.bsc.test.at.executor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import ru.bsc.test.at.executor.ei.wiremock.WireMockAdmin;
import ru.bsc.test.at.executor.exception.ScenarioStopException;
import ru.bsc.test.at.executor.helper.MqMockHelper;
import ru.bsc.test.at.executor.helper.ServiceRequestsComparatorHelper;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepParameterSet;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.service.api.Executor;
import ru.bsc.test.at.executor.service.api.StepExecutorRequest;
import ru.bsc.test.at.executor.step.executor.IStepExecutor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static ru.bsc.test.at.executor.service.AtProjectExecutor.parseLongOrVariable;

/**
 * @author Pavel Golovkin
 */
@Slf4j
public class AtStepExecutor implements Executor<StepExecutorRequest> {

    private final ServiceRequestsComparatorHelper serviceRequestsComparatorHelper = new ServiceRequestsComparatorHelper();
    private final MqMockHelper mqMockHelper = new MqMockHelper();

    @Override
    public void execute(StepExecutorRequest stepExecutorRequest) {
        Assert.notNull(stepExecutorRequest, "stepExecutorRequest must not be null");
        log.debug("executeSteps {}, {}, {}, {}, {}, {}, {}, {}",
                stepExecutorRequest.getStand(), stepExecutorRequest.getStepList(),
                stepExecutorRequest.getProject(), stepExecutorRequest.getStepResultList(), stepExecutorRequest.getHttpClient(),
                stepExecutorRequest.getScenarioVariables(), stepExecutorRequest.isStepEditable(), stepExecutorRequest.getProjectPath());
        if (stepExecutorRequest.getStepList() == null) {
            log.warn("executeSteps got empty stepList");
            return;
        }
        List<IStepExecutor> stepExecutorList = IStepExecutor.getStepExecutorList();
        for (Step step : stepExecutorRequest.getStepList()) {
            if (!step.getDisabled()) {
                List<StepParameterSet> parametersEnvironment;
                if (step.getStepParameterSetList() != null && !step.getStepParameterSetList().isEmpty()) {
                    parametersEnvironment = step.getStepParameterSetList();
                } else {
                    parametersEnvironment = new LinkedList<>();
                    parametersEnvironment.add(new StepParameterSet());
                }
                for (StepParameterSet stepParameterSet : parametersEnvironment) {
                    StepResult stepResult = new StepResult(stepExecutorRequest.getProject().getCode(), step);
                    stepResult.setStart(new Date().getTime());
                    stepResult.setEditable(stepExecutorRequest.isStepEditable());
                    stepExecutorRequest.getStepResultList().add(stepResult);

                    // COM-123 Timeout
                    if (step.getTimeoutMs() != null) {
                        long timeout = parseLongOrVariable(stepExecutorRequest.getScenarioVariables(), step.getTimeoutMs(), 0);
                        if (timeout > 0) {
                            try {
                                Thread.sleep(Math.min(timeout, 60000L));
                            } catch (InterruptedException ex) {
                                log.error("Can't interrupt thread for delay", ex);
                            }
                        }
                    }

                    if (stepParameterSet.getStepParameterList() != null) {
                        stepParameterSet.getStepParameterList()
                                .forEach(stepParameter -> stepExecutorRequest.getScenarioVariables().put(stepParameter.getName().trim(), stepParameter.getValue()));
                        stepResult.setDescription(stepParameterSet.getDescription());
                    }
                    try (WireMockAdmin wireMockAdmin = stepExecutorRequest.getStand() != null && isNotEmpty(stepExecutorRequest.getStand().getWireMockUrl()) ? new WireMockAdmin(stepExecutorRequest.getStand().getWireMockUrl()) : null) {
                        if (stepExecutorRequest.getStand() == null) {
                            log.error("Stand is not configured");
                            throw new Exception("Stand is not configured.");
                        }
                        String testId = stepExecutorRequest.getProject().getUseRandomTestId() ? UUID.randomUUID().toString() : "-";
                        stepResult.setTestId(testId);

                        for (IStepExecutor stepExecutor : stepExecutorList) {
                            if (stepExecutor.support(step)) {
                                stepResult.setSavedParameters(stepExecutorRequest.getScenarioVariables().toString());
                                stepExecutor.execute(wireMockAdmin, stepExecutorRequest.getConnection(), stepExecutorRequest.getStand(), stepExecutorRequest.getHttpClient(), stepExecutorRequest.getMqClient(), stepExecutorRequest.getScenarioVariables(), testId, stepExecutorRequest.getProject(), step, stepResult, stepExecutorRequest.getProjectPath());
                                break;
                            }
                        }

                        // После выполнения шага необходимо проверить запросы к веб-сервисам
                        serviceRequestsComparatorHelper.assertTestCaseWSRequests(stepExecutorRequest.getProject(), stepExecutorRequest.getScenarioVariables(), wireMockAdmin, testId, step);

                        mqMockHelper.assertMqRequests(wireMockAdmin, testId, step, stepExecutorRequest.getScenarioVariables(), stepExecutorRequest.getProject().getMqCheckCount(), stepExecutorRequest.getProject().getMqCheckInterval());

                        stepResult.setSavedParameters(stepExecutorRequest.getScenarioVariables().toString());
                        stepResult.setResult(StepResult.StepResultType.OK);
                    } catch (Exception e) {
                        StringWriter sw = new StringWriter();
                        e.printStackTrace(new PrintWriter(sw));

                        stepResult.setResult(StepResult.StepResultType.FAIL);
                        stepResult.setDetails(sw.toString().substring(0, Math.min(sw.toString().length(), 10000)));
                    } finally {
                        stepResult.setStop(new Date().getTime());
                    }

                    stepResult.setScenarioVariables(new HashMap<>(stepExecutorRequest.getScenarioVariables()));

                    if (stepExecutorRequest.getStopObserver() != null && stepExecutorRequest.getStopObserver().stop()) {
                        throw new ScenarioStopException("stop observer is null");
                    }
                }
            }
        }
    }
}
