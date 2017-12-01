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
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

                        if (loadedProject.getBeforeScenarioPath() != null) {
                            loadedProject.setBeforeScenario(findScenarioByPath(loadedProject.getBeforeScenarioPath(), loadedProject.getScenarioList()));
                        }
                        if (loadedProject.getAfterScenarioPath() != null) {
                            loadedProject.setAfterScenario(findScenarioByPath(loadedProject.getAfterScenarioPath(), loadedProject.getScenarioList()));
                        }

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

    private Scenario findScenarioByPath(String path, List<Scenario> scenarioList) {
        String scenarioCode;
        String scenarioGroupCode;
        String[] scenarioPathParts = path.split("/");
        if (scenarioPathParts.length == 1) {
            scenarioGroupCode = null;
            scenarioCode = scenarioPathParts[0];
        } else if (scenarioPathParts.length == 2) {
            scenarioGroupCode = scenarioPathParts[0];
            scenarioCode = scenarioPathParts[1];
        } else {
            return null;
        }

        return scenarioList.stream()
                .filter(scenario -> Objects.equals(scenario.getCode(), scenarioCode))
                .filter(scenario -> scenarioGroupCode == null || Objects.equals(scenarioGroupCode, scenario.getScenarioGroup()))
                .findAny()
                .orElse(null);
    }

    private void readExternalFiles(Project project) {


        File[] fileList = new File(directoryPath + "/" + project.getCode() + "/scenarios").listFiles(File::isDirectory);
        if (fileList != null) {
            for (File folder : fileList) {
                File scenarioYml = new File(folder, "scenario.yml");
                if (scenarioYml.exists()) {
                    try {
                        Scenario scenario = YamlUtils.loadAs(scenarioYml, Scenario.class);
                        scenario.setCode(folder.getName());
                        project.getScenarioList().add(scenario);
                    } catch (IOException e) {
                        LOGGER.error("Read file " + scenarioYml.getAbsolutePath(), e);
                    }
                } else {
                    File[] innerFileList = folder.listFiles(File::isDirectory);
                    if (innerFileList != null) {
                        for (File groupFolder : innerFileList) {
                            File scenarioYmlInGroup = new File(groupFolder, "scenario.yml");
                            if (scenarioYmlInGroup.exists()) {
                                try {
                                    Scenario scenario = YamlUtils.loadAs(scenarioYmlInGroup, Scenario.class);
                                    scenario.setScenarioGroup(folder.getName());
                                    project.getScenarioList().add(scenario);
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
        applyToProjectModels(project, model -> {
            String scenariosRoot = directoryPath + "/" + project.getCode() + "/scenarios/";
            if (model instanceof Scenario) {
                Scenario scenario = ((Scenario) model);
                scenario.getStepList().forEach(step -> {
                    String stepRoot = scenariosRoot + scenarioPath(scenario);
                    if (step.getRequestFile() != null && step.getRequest() == null) {
                        step.setRequest(readFile(stepRoot + step.getRequestFile()));
                    }
                    if (step.getExpectedResponseFile() != null && step.getExpectedResponse() == null) {
                        step.setExpectedResponse(readFile(stepRoot + step.getExpectedResponseFile()));
                    }
                    if (step.getMqMessageFile() != null && step.getMqMessage() == null) {
                        step.setMqMessage(readFile(stepRoot + step.getMqMessageFile()));
                    }

                    step.getMockServiceResponseList().forEach(mockServiceResponse -> {
                        if (mockServiceResponse.getResponseBodyFile() != null && mockServiceResponse.getResponseBody() == null) {
                            mockServiceResponse.setResponseBody(readFile(stepRoot + mockServiceResponse.getResponseBodyFile()));
                        }
                    });

                    step.getExpectedServiceRequests().forEach(expectedServiceRequest -> {
                        if (expectedServiceRequest.getExpectedServiceRequestFile() != null && expectedServiceRequest.getExpectedServiceRequest() == null) {
                            expectedServiceRequest.setExpectedServiceRequest(readFile(stepRoot + expectedServiceRequest.getExpectedServiceRequestFile()));
                        }
                    });
                });
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
            String fileName = directoryPath + "/" + projectItem.getCode() + "/" + MAIN_YAML_FILENAME;
            try {
                saveToExternalFiles(projectItem, true);
                projectItem.setScenarioList(null);
                if (projectItem.getBeforeScenario() == null) {
                    projectItem.setBeforeScenarioPath(null);
                } else {
                    projectItem.setBeforeScenarioPath(
                            (StringUtils.isEmpty(projectItem.getBeforeScenario().getScenarioGroup()) ? "" : projectItem.getBeforeScenario().getScenarioGroup() + "/")
                            + projectItem.getBeforeScenario().getCode()
                    );
                    projectItem.setBeforeScenario(null);
                }
                if (projectItem.getAfterScenario() == null) {
                    projectItem.setAfterScenarioPath(null);
                } else {
                    projectItem.setAfterScenarioPath(
                            (StringUtils.isEmpty(projectItem.getAfterScenario().getScenarioGroup()) ? "" : projectItem.getAfterScenario().getScenarioGroup() + "/")
                            + projectItem.getAfterScenario().getCode()
                    );
                    projectItem.setAfterScenario(null);
                }
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
                scenario.setCode(scenario.getId() + "-" + translit(scenario.getName()));
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
        return "mock-response-" + mockServiceResponse.getId() + "." + extByContent(mockServiceResponse.getResponseBody());
    }

    private String expectedServiceRequestFile(ExpectedServiceRequest expectedServiceRequest) {
        return "expected-service-request-" + expectedServiceRequest.getId() + "." + extByContent(expectedServiceRequest.getExpectedServiceRequest());
    }

    private String extByContent(String content) {
        return content != null && content.indexOf('<') == 0 ? "xml" : "json";
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

    private void saveToExternalFiles(Project project, boolean saveAll) {
        String projectPath = directoryPath + "/" + project.getCode() + "/";
        if (saveAll) {
            try {
                if (new File(projectPath + "scenarios").exists()) {
                    FileUtils.cleanDirectory(new File(projectPath + "scenarios"));
                }
            } catch (IOException e) {
                LOGGER.error("Clean directory error", e);
            }
        }
        project.getScenarioList().forEach(scenario -> {
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

            scenario.getStepList().forEach(step -> {
                String stepFullPath = projectPath + "scenarios/" + scenarioPath(scenario);
                if (step.getRequest() != null) {
                    try {
                        if (step.getRequestFile() == null || saveAll) {
                            step.setRequestFile(stepPath(step) + stepRequestFile());
                        }
                        if (isModelWasChanged(step) || saveAll) {
                            File file = new File(stepFullPath + step.getRequestFile());
                            FileUtils.writeStringToFile(file, step.getRequest(), FILE_ENCODING);
                        }
                        step.setRequest(null);
                    } catch (IOException e) {
                        LOGGER.error("Save file " + stepFullPath + step.getRequestFile(), e);
                    }
                } else {
                    step.setRequestFile(null);
                }
                if (step.getExpectedResponse() != null) {
                    try {
                        if (step.getExpectedResponseFile() == null || saveAll) {
                            step.setExpectedResponseFile(stepPath(step) + stepExpectedResponseFile(step));
                        }
                        if (isModelWasChanged(step) || saveAll) {
                            File file = new File(stepFullPath + step.getExpectedResponseFile());
                            FileUtils.writeStringToFile(file, step.getExpectedResponse(), FILE_ENCODING);
                        }
                        step.setExpectedResponse(null);
                    } catch (IOException e) {
                        LOGGER.error("Save file " + stepFullPath + step.getExpectedResponseFile(), e);
                    }
                } else {
                    step.setExpectedResponseFile(null);
                }
                if (step.getMqMessage() != null) {
                    try {
                        if (step.getMqMessageFile() == null || saveAll) {
                            step.setMqMessageFile(stepPath(step) + stepMqMessageFile(step));
                        }
                        File file = new File(stepFullPath + step.getMqMessageFile());
                        FileUtils.writeStringToFile(file, step.getMqMessage(), FILE_ENCODING);
                        step.setMqMessage(null);
                    } catch (IOException e) {
                        LOGGER.error("Save file " + stepFullPath + step.getMqMessageFile(), e);
                    }
                } else {
                    step.setMqMessageFile(null);
                }

                step.getMockServiceResponseList().forEach(mockServiceResponse -> {
                    if (mockServiceResponse.getResponseBody() != null) {
                        try {
                            if (mockServiceResponse.getResponseBodyFile() == null || saveAll) {
                                mockServiceResponse.setResponseBodyFile(stepPath(step) + mockResponseBodyFile(mockServiceResponse));
                            }
                            if (isModelWasChanged(step) || saveAll) {
                                File file = new File(stepFullPath + mockServiceResponse.getResponseBodyFile());
                                FileUtils.writeStringToFile(file, mockServiceResponse.getResponseBody(), FILE_ENCODING);
                            }
                            mockServiceResponse.setResponseBody(null);
                        } catch (IOException e) {
                            LOGGER.error("Save file " + stepFullPath + mockServiceResponse.getResponseBodyFile(), e);
                        }
                    } else {
                        mockServiceResponse.setResponseBodyFile(null);
                    }
                });

                step.getExpectedServiceRequests().forEach(expectedServiceRequest -> {
                    if (expectedServiceRequest.getExpectedServiceRequest() != null) {
                        try {
                            if (expectedServiceRequest.getExpectedServiceRequestFile() == null || saveAll) {
                                expectedServiceRequest.setExpectedServiceRequestFile(stepPath(step) + expectedServiceRequestFile(expectedServiceRequest));
                            }
                            if (isModelWasChanged(step) || saveAll) {
                                File file = new File(stepFullPath + expectedServiceRequest.getExpectedServiceRequestFile());
                                FileUtils.writeStringToFile(file, expectedServiceRequest.getExpectedServiceRequest(), FILE_ENCODING);
                            }
                            expectedServiceRequest.setExpectedServiceRequest(null);
                        } catch (IOException e) {
                            LOGGER.error("Save file " + stepFullPath + expectedServiceRequest.getExpectedServiceRequestFile(), e);
                        }
                    } else {
                        expectedServiceRequest.setExpectedServiceRequest(null);
                    }
                });
            });
        });

        project.getScenarioList().forEach(scenario -> {
            try {
                // TODO: Проверить, изменялись ли вложенные в каждый step элементы.
                if (isModelWasChanged(scenario) || isModelListWasChanged(scenario.getStepList()) || saveAll) {
                    String fileName = directoryPath + "/" + project.getCode() + "/scenarios/" + scenarioPath(scenario) + "scenario.yml";
                    YamlUtils.dumpToFile(scenario, fileName);
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        });

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
                        if (step.getFormDataList() != null) {
                            methodToList.method(step.getFormDataList());
                            step.getFormDataList().forEach(methodToModel::method);
                        }
                    });
                }
            });
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
