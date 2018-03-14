package ru.bsc.test.autotester.service;

import ru.bsc.test.autotester.service.impl.WiremockVersion;
import ru.bsc.test.autotester.service.impl.Version;

import java.util.List;

/**
 * @author Pavel Golovkin
 */
public interface VersionService {

    Version getManagerVersion();

    Version getExecutorVersion();

    List<WiremockVersion> getWiremockVersions();
}
