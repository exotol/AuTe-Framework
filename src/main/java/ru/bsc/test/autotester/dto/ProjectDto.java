package ru.bsc.test.autotester.dto;

import ru.bsc.test.at.executor.model.Project;

/**
 * Created by sdoroshin on 12.09.2017.
 *
 */
public class ProjectDto {

    private Long id;
    private String name;
    private String projectCode;
    private Boolean useRandomTestId;
    private String testIdHeaderName;

    public void mergeTo(Project project) {
        project.setName(getName());
        project.setProjectCode(getProjectCode());
        project.setUseRandomTestId(getUseRandomTestId());
        project.setTestIdHeaderName(getTestIdHeaderName());
        // TODO: other Project properties
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public Boolean getUseRandomTestId() {
        return useRandomTestId;
    }

    public void setUseRandomTestId(Boolean useRandomTestId) {
        this.useRandomTestId = useRandomTestId;
    }

    public String getTestIdHeaderName() {
        return testIdHeaderName;
    }

    public void setTestIdHeaderName(String testIdHeaderName) {
        this.testIdHeaderName = testIdHeaderName;
    }
}
