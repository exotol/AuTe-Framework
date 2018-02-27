package ru.bsc.test.autotester.service;

import ru.bsc.test.autotester.service.impl.Version;

/**
 * @author Pavel Golovkin
 */
public interface VersionService {

    Version getManagerVersion();

    Version getExecutorVersion();
}
