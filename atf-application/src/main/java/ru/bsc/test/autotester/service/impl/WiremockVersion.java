package ru.bsc.test.autotester.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.bsc.test.at.model.Version;

/**
 * Created by smakarov
 * 12.03.2018 17:54
 */
@Getter
@AllArgsConstructor
public class WiremockVersion {
    private final String projectCode;
    private final Version version;
}
