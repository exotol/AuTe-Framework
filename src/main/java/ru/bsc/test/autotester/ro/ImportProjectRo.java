package ru.bsc.test.autotester.ro;

public class ImportProjectRo implements AbstractRo {

    private static final long serialVersionUID = -3629008469971044019L;

    private String projectCode;
    private String yamlContent;

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getYamlContent() {
        return yamlContent;
    }

    public void setYamlContent(String yamlContent) {
        this.yamlContent = yamlContent;
    }
}
