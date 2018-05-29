package ru.bsc.test.autotester.service.impl;

import lombok.Getter;
import ru.bsc.test.autotester.model.Version;

/**
 * Created by smakarov
 * 12.03.2018 17:54
 */
@Getter
public class WiremockVersion {
    private final String projectCode;
    private final Version version;

    public WiremockVersion(String projectCode, Version version) {
        this.projectCode = projectCode;
        this.version = version;
    }
}
