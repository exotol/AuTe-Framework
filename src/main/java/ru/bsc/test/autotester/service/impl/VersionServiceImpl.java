package ru.bsc.test.autotester.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.bsc.test.autotester.service.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author Pavel Golovkin
 */
@Service
public class VersionServiceImpl implements VersionService {
    private static final String BUILD_IMPL_VERSION = "buildnumber";
    private static final String BUILD_DATE = "builddate";

    private static final Logger logger = LoggerFactory.getLogger(VersionServiceImpl.class);

    @Override
    public Version getVersion() {
        return findVersion();
    }

    private Version findVersion() {
        Properties prop = new Properties();
        try {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("version.properties"));
        } catch (Exception e) {
            logger.error("Error while loading properties", e);
        }
        String implVer = prop.getProperty(BUILD_IMPL_VERSION);
        String dateStr = prop.getProperty(BUILD_DATE);
        if (StringUtils.isNotBlank(implVer) && StringUtils.isNotBlank(dateStr)) {
            return new Version(implVer, dateStr);
        }
        return new Version(Version.UNKNOWN, Version.UNKNOWN);
    }
}
