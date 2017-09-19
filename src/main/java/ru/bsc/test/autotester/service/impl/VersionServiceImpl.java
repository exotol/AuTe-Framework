package ru.bsc.test.autotester.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import ru.bsc.test.autotester.service.VersionService;

import java.util.Map;
import java.util.Optional;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * @author Pavel Golovkin
 */
@Service
public class VersionServiceImpl implements VersionService {
	private static final String BUILD_IMPL_VERSION = "Implementation-Version";
	private static final String BUILD_DATE = "Implementation-Build-Date";

	@Override
	public Version getVersion() {
		return findVersion();
	}

	private Version findVersion() {
		try {
			Optional<Version> version;
			Resource[] resources = new PathMatchingResourcePatternResolver()
					.getResources("classpath*:META-INF/MANIFEST.MF");
			for (Resource resource : resources) {
				Manifest mf = new Manifest(resource.getInputStream());
				version = findVersionInManifest(mf);
				if (version.isPresent()) {
					return version.get();
				}
			}
		} catch (Exception e) {
			//TODO LOGGING
		}
		return new Version(Version.UNKNOWN, Version.UNKNOWN);
	}

	private Optional<Version> findVersionInManifest(Manifest manifest) throws Exception {
		String implVer = findKey(manifest, BUILD_IMPL_VERSION);
		String dateStr = findKey(manifest, BUILD_DATE);
		if (StringUtils.isNotBlank(implVer) && StringUtils.isNotBlank(dateStr)) {
			return Optional.of(new Version(implVer, dateStr));
		} else {
			return Optional.empty();
		}
	}

	private String findKey(Manifest manifest, String key) throws Exception {
		for (Map.Entry<String, Attributes> entry : manifest.getEntries().entrySet()) {
			String value = entry.getValue().getValue(key);
			if (value != null) {
				return value;
			}
		}
		return null;
	}
}
