package ru.bsc.test.autotester.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.bsc.test.autotester.service.VersionService;

import java.util.Properties;

/**
 * @author Pavel Golovkin
 */
@Service
@Slf4j
public class VersionServiceImpl implements VersionService {
    private static final String BUILD_IMPL_VERSION = "buildnumber";
    private static final String BUILD_DATE = "builddate";
    private static final String MANAGER_VERSION_PROPERTIES = "version.properties";
    private static final String EXECUTOR_VERSION_PROPERTIES = "at-executor.version.properties";

    private Version managerVersion;
    private Version executorVersion;

    @Override
    public Version getManagerVersion() {
        if (managerVersion == null) {
            managerVersion = findVersion(MANAGER_VERSION_PROPERTIES);
        }
        return managerVersion;
    }

    public Version getExecutorVersion() {
        if (executorVersion == null) {
            executorVersion = findVersion(EXECUTOR_VERSION_PROPERTIES);
        }
        return executorVersion;
    }

    private Version findVersion(String propertyFile) {
        Properties prop = new Properties();
        try {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFile));
            String version = prop.getProperty(BUILD_IMPL_VERSION);
            String date = prop.getProperty(BUILD_DATE);
            if (StringUtils.isNotBlank(version) && StringUtils.isNotBlank(date)) {
                return new Version(version, date);
            }
        } catch (Exception e) {
            log.error("Error while loading properties: {}", propertyFile, e);
        }
        return Version.unknown();
    }
}
