package ru.bsc.test.autotester.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.autotester.service.VersionService;
import ru.bsc.test.autotester.service.impl.Version;

/**
 * Created by sdoroshin on 23.10.2017.
 *
 */

@RestController
@RequestMapping("/rest/version")
public class RestVersionController {

    private final VersionService versionService;

    @Autowired
    public RestVersionController(VersionService versionService) {
        this.versionService = versionService;
    }

    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public Version managerVersion() {
        return versionService.getManagerVersion();
    }

    @RequestMapping(value = "/executor", method = RequestMethod.GET)
    public Version executorVersion() {
        return versionService.getExecutorVersion();
    }
}
