package ru.bsc.test.autotester.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public Version getVersion() {
        return findVersion();
    }

    private Version findVersion() {
        Properties prop = new Properties();
        try {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("version.properties"));
        } catch (Exception e) {
            log.error("Error while loading properties", e);
        }
        String implVer = prop.getProperty(BUILD_IMPL_VERSION);
        String dateStr = prop.getProperty(BUILD_DATE);
        if (StringUtils.isNotBlank(implVer) && StringUtils.isNotBlank(dateStr)) {
            return new Version(implVer, dateStr);
        }
        return new Version(Version.UNKNOWN, Version.UNKNOWN);
    }
}
