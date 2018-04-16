package ru.bsc.test.autotester.report.impl.allure.plugin;

import io.qameta.allure.Context;
import lombok.Setter;

/**
 * Created by smakarov
 * 16.04.2018 14:44
 */
public class ProjectContext implements Context<String> {

    @Setter
    private String projectCode;

    @Override
    public String getValue() {
        return projectCode;
    }
}
