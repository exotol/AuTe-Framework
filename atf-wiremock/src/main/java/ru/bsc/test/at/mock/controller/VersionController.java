package ru.bsc.test.at.mock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.at.mock.model.Version;

@RestController
public class VersionController {

    private static final String WIREMOCK_VERSION = "atf-wiremock";

    private final Version version = Version.load(WIREMOCK_VERSION);

    @GetMapping("/__version")
    public Version version() {
        return version;
    }
}
