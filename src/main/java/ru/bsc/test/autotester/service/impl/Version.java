package ru.bsc.test.autotester.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Pavel Golovkin
 */
@AllArgsConstructor
@Getter
@Setter
public class Version {
    static final String UNKNOWN = "UNKNOWN";

    private String implementationVersion;
    private String implementationDate;
}
