package ru.bsc.test.autotester.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import ru.bsc.test.at.executor.model.Project;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void save() {
        saveToFiles();
    }

    private void saveToFiles() {
        projectList.forEach(projectItem -> {
            try {
                FileWriter writer = new FileWriter(directoryPath + "/" + projectItem.getProjectCode() + "/" + MAIN_YAML_FILENAME);
                new Yaml().dump(projectItem, writer);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                LOGGER.error("Save file " + directoryPath + projectItem.getProjectCode() + "/" + MAIN_YAML_FILENAME, e);
            }
        });
    }
}
