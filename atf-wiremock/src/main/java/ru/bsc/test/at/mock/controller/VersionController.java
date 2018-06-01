package ru.bsc.test.at.mock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.at.model.Version;

@RestController
public class VersionController {

    private final Version version = Version.load();

    @GetMapping("/__version")
    public Version version() {
        return version;
    }
}
