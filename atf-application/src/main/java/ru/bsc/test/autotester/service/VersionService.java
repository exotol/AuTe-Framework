package ru.bsc.test.autotester.service;

import ru.bsc.test.autotester.service.impl.WiremockVersion;
import ru.bsc.test.autotester.model.Version;

import java.util.List;

/**
 * @author Pavel Golovkin
 */
public interface VersionService {

    Version getApplicationVersion();

    List<WiremockVersion> getWiremockVersions();
}
