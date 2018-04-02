package ru.bsc.test.autotester.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class EnvironmentProperties {
    private String projectsDirectoryPath;
    private Map<String, StandProperties> projectStandMap;
    private Integer mqCheckCount;
    private Long mqCheckInterval;
}
