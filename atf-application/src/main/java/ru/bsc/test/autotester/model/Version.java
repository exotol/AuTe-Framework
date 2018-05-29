package ru.bsc.test.autotester.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.Thread.currentThread;

/**
 * Created by smakarov
 * 26.02.2018 15:26
 */
@Getter
@ToString
@AllArgsConstructor
@Slf4j
public class Version {
    private final String implementationVersion;
    private final String implementationDate;

    private static final String VERSION_FILE_SUFFIX = ".version.properties";

    public static Version load(String versionFileName) {
        Version version;
        String versionFile = versionFileName + VERSION_FILE_SUFFIX;
        try (InputStream resource = currentThread().getContextClassLoader().getResourceAsStream(versionFile)) {
            Properties properties = new Properties();
            properties.load(resource);
            String implementationVersion = String.format(
                    "%s.%s.%s.%s",
                    properties.getProperty("git.build.version"),
                    properties.getProperty("git.branch"),
                    properties.getProperty("git.closest.tag.commit.count"),
                    properties.getProperty("git.commit.id.abbrev")
            );
            String implementationDate = properties.getProperty("git.build.time");
            version = new Version(implementationVersion, implementationDate);
            log.info("Version loaded from file: {}", version);
        } catch (IOException e) {
            version = Version.unknown();
            log.error("Error while loading '{}' properties", versionFile, e);
        }
        return version;
    }

    public static Version unknown() {
        return new Version();
    }

    public Version() {
        this("UNKNOWN", "UNKNOWN");
    }
}
