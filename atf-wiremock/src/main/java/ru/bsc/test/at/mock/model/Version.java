package ru.bsc.test.at.mock.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.jar.JarFile;

import static java.lang.Thread.currentThread;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by smakarov
 * 26.02.2018 15:26
 */

//TODO: вынести этот класс в общий модуль

@Getter
@ToString
@AllArgsConstructor
@Slf4j
@SuppressWarnings("Duplicates")
public class Version {
    private final String implementationVersion;
    private final String implementationDate;

    public static Version load() {
        ClassLoader classLoader = currentThread().getContextClassLoader();
        try (InputStream manifest = classLoader.getResourceAsStream(JarFile.MANIFEST_NAME)) {
            Properties properties = new Properties();
            properties.load(manifest);
            return buildVersionFromProperties(properties);
        } catch (IOException e) {
            log.error("Error while reading MANIFEST.MF", e);
            return Version.unknown();
        }
    }

    private static Version buildVersionFromProperties(Properties properties) {
        String projectVersion = properties.getProperty("Project-Version");
        String commitsCount = properties.getProperty("Git-Commits-Count");
        String shortRevision = properties.getProperty("Git-Short-Revision");
        if (isEmpty(projectVersion) || isEmpty(commitsCount) || isEmpty(shortRevision)) {
            log.warn("Version information is not available. Build the project using maven.");
            return Version.unknown();
        }
        String implementationVersion = String.format("%s.%s.%s", projectVersion, commitsCount, shortRevision);
        String implementationDate = properties.getProperty("Build-Time");
        Version version = new Version(implementationVersion, implementationDate);
        log.info("Version loaded from MANIFEST.MF: {}", version);
        return version;
    }

    public static Version unknown() {
        return new Version();
    }

    public Version() {
        this("unknown", "");
    }
}
