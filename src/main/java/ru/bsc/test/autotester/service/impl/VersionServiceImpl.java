package ru.bsc.test.autotester.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.properties.StandProperties;
import ru.bsc.test.autotester.service.VersionService;

import java.util.ArrayList;
import java.util.List;
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
    private static final String WIREMOCK_VERSION_PATH = "__version";

    private final List<WiremockVersion> wiremockVersions = new ArrayList<>();

    private Version managerVersion;
    private Version executorVersion;

    @Autowired
    public VersionServiceImpl(EnvironmentProperties env, RestTemplateBuilder builder) {
        loadProjectsWiremockVersion(env, builder);
    }

    @Override
    public Version getManagerVersion() {
        if (managerVersion == null) {
            managerVersion = findVersion(MANAGER_VERSION_PROPERTIES);
        }
        return managerVersion;
    }

    @Override
    public Version getExecutorVersion() {
        if (executorVersion == null) {
            executorVersion = findVersion(EXECUTOR_VERSION_PROPERTIES);
        }
        return executorVersion;
    }

    @Override
    public List<WiremockVersion> getWiremockVersions() {
        return wiremockVersions;
    }

    private void loadProjectsWiremockVersion(EnvironmentProperties env, RestTemplateBuilder builder) {
        RestTemplate template = builder.build();
        env.getProjectStandMap().forEach((projectName, properties) -> {
            try {
                if (StringUtils.isNotEmpty(properties.getWireMockUrl())) {
                    String url = getVersionUrl(properties);
                    Version version = template.getForObject(url, Version.class);
                    wiremockVersions.add(new WiremockVersion(projectName, version));
                }
            } catch (RestClientException e) {
                wiremockVersions.add(new WiremockVersion(projectName, Version.unknown()));
                log.error("Error while fetching project wiremock version", e);
            }
        });
    }

    private String getVersionUrl(StandProperties properties) {
        return properties.getWireMockUrl() +
               (properties.getWireMockUrl().endsWith("/") ? "" : "/") +
               WIREMOCK_VERSION_PATH;
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
