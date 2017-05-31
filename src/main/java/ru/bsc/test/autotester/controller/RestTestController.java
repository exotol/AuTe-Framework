package ru.bsc.test.autotester.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.autotester.model.Step;
import ru.bsc.test.autotester.service.StepService;

import java.util.List;

/**
 * Created by sdoroshin on 22.05.2017.
 *
 */
@RestController
@RequestMapping("/rest")
public class RestTestController {

    private StepService stepService;

    public RestTestController(StepService stepService) {
        this.stepService = stepService;
    }

    @RequestMapping(value = "step/save", method = RequestMethod.POST)
    public List<Step> save(@RequestBody List<Step> step) {
        return stepService.saveSteps(step);
    }

}
