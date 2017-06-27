package ru.bsc.test.autotester.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.service.AtExecutor;

import ru.bsc.test.autotester.repository.ScenarioRepository;
import ru.bsc.test.autotester.repository.ServiceResponseRepository;
import ru.bsc.test.autotester.repository.impl.ScenarioRepositoryWrapper;
import ru.bsc.test.autotester.repository.impl.ServiceResponseRepositoryWrapper;
import ru.bsc.test.autotester.service.ExpectedServiceRequestService;
import ru.bsc.test.autotester.service.ScenarioService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
public class ScenarioServiceImpl extends AtExecutor implements ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final ExpectedServiceRequestService expectedServiceRequestService;

    @Autowired
    public ScenarioServiceImpl(ScenarioRepository scenarioRepository, ServiceResponseRepository serviceResponseRepository, ExpectedServiceRequestService expectedServiceRequestService) {

        super(new ScenarioRepositoryWrapper(scenarioRepository), new ServiceResponseRepositoryWrapper(serviceResponseRepository));

        this.scenarioRepository = scenarioRepository;
        this.expectedServiceRequestService = expectedServiceRequestService;
    }

    @Override
    public List<Scenario> findAllByProjectIdAndScenarioGroupId(Long projectId, Long scenarioGroupId) {
        return scenarioRepository.findAllByProjectIdAndScenarioGroupIdOrderByScenarioGroupIdDescNameAsc(projectId, scenarioGroupId);
    }

    @Override
    public List<Scenario> executeScenarioList(Project project, List<Scenario> scenarioList) {
        return super.executeScenarioList(project, scenarioList);
    }

    public Scenario save(Scenario scenario) {
        return scenarioRepository.save(scenario);
    }

    public Scenario findOne(long scenarioId) {
        return scenarioRepository.findOne(scenarioId);
    }

    public void delete(Scenario scenario) {
        scenarioRepository.delete(scenario);
    }

    @Override
    public void parseExpectedServiceRequestsJmba(String expectedRequestsBaseDir, Scenario scenario) {
        // Костыли для импорта запросов к сервису из конкретного проекта (jbma)
        for (Step step: scenario.getSteps()) {
            Long i = 0L;
            String testCaseDir = step.getTmpServiceRequestsDirectory();
            if (testCaseDir != null && !testCaseDir.isEmpty()) {

                List<ServiceNameResponsePair> servicePairs = parseServiceResponsesString(step.getResponses());
                for (ServiceNameResponsePair pair : servicePairs) {
                    File serviceRequestDirectory = new File(expectedRequestsBaseDir + '\\' + testCaseDir + '\\' + pair.getServiceName());
                    if (serviceRequestDirectory.exists()) {

                        for (File serviceRequestFile : serviceRequestDirectory.listFiles(pathname -> pathname != null && pathname.getName() != null && pathname.getName().endsWith(".xml"))) {
                            if (!serviceRequestFile.isDirectory()) {
                                try {
                                    expectedServiceRequestService.save(new ExpectedServiceRequest(
                                            step.getId(),
                                            pair.getServiceName(),
                                            new String(Files.readAllBytes(Paths.get(serviceRequestFile.getPath())), StandardCharsets.UTF_8),
                                            50 * i++
                                    ));

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    @Override
    public List<Scenario> findAll(List<Long> scenarioIdList) {
        return scenarioRepository.findAll(scenarioIdList);
    }
}
