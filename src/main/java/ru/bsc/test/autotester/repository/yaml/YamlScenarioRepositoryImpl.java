package ru.bsc.test.autotester.repository.yaml;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.autotester.component.Translator;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.repository.ScenarioRepository;
import ru.bsc.test.autotester.repository.yaml.base.BaseYamlRepository;
import ru.bsc.test.autotester.yaml.YamlUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

/**
 * Created by sdoroshin on 27.10.2017.
 *
 */

@Repository
@Slf4j
public class YamlScenarioRepositoryImpl extends BaseYamlRepository implements ScenarioRepository {

    private final EnvironmentProperties environmentProperties;

    @Autowired
    public YamlScenarioRepositoryImpl(
            EnvironmentProperties environmentProperties,
            Translator translator
    ) {
        super(translator);
        this.environmentProperties = environmentProperties;
    }

    @Override
    public List<Scenario> findScenarios(String projectCode) {
        File scenariosDirectory = Paths.get(
                environmentProperties.getProjectsDirectoryPath(),
                projectCode,
                "scenarios"
        ).toFile();
        if (!scenariosDirectory.exists()) {
            return Collections.emptyList();
        }
        File[] directories = scenariosDirectory.listFiles(File::isDirectory);
        if (directories == null) {
            return Collections.emptyList();
        }
        List<Scenario> scenarios = new ArrayList<>();
        for (File directory : directories) {
            File scenarioYml = new File(directory, SCENARIO_YML_FILENAME);
            if (scenarioYml.exists()) {
                try {
                    scenarios.add(loadScenarioFromFiles(directory, null, false));
                } catch (IOException e) {
                    log.error("Read file " + scenarioYml.getAbsolutePath(), e);
                }
            } else {
                File[] innerFileList = directory.listFiles(File::isDirectory);
                if (innerFileList != null) {
                    for (File scenarioYmlInGroup : innerFileList) {
                        if (new File(scenarioYmlInGroup, SCENARIO_YML_FILENAME).exists()) {
                            try {
                                scenarios.add(loadScenarioFromFiles(scenarioYmlInGroup, directory.getName(), false));
                            } catch (IOException e) {
                                log.error("Read file " + scenarioYmlInGroup.getAbsolutePath(), e);
                            }
                        }
                    }
                }
            }
        }
        return scenarios;
    }

    @Override
    public Scenario findScenario(String projectCode, String scenarioPath) throws IOException {
        String[] pathParts = scenarioPath.split("/");
        String projectsPath = environmentProperties.getProjectsDirectoryPath();
        return loadScenarioFromFiles(
                Paths.get(projectsPath, projectCode, "scenarios", scenarioPath).toFile(),
                pathParts.length > 1 ? pathParts[0] : null,
                true
        );
    }

    @Override
    public Scenario saveScenario(String projectCode, String scenarioPath, Scenario scenario, boolean updateDirectoryName) throws IOException {
        String projectsPath = environmentProperties.getProjectsDirectoryPath();
        if (scenarioPath == null) {
            scenarioPath = scenarioPath(scenario);
        } else {
            if (updateDirectoryName && !scenario.getCode().equals(translator.translate(scenario.getName()))) {
                scenario.setCode(null);
                Files.move(
                        Paths.get(projectsPath, projectCode, "scenarios", scenarioPath),
                        Paths.get(projectsPath, projectCode, "scenarios", scenarioPath(scenario))
                );
                scenarioPath = scenarioPath(scenario);
            }
        }
        File scenarioFile = Paths.get(
                projectsPath,
                projectCode,
                "scenarios",
                scenarioPath,
                SCENARIO_YML_FILENAME
        ).toFile();
        File scenarioRootDirectory = scenarioFile.getParentFile();

        // Прочитать существующий сценарий (для создаваемого сценария этот файл еще не существует)
        if (new File(scenarioRootDirectory, SCENARIO_YML_FILENAME).exists()) {
            Scenario existsScenario = loadScenarioFromFiles(scenarioRootDirectory, null, true);

            // Найти шаги, которые удалены
            existsScenario.getStepList().forEach(existsStep -> {
                boolean isExists = scenario.getStepList().stream()
                        .anyMatch(step -> Objects.equals(existsStep.getCode(), step.getCode()));
                if (!isExists && existsStep.getCode() != null) {
                    Path stepsDirectory = Paths.get(
                            scenarioRootDirectory.getAbsolutePath(),
                            "steps",
                            existsStep.getCode()
                    );
                    try {
                        Files.deleteIfExists(stepsDirectory);
                    } catch (IOException e) {
                        log.error("Delete step directory {}", stepsDirectory, e);
                    }
                }
            });
        }

        saveScenarioToFiles(scenario, scenarioFile);
        scenario.getStepList().forEach(step -> loadStepFromFiles(step, scenarioRootDirectory));
        return scenario;
    }

    @Override
    public Set<Scenario> findByRelativeUrl(String projectCode, String relativeUrl) {
        return findScenarios(projectCode).stream()
                .filter(scenario -> checkSteps(scenario, relativeUrl))
                .collect(Collectors.toSet());
    }

    @Override
    public void delete(String projectCode, String scenarioPath) throws IOException {
        FileUtils.deleteDirectory(new File(environmentProperties.getProjectsDirectoryPath() + "/" + projectCode + "/scenarios/" + scenarioPath));
    }

    private boolean checkSteps(Scenario scenario, String relativeUrl) {
        return scenario.getStepList()
                .stream()
                .anyMatch(s -> containsIgnoreCase(s.getRelativeUrl(), relativeUrl));
    }

    private Scenario loadScenarioFromFiles(File scenarioDirectory, String group, boolean fetchSteps) throws IOException {
        File scenarioFile = new File(scenarioDirectory, SCENARIO_YML_FILENAME);
        Scenario scenario = YamlUtils.loadAs(scenarioFile, Scenario.class);
        scenario.setCode(scenarioFile.getParentFile().getName());
        scenario.setScenarioGroup(group);
        File scenarioRootDirectory = scenarioFile.getParentFile();
        if (fetchSteps) {
            scenario.getStepList().forEach(step -> loadStepFromFiles(step, scenarioRootDirectory));
        } else {
            scenario.getStepList().clear();
        }
        return scenario;
    }
}
