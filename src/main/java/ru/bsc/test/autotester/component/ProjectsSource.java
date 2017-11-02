package ru.bsc.test.autotester.component;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;
import ru.bsc.test.at.executor.model.AbstractModel;
import ru.bsc.test.at.executor.model.ExpectedServiceRequest;
import ru.bsc.test.at.executor.model.MockServiceResponse;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Step;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
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
                        Representer representer = new Representer();
                        representer.getPropertyUtils().setSkipMissingProperties(true);
                        Project loadedProject = new Yaml(representer).loadAs(new FileReader(mainYaml), Project.class);
                        loadedProject.setProjectCode(projectDirectory.getName());
                        readExternalFiles(loadedProject);

                        projectList.add(loadedProject);
                    } catch (FileNotFoundException e) {
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
        // Чтение дополнительных файлов
        applyToProjectModels(project, model -> {
            if (model instanceof Step) {
                Step step = ((Step) model);
                if (step.getRequestFile() != null && step.getRequest() == null) {
                    String filePath = directoryPath + "/" + project.getProjectCode() + "/" + step.getRequestFile();
                    step.setRequest(readFile(filePath));
                }
                if (step.getExpectedResponseFile() != null && step.getExpectedResponse() == null) {
                    String filePath = directoryPath + "/" + step.getScenario().getProject().getProjectCode() + "/" + step.getExpectedResponseFile();
                    step.setExpectedResponse(readFile(filePath));
                }

            } else if (model instanceof MockServiceResponse) {
                MockServiceResponse mockServiceResponse = (MockServiceResponse) model;
                if (mockServiceResponse.getResponseBodyFile() != null && mockServiceResponse.getResponseBody() == null) {
                    String filePath = directoryPath + "/" + mockServiceResponse.getStep().getScenario().getProject().getProjectCode() + "/" + mockServiceResponse.getResponseBodyFile();
                    mockServiceResponse.setResponseBody(readFile(filePath));
                }
            } else if (model instanceof ExpectedServiceRequest) {
                ExpectedServiceRequest expectedServiceRequest = (ExpectedServiceRequest) model;
                if (expectedServiceRequest.getExpectedServiceRequestFile() != null && expectedServiceRequest.getExpectedServiceRequest() == null) {
                    String filePath = directoryPath + "/" + expectedServiceRequest.getStep().getScenario().getProject().getProjectCode() + "/" + expectedServiceRequest.getExpectedServiceRequestFile();
                    expectedServiceRequest.setExpectedServiceRequest(readFile(filePath));
                }
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
        return loadProjects();
    }

    public void save(List<Project> projectList) {
        checkAndRepairSort(projectList);
        checkAndRepairId(projectList);
        saveToFiles(projectList);
        projectList.forEach(this::readExternalFiles);
    }

    private void saveToFiles(List<Project> projectList) {
        projectList.forEach(projectItem -> {
            try {
                FileWriter writer = new FileWriter(directoryPath + "/" + projectItem.getProjectCode() + "/" + MAIN_YAML_FILENAME);
                DumperOptions dumperOptions = new DumperOptions();
                dumperOptions.setAnchorGenerator(new AutotesterAnchorGenerator());

                saveToExternalFiles(projectItem);

                new Yaml(dumperOptions).dump(projectItem, writer);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                LOGGER.error("Save file " + directoryPath + "/" + projectItem.getProjectCode() + "/" + MAIN_YAML_FILENAME, e);
            }
        });
    }

    private void saveToExternalFiles(Project project) {
        applyToProjectModels(project, model -> {
            if (model instanceof Step) {
                Step step = (Step)model;
                if (step.getRequest() != null) {
                    try {
                        if (step.getRequestFile() == null) {
                            step.setRequestFile("scenarios/" + step.getScenario().getId() + "/steps/" + step.getId() + "/request.json");
                        }
                        File file = new File(directoryPath + "/" + project.getProjectCode() + "/" + step.getRequestFile());
                        FileUtils.writeStringToFile(file, step.getRequest(), FILE_ENCODING);
                        step.setRequest(null);
                    } catch (IOException e) {
                        LOGGER.error("Save file " + directoryPath + "/" + project.getProjectCode() + "/" + step.getRequestFile(), e);
                    }
                }
                if (step.getExpectedResponse() != null) {
                    try {
                        if (step.getExpectedResponseFile() == null) {
                            step.setExpectedResponseFile("scenarios/" + step.getScenario().getId() + "/steps/" + step.getId() + "/expected-response.json");
                        }
                        File file = new File(directoryPath + "/" + project.getProjectCode() + "/" + step.getExpectedResponseFile());
                        FileUtils.writeStringToFile(file, step.getExpectedResponse(), FILE_ENCODING);
                        step.setExpectedResponse(null);
                    } catch (IOException e) {
                        LOGGER.error("Save file " + directoryPath + "/" + project.getProjectCode() + "/" + step.getExpectedResponseFile(), e);
                    }
                }
            } else if (model instanceof MockServiceResponse) {
                MockServiceResponse mockServiceResponse = (MockServiceResponse)model;
                if (mockServiceResponse.getResponseBody() != null) {
                    try {
                        if (mockServiceResponse.getResponseBodyFile() == null) {
                            mockServiceResponse.setResponseBodyFile("scenarios/" + mockServiceResponse.getStep().getScenario().getId() + "/steps/" + mockServiceResponse.getStep().getId() + "/mock-response-" + mockServiceResponse.getId() + ".json");
                        }
                        File file = new File(directoryPath + "/" + project.getProjectCode() + "/" + mockServiceResponse.getResponseBodyFile());
                        FileUtils.writeStringToFile(file, mockServiceResponse.getResponseBody(), FILE_ENCODING);
                        mockServiceResponse.setResponseBody(null);
                    } catch (IOException e) {
                        LOGGER.error("Save file " + directoryPath + "/" + project.getProjectCode() + "/" + mockServiceResponse.getResponseBodyFile(), e);
                    }
                }
            } else if (model instanceof ExpectedServiceRequest) {
                ExpectedServiceRequest expectedServiceRequest = (ExpectedServiceRequest)model;
                if (expectedServiceRequest.getExpectedServiceRequest() != null) {
                    try {
                        if (expectedServiceRequest.getExpectedServiceRequestFile() == null) {
                            expectedServiceRequest.setExpectedServiceRequestFile("scenarios/" + expectedServiceRequest.getStep().getScenario().getId() + "/steps/" + expectedServiceRequest.getStep().getId() + "/expected-service-response-" + expectedServiceRequest.getId() + ".json");
                        }
                        File file = new File(directoryPath + "/" + project.getProjectCode() + "/" + expectedServiceRequest.getExpectedServiceRequestFile());
                        FileUtils.writeStringToFile(file, expectedServiceRequest.getExpectedServiceRequest(), FILE_ENCODING);
                        expectedServiceRequest.setExpectedServiceRequest(null);
                    } catch (IOException e) {
                        LOGGER.error("Save file " + directoryPath + "/" + project.getProjectCode() + "/" + expectedServiceRequest.getExpectedServiceRequestFile(), e);
                    }
                }
            }
        }, modelList -> {});
    }

    private void checkAndRepairSort(List<Project> projectList) {
        applyToAllProjects(
                projectList,
                model -> {},
                modelList -> modelList.sort((o1, o2) -> Long.compare(o1.getSort() != null ? o1.getSort() : 0L, o2.getSort() != null ? o2.getSort() : 0L)));
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
        if (project.getScenarios() != null) {
            methodToList.method(project.getScenarios());
            project.getScenarios().forEach(scenario -> {
                methodToModel.method(scenario);
                if (scenario.getSteps() != null) {
                    methodToList.method(scenario.getSteps());
                    scenario.getSteps().forEach(step -> {
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
