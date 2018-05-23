package ru.bsc.test.at.executor.service.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.bsc.test.at.executor.helper.client.impl.http.HttpClient;
import ru.bsc.test.at.executor.helper.client.impl.mq.MqClient;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.service.IStopObserver;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @author Pavel Golovkin
 */
@Data
@AllArgsConstructor
public class StepExecutorRequest implements ExecutorRequest {

    private Connection connection;
    private Stand stand;
    private List<Step> stepList;
    private Project project;
    private List<StepResult>stepResultList;
    private HttpClient httpClient;
    private MqClient mqClient;
    private Map<String, Object> scenarioVariables;
    private boolean stepEditable;
    private String projectPath;
    private IStopObserver stopObserver;
}
