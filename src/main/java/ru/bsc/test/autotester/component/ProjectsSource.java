package ru.bsc.test.autotester.component;

import org.apache.commons.io.FileUtils;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by sdoroshin on 27.10.2017.
 *
 */

@Component
public class ProjectsSource {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProjectsSource.class);
    private final static String MAIN_YAML_FILENAME = "main.yml";
    private static final String FILE_ENCODING = "UTF-8";

    private String directoryPath;
    private final HashMap<Long, Integer> idToHashCode = new HashMap<>();

    private List<Project> loadProjects() {
        List<Project> projectList = new LinkedList<>();
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
                        loadedProject.setProjectCode(projectDirectory.getName());
                        readExternalFiles(loadedProject);

                        projectList.add(loadedProject);
                    } catch (IOException e) {
                        LOGGER.error("Main project file not found", e);
                    }
                }
            }
        }

        // Проверка и восстановление id:
        // - исправляются повторения id
        // - исправляются неназначенные id (id == null)
        checkAndRepairId(projectList);

        return projectList;
    }

    private void readExternalFiles(Project project) {
        // Чтение внешних файлов списков шагов
        project.getScenarioList().forEach(scenario -> {
            if (scenario.getStepListYamlFile() != null && (scenario.getStepList() == null || scenario.getStepList().isEmpty())) {
                File stepListYamlFile = new File(directoryPath + "/" + project.getProjectCode() + "/" + scenario.getStepListYamlFile());
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

        // Чтение внешних файлов для вложенных моделей
        applyToProjectModels(project, model -> {
            String projectPath = directoryPath + "/" + project.getProjectCode() + "/";
            if (model instanceof Step) {
                Step step = ((Step) model);
                if (step.getRequestFile() != null && step.getRequest() == null) {
                    step.setRequest(readFile(projectPath + step.getRequestFile()));
                }
                if (step.getExpectedResponseFile() != null && step.getExpectedResponse() == null) {
                    step.setExpectedResponse(readFile(projectPath + step.getExpectedResponseFile()));
                }
                if (step.getMqMessageFile() != null && step.getMqMessage() == null) {
                    step.setMqMessage(readFile(projectPath + step.getMqMessageFile()));
                }
            } else if (model instanceof MockServiceResponse) {
                MockServiceResponse mockServiceResponse = (MockServiceResponse) model;
                if (mockServiceResponse.getResponseBodyFile() != null && mockServiceResponse.getResponseBody() == null) {
                    mockServiceResponse.setResponseBody(readFile(projectPath + mockServiceResponse.getResponseBodyFile()));
                }
            } else if (model instanceof ExpectedServiceRequest) {
                ExpectedServiceRequest expectedServiceRequest = (ExpectedServiceRequest) model;
                if (expectedServiceRequest.getExpectedServiceRequestFile() != null && expectedServiceRequest.getExpectedServiceRequest() == null) {
                    expectedServiceRequest.setExpectedServiceRequest(readFile(projectPath + expectedServiceRequest.getExpectedServiceRequestFile()));
                }
            }
            if (model != null) {
                idToHashCode.put(model.getId(), model.hashCode());
            }
        }, modelList -> {});
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
            checkAndRepairSort(projectList);
            checkAndRepairId(projectList);
            saveToFiles(projectList);
            projectList.forEach(this::readExternalFiles);
        }
    }

    private void saveToFiles(List<Project> projectList) {
        projectList.forEach(projectItem -> {
            String fileName = directoryPath + "/" + projectItem.getProjectCode() + "/" + MAIN_YAML_FILENAME;
            try {
                saveToExternalFiles(projectItem);
                YamlUtils.dumpToFile(projectItem, fileName);
            } catch (IOException e) {
                LOGGER.error("Save file " + fileName, e);
            }
        });
    }

    private String translit(String string) {
        String[] _alpha = {"a","b","v","g","d","e","yo","g","z","i","y","i",
                "k","l","m","n","o","p","r","s","t","u",
                "f","h","tz","ch","sh","sh","'","e","yu","ya"};
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

        return nname.toString().replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    private String scenarioPath(Scenario scenario) {
        String result;
        if (scenario != null) {
            result = "";
            if (scenario.getScenarioGroup() != null) {
                result += "group-" + scenario.getScenarioGroup().getId() + "-" + translit(scenario.getScenarioGroup().getName()) + "/";
            }

            result += String.valueOf(scenario.getId()) + "-" + translit(scenario.getName()) + "/";
        } else {
            result = "0/";
        }
        return result;
    }

    private String stepPath(Step step) {
        return step.getId() + (step.getStepComment() == null ? "" : "-" + translit(step.getStepComment()));
    }

    private String stepRequestFile(Step step, Scenario scenario) {
        return "scenarios/" + scenarioPath(scenario) + "steps/" + stepPath(step) + "/request.json";
    }

    private String stepExpectedResponseFile(Step step, Scenario scenario) {
        return "scenarios/" + scenarioPath(scenario) + "steps/" + stepPath(step) + "/expected-response.json";
    }

    private String mockResponseBodyFile(MockServiceResponse mockServiceResponse, Scenario scenario) {
        String ext = mockServiceResponse.getResponseBody() != null && mockServiceResponse.getResponseBody().indexOf('<') == 0 ? "xml" : "json";
        return "scenarios/" + scenarioPath(scenario) + "steps/" + stepPath(mockServiceResponse.getStep()) + "/mock-response-" + mockServiceResponse.getId() + "." + ext;
    }

    private String expectedServiceRequestFile(ExpectedServiceRequest expectedServiceRequest, Scenario scenario) {
        String ext = expectedServiceRequest.getExpectedServiceRequest() != null && expectedServiceRequest.getExpectedServiceRequest().indexOf('<') == 0 ? "xml" : "json";
        return "scenarios/" + scenarioPath(scenario) + "steps/" + stepPath(expectedServiceRequest.getStep()) + "/expected-service-request-" + expectedServiceRequest.getId() + "." + ext;
    }

    private String stepMqMessageFile(Step step, Scenario scenario) {
        String ext = step.getMqMessage() != null && step.getMqMessage().indexOf('<') == 0 ? "xml" : ( step.getMqMessage().indexOf('{') == 0 ? "json" : "txt");
        return "scenarios/" + scenarioPath(scenario) + "steps/" + stepPath(step) + "/mq-message." + ext;
    }

    private boolean isModelWasChanged(AbstractModel model) {
        if (model == null) {
            return true;
        }
        Integer lastHashCode = idToHashCode.get(model.getId());
        // The model did not change
        return lastHashCode == null || lastHashCode != model.hashCode();
    }

    private boolean isModelListWasChanged(List<? extends AbstractModel> modelList) {
        if (modelList == null) {
            return true;
        }
        for (AbstractModel model: modelList) {
            if (isModelWasChanged(model)) {
                return true;
            }
        }
        return false;
    }

    private void saveToExternalFiles(Project project) {
        applyToProjectModels(project, model -> {
            if (model instanceof Step) {
                Step step = (Step)model;
                Scenario stepScenario = findScenarioByStep(step, project);
                if (step.getRequest() != null) {
                    try {
                        if (step.getRequestFile() == null) {
                            step.setRequestFile(stepRequestFile(step, stepScenario));
                        }
                        if (isModelWasChanged(model)) {
                            File file = new File(directoryPath + "/" + project.getProjectCode() + "/" + step.getRequestFile());
                            FileUtils.writeStringToFile(file, step.getRequest(), FILE_ENCODING);
                        }
                        step.setRequest(null);
                    } catch (IOException e) {
                        LOGGER.error("Save file " + directoryPath + "/" + project.getProjectCode() + "/" + step.getRequestFile(), e);
                    }
                }
                if (step.getExpectedResponse() != null) {
                    try {
                        if (step.getExpectedResponseFile() == null) {
                            step.setExpectedResponseFile(stepExpectedResponseFile(step, stepScenario));
                        }
                        if (isModelWasChanged(model)) {
                            File file = new File(directoryPath + "/" + project.getProjectCode() + "/" + step.getExpectedResponseFile());
                            FileUtils.writeStringToFile(file, step.getExpectedResponse(), FILE_ENCODING);
                        }
                        step.setExpectedResponse(null);
                    } catch (IOException e) {
                        LOGGER.error("Save file " + directoryPath + "/" + project.getProjectCode() + "/" + step.getExpectedResponseFile(), e);
                    }
                }
                if (step.getMqMessage() != null) {
                    try {
                        if (step.getMqMessageFile() == null) {
                            step.setMqMessageFile(stepMqMessageFile(step, stepScenario));
                        }
                        File file = new File(directoryPath + "/" + project.getProjectCode() + "/" + step.getMqMessageFile());
                        FileUtils.writeStringToFile(file, step.getMqMessage(), FILE_ENCODING);
                        step.setMqMessage(null);
                    } catch (IOException e) {
                        LOGGER.error("Save file " + directoryPath + "/" + project.getProjectCode() + "/" + step.getMqMessageFile(), e);
                    }
                }
            } else if (model instanceof MockServiceResponse) {
                MockServiceResponse mockServiceResponse = (MockServiceResponse)model;
                Scenario stepScenario = findScenarioByStep(mockServiceResponse.getStep(), project);
                if (mockServiceResponse.getResponseBody() != null) {
                    try {
                        if (mockServiceResponse.getResponseBodyFile() == null) {
                            mockServiceResponse.setResponseBodyFile(mockResponseBodyFile(mockServiceResponse, stepScenario));
                        }
                        if (isModelWasChanged(model)) {
                            File file = new File(directoryPath + "/" + project.getProjectCode() + "/" + mockServiceResponse.getResponseBodyFile());
                            FileUtils.writeStringToFile(file, mockServiceResponse.getResponseBody(), FILE_ENCODING);
                        }
                        mockServiceResponse.setResponseBody(null);
                    } catch (IOException e) {
                        LOGGER.error("Save file " + directoryPath + "/" + project.getProjectCode() + "/" + mockServiceResponse.getResponseBodyFile(), e);
                    }
                }
            } else if (model instanceof ExpectedServiceRequest) {
                ExpectedServiceRequest expectedServiceRequest = (ExpectedServiceRequest)model;
                Scenario stepScenario = findScenarioByStep(expectedServiceRequest.getStep(), project);
                if (expectedServiceRequest.getExpectedServiceRequest() != null) {
                    try {
                        if (expectedServiceRequest.getExpectedServiceRequestFile() == null) {
                            expectedServiceRequest.setExpectedServiceRequestFile(expectedServiceRequestFile(expectedServiceRequest, stepScenario));
                        }
                        if (isModelWasChanged(model)) {
                            File file = new File(directoryPath + "/" + project.getProjectCode() + "/" + expectedServiceRequest.getExpectedServiceRequestFile());
                            FileUtils.writeStringToFile(file, expectedServiceRequest.getExpectedServiceRequest(), FILE_ENCODING);
                        }
                        expectedServiceRequest.setExpectedServiceRequest(null);
                    } catch (IOException e) {
                        LOGGER.error("Save file " + directoryPath + "/" + project.getProjectCode() + "/" + expectedServiceRequest.getExpectedServiceRequestFile(), e);
                    }
                }
            }
        }, modelList -> {});

        project.getScenarioList().forEach(scenario -> {
            if (scenario.getStepList() != null && !scenario.getStepList().isEmpty()) {
                try {
                    if (scenario.getStepListYamlFile() == null || scenario.getStepListYamlFile().isEmpty()) {
                        scenario.setStepListYamlFile("scenarios/" + scenarioPath(scenario) + "stepList.yml");
                    }
                    if (isModelListWasChanged(scenario.getStepList())) {
                        String fileName = directoryPath + "/" + project.getProjectCode() + "/" + scenario.getStepListYamlFile();
                        YamlUtils.dumpToFile(scenario.getStepList(), fileName);
                    }
                    scenario.getStepList().clear();
                } catch (IOException e) {
                    LOGGER.error("", e);
                }
            }
        });

    }

    private Scenario findScenarioByStep(Step step, Project project) {
        return project.getScenarioList()
                .stream()
                .filter(scenario1 ->
                        scenario1.getStepList().stream().filter(step1 -> Objects.equals(step1.getId(), step.getId())).count() == 1
                )
                .findAny().orElse(null);
    }

    private void checkAndRepairSort(List<Project> projectList) {
        applyToAllProjects(
                projectList,
                model -> {},
                modelList -> modelList.sort(Comparator.comparingLong(o -> o.getSort() != null ? o.getSort() : 0L)));
    }

    private void checkAndRepairId(List<Project> projectList) {
        Set<Long> idSet = new LinkedHashSet<>();
        applyToAllProjects(projectList, model -> collectAndReplaceRepeatsId(idSet, model), modelList -> {});
        applyToAllProjects(projectList, model -> newIdToModel(idSet, model), modelList -> {});
    }

    private void collectAndReplaceRepeatsId(Set<Long> idSet, AbstractModel model) {
        if (idSet == null || model == null) {
            return;
        }
        if (model.getId() != null) {
            Long newId = idSet.add(model.getId()) ? model.getId() : null;
            model.setId(newId);
        }
    }

    private void newIdToModel(Set<Long> idSet, AbstractModel model) {
        if (idSet == null || model == null) {
            return;
        }

        if (model.getId() == null) {
            Long lastId = idSet.stream().max(Long::compareTo).orElse(1L);
            Long newId = lastId + 1;
            idSet.add(newId);
            model.setId(newId);
        }
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
                    });
                }
            });
        }
        if (project.getScenarioGroups() != null) {
            methodToList.method(project.getScenarioGroups());
            project.getScenarioGroups().forEach(methodToModel::method);
        }
        if (project.getStandList() != null) {
            methodToList.method(project.getStandList());
            project.getStandList().forEach(methodToModel::method);
        }
    }

    private void applyToAllProjects(List<Project> projectList, IMethodToModel methodToModel, IMethodToList methodToList) {
        projectList.forEach(project -> applyToProjectModels(project, methodToModel, methodToList));
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
