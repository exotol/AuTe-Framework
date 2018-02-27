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
    private static final String UNKNOWN = "UNKNOWN";

    private String implementationVersion;
    private String implementationDate;

    public static Version unknown() {
        return new Version(UNKNOWN, UNKNOWN);
    }
}
