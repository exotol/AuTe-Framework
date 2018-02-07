package ru.bsc.test.autotester.component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bsc.test.at.executor.model.AmqpBroker;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;
import ru.bsc.test.at.executor.model.MockServiceResponse;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.properties.StandProperties;
import ru.bsc.test.autotester.yaml.YamlUtils;

/**
 * Created by sdoroshin on 27.10.2017.
 *
 */

public class ProjectsSource {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProjectsSource.class);
    private final static String MAIN_YAML_FILENAME = "main.yml";
    private final static String SCENARIO_YAML_FILENAME = "scenario.yml";
    private static final String FILE_ENCODING = "UTF-8";

    private EnvironmentProperties environmentProperties;

    private List<Project> loadProjects() {
        List<Project> projectList = new LinkedList<>();

        projectList.clear();
        LOGGER.debug("Load projects from: {}", environmentProperties.getProjectsDirectoryPath());
        File[] fileList = new File(environmentProperties.getProjectsDirectoryPath()).listFiles(File::isDirectory);
        if (fileList != null) {
            for (File projectDirectory: fileList) {
                LOGGER.debug("Reading directory: {}", projectDirectory.getAbsolutePath());
                File mainYaml = new File(projectDirectory.getAbsolutePath() + "/" + MAIN_YAML_FILENAME);
                if (mainYaml.exists()) {
                    try {
                        Project loadedProject = YamlUtils.loadAs(mainYaml, Project.class);
                        loadedProject.setCode(projectDirectory.getName());
                        environmentToProject(loadedProject);
                        readExternalFiles(loadedProject);

                        projectList.add(loadedProject);
                    } catch (IOException e) {
                        LOGGER.error("Main project file not found", e);
                    }
                }
            }
        }

        return projectList;
    }

    private void environmentToProject(Project project) {
        StandProperties standProperties = environmentProperties.getProjectStandMap().get(project.getCode());
        if (standProperties != null) {
            Stand stand = new Stand();
            stand.setServiceUrl(standProperties.getServiceUrl());
            stand.setWireMockUrl(standProperties.getWireMockUrl());
            if (standProperties.getDataBase() != null) {
                stand.setDbUrl(standProperties.getDataBase().getUrl());
                stand.setDbUser(standProperties.getDataBase().getUser());
                stand.setDbPassword(standProperties.getDataBase().getPassword());
            }
            project.setStand(stand);

            if (standProperties.getAmqpBroker() != null) {
                AmqpBroker amqpBroker = new AmqpBroker();
                amqpBroker.setMqService(standProperties.getAmqpBroker().getMqService());
                amqpBroker.setHost(standProperties.getAmqpBroker().getHost());
                amqpBroker.setPort(standProperties.getAmqpBroker().getPort());
                amqpBroker.setUsername(standProperties.getAmqpBroker().getUsername());
                amqpBroker.setPassword(standProperties.getAmqpBroker().getPassword());
                amqpBroker.setQueueManager(standProperties.getAmqpBroker().getQueueManager());
                amqpBroker.setChannel(standProperties.getAmqpBroker().getChannel());
                amqpBroker.setCcsid(standProperties.getAmqpBroker().getCcsid());
                amqpBroker.setTransportType(standProperties.getAmqpBroker().getTransportType());
                project.setAmqpBroker(amqpBroker);
            }
        }
    }

    private void readExternalFiles(Project project) {
        Set<String> groupSet = new HashSet<>();
        File[] fileList = new File(environmentProperties.getProjectsDirectoryPath() + "/" + project.getCode() + "/scenarios").listFiles(File::isDirectory);
        if (fileList != null) {
            for (File directory : fileList) {
                File scenarioYml = new File(directory, SCENARIO_YAML_FILENAME);
                if (scenarioYml.exists()) {
                    try {
                        project.getScenarioList().add(
                                loadScenarioFromFiles(directory, null)
                        );
                    } catch (IOException e) {
                        LOGGER.error("Read file " + scenarioYml.getAbsolutePath(), e);
                    }
                } else {
                    File[] innerFileList = directory.listFiles(File::isDirectory);
                    if (innerFileList != null) {
                        for (File scenarioYmlInGroup : innerFileList) {
                            if (new File(scenarioYmlInGroup, SCENARIO_YAML_FILENAME).exists()) {
                                try {
                                    project.getScenarioList().add(
                                            loadScenarioFromFiles(scenarioYmlInGroup, directory.getName())
                                    );
                                } catch (IOException e) {
                                    LOGGER.error("Read file " + scenarioYmlInGroup.getAbsolutePath(), e);
                                }
                            }
                        }
                    }
                    groupSet.add(directory.getName());
                }
            }
        }
        List<String> groupList = new ArrayList<>(groupSet);
        Collections.sort(groupList);
        project.setGroupList(groupList);
    }

    private String readFile(String path) {
        try {
            File file = new File(path);
            return FileUtils.readFileToString(file, FILE_ENCODING);
        } catch (IOException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Reading file " + path, e);
            }
        }
        return null;
    }

    public List<Project> getProjectList() {
        synchronized (this) {
            return loadProjects();
        }
    }

    public void saveProject(Project project) {
        synchronized (this) {
            saveProjectToFiles(project);
        }
    }

    public void saveFullProject(Project project) throws Exception {
        synchronized (this) {
            File root = new File(environmentProperties.getProjectsDirectoryPath() + "/" + project.getCode());
            if (!root.exists()) {
                if (root.mkdirs()) {
                    saveAllScenariosToExternalFiles(project);
                    saveProjectToFiles(project);
                } else {
                    throw new Exception("Directory " + root + " not created.");
                }
            } else {
                throw new Exception("Project " + project.getCode() + " already exists.");
            }
        }
    }

    private void clearProjectBeforeSave(Project project) {
        project.setScenarioList(null);
        project.setStand(null);
        project.setAmqpBroker(null);
    }

    private void saveProjectToFiles(Project project) {
        String fileName = environmentProperties.getProjectsDirectoryPath() + "/" + project.getCode() + "/" + MAIN_YAML_FILENAME;
        try {
            clearProjectBeforeSave(project);
            YamlUtils.dumpToFile(project, fileName);
        } catch (IOException e) {
            LOGGER.error("Save file " + fileName, e);
        }
    }

    private String translit(String string) {
        if (string == null) {
            return "";
        }
        String[] _alpha = {"a","b","v","g","d","e","yo","g","z","i","y","i",
                "k","l","m","n","o","p","r","s","t","u",
                "f","h","tz","ch","sh","sh","","e","yu","ya"};
        String alpha = "абвгдеёжзиыйклмнопрстуфхцчшщьэюя";
        StringBuilder nname = new StringBuilder("");
        for (char ch : string.toLowerCase().toCharArray()) {
            int k = alpha.indexOf(ch);
            if (k != -1)
                nname.append(_alpha[k]);
            else {
                nname.append(ch);
            }
        }

        String result = nname.toString().replaceAll("[^a-zA-Z0-9.-]", "_");
        result = result.substring(0, Math.min(result.length(), 30));
        return result;
    }

    private String scenarioPath(Scenario scenario) {
        String result = "";
        if (scenario != null) {
            if (scenario.getScenarioGroup() != null) {
                result += scenario.getScenarioGroup() + "/";
            }
            if (scenario.getCode() == null) {
                scenario.setCode(UUID.randomUUID().toString().substring(0, 6) + "-" + translit(scenario.getName()));
            }
            result += scenario.getCode() + "/";
        } else {
            result = "0/";
        }
        return result;
    }

    private String stepPath(Step step) {
        return "steps/" + step.getCode() + "/";
    }

    private String stepRequestFile() {
        return "request.json";
    }

    private String stepExpectedResponseFile(Step step) {
        return "expected-response." + extByContent(step.getExpectedResponse());
    }

    private String stepMqMessageFile(Step step) {
        return "mq-message." + extByContent(step.getMqMessage());
    }

    private String mockResponseBodyFile(MockServiceResponse mockServiceResponse) {
        if (mockServiceResponse.getCode() == null) {
            mockServiceResponse.setCode(UUID.randomUUID().toString());
        }
        return "mock-response-" + mockServiceResponse.getCode() + "." + extByContent(mockServiceResponse.getResponseBody());
    }

    private String expectedServiceRequestFile(ExpectedServiceRequest expectedServiceRequest) {
        if (expectedServiceRequest.getCode() == null) {
            expectedServiceRequest.setCode(UUID.randomUUID().toString());
        }
        return "expected-service-request-" + expectedServiceRequest.getCode() + "." + extByContent(expectedServiceRequest.getExpectedServiceRequest());
    }

    private String extByContent(String content) {
        return content != null && content.indexOf('<') == 0 ? "xml" : "json";
    }

    private void saveAllScenariosToExternalFiles(Project project) throws IOException {
        String projectPath = environmentProperties.getProjectsDirectoryPath() + "/" + project.getCode() + "/";
        if (new File(projectPath + "scenarios").exists()) {
            FileUtils.deleteDirectory(new File(projectPath + "scenarios"));
        }
        project.getScenarioList().forEach(scenario -> {
            try {
                File scenarioFile = new File(environmentProperties.getProjectsDirectoryPath() + "/" + project.getCode() + "/scenarios/" + scenarioPath(scenario) + "/" + SCENARIO_YAML_FILENAME);
                saveScenarioToFiles(scenario, scenarioFile, true);
            } catch (IOException e) {
                LOGGER.error("Save project external files", e);
            }
        });
    }

    public void deleteScenario(String projectCode, String scenarioPath) throws IOException {
        FileUtils.deleteDirectory(new File(environmentProperties.getProjectsDirectoryPath() + "/" + projectCode + "/scenarios/" + scenarioPath));
    }

    public Scenario findScenario(String projectCode, String scenarioPath) throws IOException {
        String[] pathParts = scenarioPath.split("/");
        return loadScenarioFromFiles(new File(environmentProperties.getProjectsDirectoryPath() + "/" + projectCode + "/scenarios/" + scenarioPath), pathParts.length > 1 ? pathParts[0] : null);
    }

    private Scenario loadScenarioFromFiles(File scenarioDirectory, String group) throws IOException {
        File scenarioFile = new File(scenarioDirectory + "/" + SCENARIO_YAML_FILENAME);
        Scenario scenario = YamlUtils.loadAs(scenarioFile, Scenario.class);
        scenario.setCode(scenarioFile.getParentFile().getName());
        scenario.setScenarioGroup(group);

        File scenarioRootDirectory = scenarioFile.getParentFile();
        scenario.getStepList().forEach(step ->
            loadStepFromFiles(step, scenarioRootDirectory)
        );

        return scenario;
    }

    private void loadStepFromFiles(Step step, File scenarioRootDirectory) {
        if (step.getRequestFile() != null && step.getRequest() == null) {
            step.setRequest(readFile(scenarioRootDirectory + "/" + step.getRequestFile()));
        }
        if (step.getExpectedResponseFile() != null && step.getExpectedResponse() == null) {
            step.setExpectedResponse(readFile(scenarioRootDirectory + "/" + step.getExpectedResponseFile()));
        }
        if (step.getMqMessageFile() != null && step.getMqMessage() == null) {
            step.setMqMessage(readFile(scenarioRootDirectory + "/" + step.getMqMessageFile()));
        }

        step.getMockServiceResponseList().forEach(mockServiceResponse -> {
            if (mockServiceResponse.getResponseBodyFile() != null && mockServiceResponse.getResponseBody() == null) {
                mockServiceResponse.setResponseBody(readFile(scenarioRootDirectory + "/" + mockServiceResponse.getResponseBodyFile()));
            }
        });

        step.getExpectedServiceRequests().forEach(expectedServiceRequest -> {
            if (expectedServiceRequest.getExpectedServiceRequestFile() != null && expectedServiceRequest.getExpectedServiceRequest() == null) {
                expectedServiceRequest.setExpectedServiceRequest(readFile(scenarioRootDirectory + "/" + expectedServiceRequest.getExpectedServiceRequestFile()));
            }
        });
    }

    public void saveScenario(String projectCode, String scenarioPath, Scenario scenario) throws IOException {
        if (scenarioPath == null) {
            scenarioPath = scenarioPath(scenario);
        }
        File scenarioFile = new File(environmentProperties.getProjectsDirectoryPath() + File.separatorChar + projectCode + File.separatorChar + "scenarios" + File.separatorChar + scenarioPath + File.separatorChar + SCENARIO_YAML_FILENAME);
        File scenarioRootDirectory = scenarioFile.getParentFile();

        // Прочитать существующий сценарий (для создаваемого сценария этот файл еще не существует)
        if (new File(scenarioRootDirectory, SCENARIO_YAML_FILENAME).exists()) {
            Scenario existsScenario = loadScenarioFromFiles(scenarioRootDirectory, null);

            // Найти шаги, которые удалены
            existsScenario.getStepList().forEach(existsStep -> {
                boolean isExists = scenario.getStepList().stream().filter(step -> Objects.equals(existsStep.getCode(), step.getCode())).count() > 0;
                if (!isExists && existsStep.getCode() != null) {
                    try {
                        FileUtils.deleteDirectory(new File(scenarioRootDirectory, "steps" + File.separatorChar + existsStep.getCode()));
                    } catch (IOException e) {
                        LOGGER.error("Delete step directory " + scenarioRootDirectory + File.separatorChar + existsStep.getCode(), e);
                    }
                }
            });
        }

        saveScenarioToFiles(scenario, scenarioFile, false);
        scenario.getStepList().forEach(step -> loadStepFromFiles(step, scenarioRootDirectory));
    }

    private void saveStepToFiles(String stepCode, Step step, File scenarioRootDirectory, boolean updatePaths) throws IOException {
        if (step.getCode() != null) {
            FileUtils.deleteDirectory(new File(scenarioRootDirectory + "/" + stepPath(step)));
        }
        step.setCode(StringUtils.isEmpty(stepCode) ? UUID.randomUUID().toString() : stepCode);

        if (step.getRequest() != null) {
            try {
                if (step.getRequestFile() == null || updatePaths) {
                    step.setRequestFile(stepPath(step) + stepRequestFile());
                }
                File file = new File(scenarioRootDirectory + "/" + step.getRequestFile());
                FileUtils.writeStringToFile(file, step.getRequest(), FILE_ENCODING);
                step.setRequest(null);
            } catch (IOException e) {
                LOGGER.error("Save file " + scenarioRootDirectory + "/" + step.getRequestFile(), e);
            }
        } else {
            step.setRequestFile(null);
        }

        if (step.getExpectedResponse() != null) {
            try {
                if (step.getExpectedResponseFile() == null || updatePaths) {
                    step.setExpectedResponseFile(stepPath(step) + stepExpectedResponseFile(step));
                }
                File file = new File(scenarioRootDirectory + "/" + step.getExpectedResponseFile());
                FileUtils.writeStringToFile(file, step.getExpectedResponse(), FILE_ENCODING);
                step.setExpectedResponse(null);
            } catch (IOException e) {
                LOGGER.error("Save file " + scenarioRootDirectory + "/" + step.getExpectedResponseFile(), e);
            }
        } else {
            step.setExpectedResponseFile(null);
        }

        if (step.getMqMessage() != null) {
            try {
                if (step.getMqMessageFile() == null || updatePaths) {
                    step.setMqMessageFile(stepPath(step) + stepMqMessageFile(step));
                }
                File file = new File(scenarioRootDirectory + "/" + step.getMqMessageFile());
                FileUtils.writeStringToFile(file, step.getMqMessage(), FILE_ENCODING);
                step.setMqMessage(null);
            } catch (IOException e) {
                LOGGER.error("Save file " + scenarioRootDirectory + "/" + step.getMqMessageFile(), e);
            }
        } else {
            step.setMqMessageFile(null);
        }

        step.getMockServiceResponseList().forEach(mockServiceResponse -> {
            if (mockServiceResponse.getResponseBody() != null) {
                try {
                    if (mockServiceResponse.getResponseBodyFile() == null || updatePaths) {
                        mockServiceResponse.setResponseBodyFile(stepPath(step) + mockResponseBodyFile(mockServiceResponse));
                    }
                    File file = new File(scenarioRootDirectory + "/" + mockServiceResponse.getResponseBodyFile());
                    FileUtils.writeStringToFile(file, mockServiceResponse.getResponseBody(), FILE_ENCODING);
                    mockServiceResponse.setResponseBody(null);
                } catch (IOException e) {
                    LOGGER.error("Save file " + scenarioRootDirectory + "/" + mockServiceResponse.getResponseBodyFile(), e);
                }
            } else {
                mockServiceResponse.setResponseBodyFile(null);
            }
        });

        step.getExpectedServiceRequests().forEach(expectedServiceRequest -> {
            if (expectedServiceRequest.getExpectedServiceRequest() != null) {
                try {
                    if (expectedServiceRequest.getExpectedServiceRequestFile() == null || updatePaths) {
                        expectedServiceRequest.setExpectedServiceRequestFile(stepPath(step) + expectedServiceRequestFile(expectedServiceRequest));
                    }
                    File file = new File(scenarioRootDirectory + "/" + expectedServiceRequest.getExpectedServiceRequestFile());
                    FileUtils.writeStringToFile(file, expectedServiceRequest.getExpectedServiceRequest(), FILE_ENCODING);
                    expectedServiceRequest.setExpectedServiceRequest(null);
                } catch (IOException e) {
                    LOGGER.error("Save file " + scenarioRootDirectory + "/" + expectedServiceRequest.getExpectedServiceRequestFile(), e);
                }
            } else {
                expectedServiceRequest.setExpectedServiceRequest(null);
            }
        });
    }

    private void saveScenarioToFiles(Scenario scenario, File scenarioFile, boolean updatePaths) throws IOException {
        File scenarioRootDirectory = scenarioFile.getParentFile();
        int i = 1;
        Set<String> codeSet = scenario.getStepList().stream().map(Step::getCode).collect(Collectors.toSet());
        for (Step step: scenario.getStepList()) {
            if (step.getCode() == null) {
                String newCode = null;
                if (step.getStepComment() != null) {
                    String translited = translit(step.getStepComment());
                    if (StringUtils.isNotEmpty(translited)) {
                        newCode = String.valueOf(i) + "-" + translited;
                    }
                }
                if (StringUtils.isEmpty(newCode)) {
                    newCode = String.valueOf(i);
                }
                int j = 2;
                while (codeSet.contains(newCode)) {
                    newCode = String.valueOf(i) + "-" + String.valueOf(j);
                    j++;
                }
                step.setCode(newCode);
                codeSet.add(newCode);
            }
            i++;
        }

        for (Step step : scenario.getStepList()) {
            saveStepToFiles(step.getCode(), step, scenarioRootDirectory, updatePaths);
        }
        String scenarioGroupSaved = scenario.getScenarioGroup();
        scenario.setScenarioGroup(null);
        YamlUtils.dumpToFile(scenario, scenarioFile.getAbsolutePath());
        scenario.setScenarioGroup(scenarioGroupSaved);
    }

    public void addNewGroup(String projectCode, String groupName) throws Exception {
        File file = new File(environmentProperties.getProjectsDirectoryPath() + "/" + projectCode + "/scenarios/" + groupName);
        if (file.exists()) {
            throw new Exception("Directory already exists");
        } else {
            if (!file.mkdir()) {
                throw new Exception("Directory not created");
            }
        }
    }

    public void renameGroup(String projectCode, String oldGroupName, String newGroupName) throws Exception {
        File file = new File(environmentProperties.getProjectsDirectoryPath() + "/" + projectCode + "/scenarios/" + oldGroupName);
        if (new File(file, "scenario.yml").exists() || !file.isDirectory()) {
            throw new Exception("This is not a group");
        } else {
            if (!file.renameTo(new File(environmentProperties.getProjectsDirectoryPath() + "/" + projectCode + "/scenarios/" + newGroupName))) {
                throw new Exception("Directory not renamed");
            }
        }
    }

    public void setEnvironmentProperties(EnvironmentProperties environmentProperties) {
        this.environmentProperties = environmentProperties;
    }

    public EnvironmentProperties getEnvironmentProperties() {
        return environmentProperties;
    }
}
