package ru.bsc.test.at.mock.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.at.mock.model.Version;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
@RestController
public class VersionController {

    private static final String VERSION_PROPERTIES = "version.properties";

    private Version version;

    public VersionController() {
        Properties properties = new Properties();
        try (InputStream resourceAsStream =
                     Thread.currentThread().getContextClassLoader().getResourceAsStream(VERSION_PROPERTIES)) {
            properties.load(resourceAsStream);
            version = new Version(properties.getProperty("buildnumber"), properties.getProperty("builddate"));
            log.info("Version loaded from file: {}", version);
        } catch (IOException e) {
            version = Version.unknown();
            log.error("Error while loading version properties", e);
        }
    }

    @GetMapping("/__version")
    public Version version() {
        return version;
    }
}
