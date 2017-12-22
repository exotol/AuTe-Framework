package ru.bsc.test.autotester.properties;

import java.util.Map;

public class EnvironmentProperties {
    private String projectsDirectoryPath;
    private Map<String, StandProperties> projectStandMap;

    public String getProjectsDirectoryPath() {
        return projectsDirectoryPath;
    }

    public void setProjectsDirectoryPath(String projectsDirectoryPath) {
        this.projectsDirectoryPath = projectsDirectoryPath;
    }

    public Map<String, StandProperties> getProjectStandMap() {
        return projectStandMap;
    }

    public void setProjectStandMap(Map<String, StandProperties> projectStandMap) {
        this.projectStandMap = projectStandMap;
    }
}
