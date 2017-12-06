package ru.bsc.test.autotester.component;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.AbstractModel;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;
import ru.bsc.test.at.executor.model.MockServiceResponse;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.yaml.YamlUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 27.10.2017.
 *
 */

@Component
public class ProjectsSource {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProjectsSource.class);
    private final static String MAIN_YAML_FILENAME = "main.yml";
    private final static String SCENARIO_YAML_FILENAME = "scenario.yml";
    private static final String FILE_ENCODING = "UTF-8";

    private String directoryPath;
    private final HashMap<Object, Integer> idToHashCode = new HashMap<>();

    private List<Project> loadProjects() {
        List<Project> projectList = new LinkedList<>();

        /*
        try {
            projectList.add(YamlUtils.loadAs(new File("c:\\tmp\\project-BCS_PREMIER.yml"), Project.class));
            projectList.get(0).setCode("NEW_FORMAT");
            saveToFiles(projectList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */


        projectList.clear();
        LOGGER.debug("Load projects from: {}", directoryPath);
        File[] fileList = new File(directoryPath).listFiles(File::isDirectory);
        if (fileList != null) {
            for (File projectDirectory: fileList) {
                LOGGER.debug("Reading directory: {}", projectDirectory.getAbsolutePath());
                File mainYaml = new File(projectDirectory.getAbsolutePath() + "/" + MAIN_YAML_FILENAME);
                if (mainYaml.exists()) {
                    try {
                        Project loadedProject = YamlUtils.loadAs(mainYaml, Project.class);
                        loadedProject.setCode(projectDirectory.getName());
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

    private void readExternalFiles(Project project) {
        File[] fileList = new File(directoryPath + "/" + project.getCode() + "/scenarios").listFiles(File::isDirectory);
        if (fileList != null) {
            for (File folder : fileList) {
                File scenarioYml = new File(folder, SCENARIO_YAML_FILENAME);
                if (scenarioYml.exists()) {
                    try {
                        project.getScenarioList().add(
                                loadScenarioFromFiles(folder)
                        );
                    } catch (IOException e) {
                        LOGGER.error("Read file " + scenarioYml.getAbsolutePath(), e);
                    }
                } else {
                    File[] innerFileList = folder.listFiles(File::isDirectory);
                    if (innerFileList != null) {
                        for (File groupFolder : innerFileList) {
                            File scenarioYmlInGroup = new File(groupFolder, SCENARIO_YAML_FILENAME);
                            if (scenarioYmlInGroup.exists()) {
                                try {
                                    project.getScenarioList().add(
                                            loadScenarioFromFiles(scenarioYmlInGroup)
                                    );
                                } catch (IOException e) {
                                    LOGGER.error("Read file " + scenarioYmlInGroup.getAbsolutePath(), e);
                                }
                            }
                        }
                    }
                }
            }
        }


        /*
        // Чтение внешних файлов списков шагов
        project.getScenarioList().forEach(scenario -> {
            if (scenario.getStepListYamlFile() != null && (scenario.getStepList() == null || scenario.getStepList().isEmpty())) {
                File stepListYamlFile = new File(directoryPath + "/" + project.getCode() + "/" + scenario.getStepListYamlFile());
                try {
                    if (stepListYamlFile.exists()) {
                        List<Step> loadedStepList = YamlUtils.loadAs(stepListYamlFile, List.class);
                        scenario.getStepList().clear();
                        scenario.getStepList().addAll(loadedStepList);
                    }
                } catch (IOException e) {
                    LOGGER.error("Read step list yaml", e);
                }
            }
        });
        */

        // Чтение внешних файлов для вложенных моделей
        applyToProjectModels(project, model -> idToHashCode.put(model, model.hashCode()), modelList -> {});
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

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public List<Project> getProjectList() {
        synchronized (this) {
            return loadProjects();
        }
    }

    public void save(List<Project> projectList) {
        synchronized (this) {
            saveToFiles(projectList);
            projectList.forEach(this::readExternalFiles);
        }
    }

    private void saveToFiles(List<Project> projectList) {
        projectList.forEach(projectItem -> {
            String fileName = directoryPath + "/" + projectItem.getCode() + "/" + MAIN_YAML_FILENAME;
            try {
                saveToExternalFiles(projectItem);
                projectItem.setScenarioList(null);
                YamlUtils.dumpToFile(projectItem, fileName);
            } catch (IOException e) {
                LOGGER.error("Save file " + fileName, e);
            }
        });
    }

    private String translit(String string) {
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

    private void saveToExternalFiles(Project project) throws IOException {
        String projectPath = directoryPath + "/" + project.getCode() + "/";
        if (new File(projectPath + "scenarios").exists()) {
            FileUtils.deleteDirectory(new File(projectPath + "scenarios"));
        }
        project.getScenarioList().forEach(scenario -> {
            try {
                saveScenarioToFiles(project.getCode(), scenarioPath(scenario), scenario);
            } catch (IOException e) {
                LOGGER.error("Save project external files", e);
            }
        });
    }

    private void applyToProjectModels(Project project, IMethodToModel methodToModel, IMethodToList methodToList) {
        methodToModel.method(project);
        methodToModel.method(project.getAmqpBroker());
        if (project.getScenarioList() != null) {
            methodToList.method(project.getScenarioList());
            project.getScenarioList().forEach(scenario -> {
                methodToModel.method(scenario);
                if (scenario.getStepList() != null) {
                    methodToList.method(scenario.getStepList());
                    scenario.getStepList().forEach(step -> {
                        methodToModel.method(step);
                        if (step.getExpectedServiceRequests() != null) {
                            methodToList.method(step.getExpectedServiceRequests());
                            step.getExpectedServiceRequests().forEach(methodToModel::method);
                        }
                        if (step.getStepParameterSetList() != null) {
                            methodToList.method(step.getStepParameterSetList());
                            step.getStepParameterSetList().forEach(stepParameterSet -> {
                                methodToModel.method(stepParameterSet);
                                if (stepParameterSet.getStepParameterList() != null) {
                                    methodToList.method(stepParameterSet.getStepParameterList());
                                    stepParameterSet.getStepParameterList().forEach(methodToModel::method);
                                }
                            });
                        }
                        if (step.getMockServiceResponseList() != null) {
                            methodToList.method(step.getMockServiceResponseList());
                            step.getMockServiceResponseList().forEach(methodToModel::method);
                        }
                        if (step.getFormDataList() != null) {
                            methodToList.method(step.getFormDataList());
                            step.getFormDataList().forEach(methodToModel::method);
                        }
                    });
                }
            });
        }
    }

    /*private void applyToAllProjects(List<Project> projectList, IMethodToModel methodToModel, IMethodToList methodToList) {
        projectList.forEach(project -> applyToProjectModels(project, methodToModel, methodToList));
    }*/

    public void deleteScenario(String projectCode, String scenarioPath) throws IOException {
        FileUtils.deleteDirectory(new File(directoryPath + "/" + projectCode + "/scenarios/" + scenarioPath));
    }

    public Scenario findScenario(String projectCode, String scenarioPath) throws IOException {
        return loadScenarioFromFiles(new File(directoryPath + "/" + projectCode + "/scenarios/" + scenarioPath));
    }

    private Scenario loadScenarioFromFiles(File scenarioFolder) throws IOException {
        File scenarioFile = new File(scenarioFolder + "/" + SCENARIO_YAML_FILENAME);
        Scenario scenario = YamlUtils.loadAs(scenarioFile, Scenario.class);
        scenario.setCode(scenarioFile.getParentFile().getParent());

        File scenarioRootDirectory = scenarioFile.getParentFile();
        scenario.getStepList().forEach(step ->
            loadStepFromFiles(step, scenarioRootDirectory)
        );

        return scenario;
    }

    private Step loadStepFromFiles(Step step, File scenarioRootDirectory) {
        File stepFolder = new File(scenarioRootDirectory, stepPath(step));
        if (step.getRequestFile() != null && step.getRequest() == null) {
            step.setRequest(readFile(stepFolder + step.getRequestFile()));
        }
        if (step.getExpectedResponseFile() != null && step.getExpectedResponse() == null) {
            step.setExpectedResponse(readFile(stepFolder + step.getExpectedResponseFile()));
        }
        if (step.getMqMessageFile() != null && step.getMqMessage() == null) {
            step.setMqMessage(readFile(stepFolder + step.getMqMessageFile()));
        }

        step.getMockServiceResponseList().forEach(mockServiceResponse -> {
            if (mockServiceResponse.getResponseBodyFile() != null && mockServiceResponse.getResponseBody() == null) {
                mockServiceResponse.setResponseBody(readFile(scenarioRootDirectory + mockServiceResponse.getResponseBodyFile()));
            }
        });

        step.getExpectedServiceRequests().forEach(expectedServiceRequest -> {
            if (expectedServiceRequest.getExpectedServiceRequestFile() != null && expectedServiceRequest.getExpectedServiceRequest() == null) {
                expectedServiceRequest.setExpectedServiceRequest(readFile(scenarioRootDirectory + expectedServiceRequest.getExpectedServiceRequestFile()));
            }
        });
        return step;
    }

    public void saveScenario(String projectCode, String scenarioPath, Scenario scenario) throws IOException {
        saveScenarioToFiles(projectCode, scenarioPath, scenario);
    }

    public Step saveStep(String projectCode, String scenarioPath, String stepCode, Step step) throws IOException {
        File scenarioRootDirectory = new File(directoryPath + "/" + projectCode + "/scenarios/" + scenarioPath + "/");
        return saveStepToFiles(stepCode, step, scenarioRootDirectory);
    }

    private Step saveStepToFiles(String stepCode, Step step, File scenarioRootDirectory) throws IOException {

        FileUtils.deleteDirectory(new File(scenarioRootDirectory + "/" + stepPath(step)));

        step.setCode(stepCode);

        if (step.getRequest() != null) {
            try {
                if (step.getRequestFile() == null) {
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
                if (step.getExpectedResponseFile() == null) {
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
                if (step.getMqMessageFile() == null) {
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
                    if (mockServiceResponse.getResponseBodyFile() == null) {
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
                    if (expectedServiceRequest.getExpectedServiceRequestFile() == null) {
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

        // TODO: read step from files and return
        return loadStepFromFiles(step, scenarioRootDirectory);
    }

    private void saveScenarioToFiles(String projectCode, String scenarioPath, Scenario scenario) throws IOException {
        File scenarioFile = new File(directoryPath + "/" + projectCode + "/scenarios/" + scenarioPath + "/" + SCENARIO_YAML_FILENAME);
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
            saveStepToFiles(step.getCode(), step, scenarioRootDirectory);
        }
        YamlUtils.dumpToFile(scenario, scenarioRootDirectory + SCENARIO_YAML_FILENAME);
    }

    @FunctionalInterface
    interface IMethodToModel {
        void method(AbstractModel model);
    }

    @FunctionalInterface
    interface IMethodToList {
        void method(List<? extends AbstractModel> modelList);
    }
}
