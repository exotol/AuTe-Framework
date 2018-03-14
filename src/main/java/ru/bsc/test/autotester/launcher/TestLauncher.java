package ru.bsc.test.autotester.launcher;

import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.service.AtExecutor;
import ru.bsc.test.autotester.component.impl.LimitTransliterationTranslator;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.report.AbstractReportGenerator;
import ru.bsc.test.autotester.report.AllureReportGenerator;
import ru.bsc.test.autotester.repository.ProjectRepository;
import ru.bsc.test.autotester.repository.yaml.YamlProjectRepositoryImpl;
import ru.bsc.test.autotester.yaml.YamlUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestLauncher {

    public void launch() throws Exception {
        EnvironmentProperties properties = YamlUtils.loadAs(new File("env.yml"), EnvironmentProperties.class);
        ProjectRepository projectRepository = new YamlProjectRepositoryImpl(
                properties,
                new LimitTransliterationTranslator()
        );
        List<Project> projectList = projectRepository.findAllProjectsWithScenarios();
        AbstractReportGenerator reportGenerator = new AllureReportGenerator();
        int testFailedCount = projectList.stream().mapToInt(project -> {
            if (properties.getProjectStandMap() == null || !properties.getProjectStandMap().containsKey(project.getCode())) {
                return 0;
            }
            AtExecutor atExecutor = new AtExecutor();
            atExecutor.setProjectPath(Paths.get(properties.getProjectsDirectoryPath(), project.getCode()).toString());

            int scenarioFailedCount = 0;
            for (Scenario scenario: project.getScenarioList()) {
                scenarioFailedCount += test(scenario, project, atExecutor, reportGenerator);
            }
            return scenarioFailedCount;
        }).sum();

        reportGenerator.generate(new File("." + File.separator + "report"));
        System.exit(testFailedCount > 0 ? 1 : 0);
    }

    private int test(Scenario scenarioToExecute, Project project, AtExecutor atExecutor, AbstractReportGenerator reportGenerator) {
        Map<Scenario, List<StepResult>> result = new HashMap<>();
        int[] sum = { 0 };

        atExecutor.executeScenarioList(
                project,
                Collections.singletonList(scenarioToExecute),
                result,
                () -> false,
                scenarioResultListMap -> sum[0] = scenarioResultListMap.entrySet().stream().mapToInt(value -> {
                    Scenario scenario = value.getKey();
                    List<StepResult> stepResultList = value.getValue();
                    reportGenerator.add(scenario, stepResultList);
                    return stepResultList.stream().mapToInt(stepResult -> (StepResult.RESULT_OK.equals(stepResult.getResult())) ? 0 : 1).sum();
                }).sum()
        );
        return sum[0];
    }
}
