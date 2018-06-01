package ru.bsc.test.autotester.service;

import ru.bsc.test.at.model.Version;
import ru.bsc.test.autotester.service.impl.WiremockVersion;

import java.util.List;

/**
 * @author Pavel Golovkin
 */
public interface VersionService {

    Version getApplicationVersion();

    List<WiremockVersion> getWiremockVersions();
}
