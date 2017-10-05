package ru.bsc.test.autotester.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.service.AtExecutor;

import ru.bsc.test.autotester.repository.ScenarioRepository;
import ru.bsc.test.autotester.service.ExpectedServiceRequestService;
import ru.bsc.test.autotester.service.ScenarioService;
import ru.bsc.test.autotester.service.StepService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Service
public class ScenarioServiceImpl implements ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final ExpectedServiceRequestService expectedServiceRequestService;
    private final StepService stepService;

    @Autowired
    public ScenarioServiceImpl(ScenarioRepository scenarioRepository, ExpectedServiceRequestService expectedServiceRequestService, StepService stepService) {
        this.scenarioRepository = scenarioRepository;
        this.expectedServiceRequestService = expectedServiceRequestService;
        this.stepService = stepService;
    }

    @PostConstruct
    public void init() {
        stepService.setScenarioService(this);
    }

    @Override
    public List<Scenario> findAllByProjectIdAndScenarioGroupId(Long projectId, Long scenarioGroupId) {
        return scenarioRepository.findAllByProjectIdAndScenarioGroupIdOrderByScenarioGroupIdDescNameAsc(projectId, scenarioGroupId);
    }

    @Override
    public List<Scenario> executeScenarioList(Project project, List<Scenario> scenarioList) {
        AtExecutor atExecutor = new AtExecutor();
        return atExecutor.executeScenarioList(project, scenarioList);
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

    @SuppressWarnings("WeakerAccess")
    protected class ServiceNameResponsePair {
        private String serviceName;
        private String response;

        ServiceNameResponsePair(String serviceName, String response) {
            this.serviceName = serviceName;
            this.response = response;
        }

        public String getServiceName() {
            return serviceName;
        }

        String getResponse() {
            return response;
        }
    }

    @SuppressWarnings("WeakerAccess")
    protected List<ServiceNameResponsePair> parseServiceResponsesString(String responses) {
        List<ServiceNameResponsePair> result = new LinkedList<>();
        if (responses != null) {
            for (String service : responses.split(";")) {
                String[] serviceData = service.split(":");
                // TODO что-то сделать с неправильным форматом
                if (serviceData.length == 2) {
                    String serviceName = serviceData[0].trim();
                    for (String response : serviceData[1].split(",")) {
                        result.add(new ServiceNameResponsePair(serviceName, response.trim()));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void parseExpectedServiceRequestsJmba(String expectedRequestsBaseDir, Scenario scenario) {
        // Костыли для импорта запросов к сервису из конкретного проекта (jbma)
        for (Step step: scenario.getSteps()) {
            Long i = 0L;
            String testCaseDir = step.getTmpServiceRequestsDirectory();
            if (StringUtils.isNotEmpty(testCaseDir)) {

                List<ServiceNameResponsePair> servicePairs = parseServiceResponsesString(step.getResponses());
                for (ServiceNameResponsePair pair : servicePairs) {
                    File serviceRequestDirectory = new File(expectedRequestsBaseDir + '\\' + testCaseDir + '\\' + pair.getServiceName());
                    if (serviceRequestDirectory.exists()) {

                        for (File serviceRequestFile : serviceRequestDirectory.listFiles(pathname -> pathname != null && pathname.getName() != null && pathname.getName().endsWith(".xml"))) {
                            if (!serviceRequestFile.isDirectory()) {
                                try {
                                    expectedServiceRequestService.save(new ExpectedServiceRequest(
                                            step,
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

    @Override
    public Step cloneStep(Step step) {
        if (step != null) {
            Scenario scenario = step.getScenario();
            Long maxSortStep = scenario.getSteps().stream().max(Comparator.comparing(Step::getSort)).map(Step::getSort).orElse(0L);
            Step newStep = step.clone();
            newStep.setSort(maxSortStep + 50);
            newStep.setScenario(scenario);
            return stepService.save(newStep);
        }
        return null;
    }
}
