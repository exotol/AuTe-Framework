package ru.bsc.test.autotester.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.serializer.AnchorGenerator;
import ru.bsc.test.at.executor.model.AbstractModel;
import ru.bsc.test.at.executor.model.Project;

import javax.annotation.PostConstruct;
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

    private String directoryPath;
    private List<Project> projectList = new LinkedList<>();

    @PostConstruct
    public void loadProjects() {
        LOGGER.debug("Load projects from: {}", directoryPath);
        File[] fileList = new File(directoryPath).listFiles(File::isDirectory);
        if (fileList != null) {
            for (File fileEntry: fileList) {
                LOGGER.debug("Reading directory: {}", fileEntry.getAbsolutePath());
                File mainYaml = new File(fileEntry.getAbsolutePath() + "/" + MAIN_YAML_FILENAME);
                if (mainYaml.exists()) {
                    try {
                        Project loadedProject = new Yaml().loadAs(new FileReader(mainYaml), Project.class);
                        loadedProject.setProjectCode(fileEntry.getName());
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
        checkAndRepairId();
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void save() {
        checkAndRepairSort();
        checkAndRepairId();
        saveToFiles();
    }

    private void saveToFiles() {
        projectList.forEach(projectItem -> {
            try {
                FileWriter writer = new FileWriter(directoryPath + "/" + projectItem.getProjectCode() + "/" + MAIN_YAML_FILENAME);
                DumperOptions dumperOptions = new DumperOptions();
                dumperOptions.setAnchorGenerator(new AutotesterAnchorGenerator());
                new Yaml(dumperOptions).dump(projectItem, writer);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                LOGGER.error("Save file " + directoryPath + projectItem.getProjectCode() + "/" + MAIN_YAML_FILENAME, e);
            }
        });
    }

    private void checkAndRepairSort() {
        applyToAllModels(
                model -> {},
                modelList -> modelList.sort((o1, o2) -> Long.compare(o1.getSort() != null ? o1.getSort() : 0L, o2.getSort() != null ? o2.getSort() : 0L)));
    }

    private void checkAndRepairId() {
        Set<Long> idSet = new LinkedHashSet<>();
        applyToAllModels(model -> collectAndReplaceRepeatsId(idSet, model), modelList -> {});
        applyToAllModels(model -> newIdToModel(idSet, model), modelList -> {});
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

    private void applyToAllModels(IMethodToModel methodToModel, IMethodToList methodToList) {
        projectList.forEach(project -> {
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
        });
    }

    @FunctionalInterface
    interface IMethodToModel {
        void method(AbstractModel model);
    }

    @FunctionalInterface
    interface IMethodToList {
        void method(List<? extends AbstractModel> modelList);
    }

    private class AutotesterAnchorGenerator implements AnchorGenerator {

        private long lastAnchorId = 0;

        @Override
        public String nextAnchor(Node node) {
            if (node instanceof MappingNode) {
                NodeTuple idNode = ((MappingNode) node).getValue()
                        .stream()
                        .filter(nodeTuple -> nodeTuple.getKeyNode() instanceof ScalarNode)
                        .filter(nodeTuple -> "id".equals(((ScalarNode) nodeTuple.getKeyNode()).getValue()))
                        .findAny()
                        .orElse(null);
                if (idNode != null && idNode.getValueNode() instanceof ScalarNode) {
                    String idValue = ((ScalarNode) idNode.getValueNode()).getValue();
                    if (idValue != null) {
                        return "objId" + idValue;
                    }
                }
            }
            return "id" + (lastAnchorId++);
        }
    }
}
