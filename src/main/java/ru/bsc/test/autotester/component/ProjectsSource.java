package ru.bsc.test.autotester.component;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import ru.bsc.test.at.executor.model.Project;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 27.10.2017.
 *
 */

@Component
public class ProjectsSource {

    private String directoryPath;
    private List<Project> projectList = new LinkedList<>();

    @PostConstruct
    public void loadProjects() {

        File[] fileList = new File(directoryPath).listFiles();
        if (fileList != null) {
            for (File fileEntry : fileList) {
                if (fileEntry.isDirectory()) {
                    File mainYaml = new File(fileEntry.getAbsolutePath() + "/main.yml");
                    if (mainYaml.exists()) {
                        try {
                            Project loadedProject = new Yaml().loadAs(new FileReader(mainYaml), Project.class);
                            projectList.add(loadedProject);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
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
}
