package ru.bsc.test.autotester.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.bsc.test.autotester.service.VersionService;

import java.util.Properties;

/**
 * @author Pavel Golovkin
 */
@Service
public class VersionServiceImpl implements VersionService {
	private static final String BUILD_IMPL_VERSION = "buildnumber";
	private static final String BUILD_DATE = "builddate";

	@Override
	public Version getVersion() {
		return findVersion();
	}

	private Version findVersion() {
		try {
			Properties prop = new Properties();
			prop.load(VersionServiceImpl.class.getClassLoader().getResourceAsStream("version.properties"));

			String implVer = prop.getProperty(BUILD_IMPL_VERSION);
			String dateStr = prop.getProperty(BUILD_DATE);
			if (StringUtils.isNotBlank(implVer) && StringUtils.isNotBlank(dateStr)) {
				return new Version(implVer, dateStr);
			}
		} catch (Exception e) {
			//TODO LOGGING
		}
		return new Version(Version.UNKNOWN, Version.UNKNOWN);
	}
}
