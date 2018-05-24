package ru.bsc.test.at.mock.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by smakarov
 * 26.02.2018 15:26
 */
@Getter
@AllArgsConstructor
@ToString
public class Version {
    private final String implementationVersion;
    private final String implementationDate;

    public static Version unknown() {
        return new Version("UNKNOWN", "UNKNOWN");
    }
}
