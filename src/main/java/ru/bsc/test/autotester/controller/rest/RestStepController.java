package ru.bsc.test.autotester.controller.rest;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.exception.ResourceNotFoundException;
import ru.bsc.test.autotester.mapper.StepRoMapper;
import ru.bsc.test.autotester.ro.StepRo;
import ru.bsc.test.autotester.service.ScenarioService;
import ru.bsc.test.autotester.service.StepService;

import java.io.IOException;

@RestController
@RequestMapping("/rest/projects/{projectCode}/scenarios/{scenarioPath}/steps")
public class RestStepController {

    private final StepService stepService;
    private final ScenarioService scenarioService;
    private StepRoMapper stepRoMapper = Mappers.getMapper(StepRoMapper.class);

    @Autowired
    public RestStepController(StepService stepService, ScenarioService scenarioService) {
        this.stepService = stepService;
        this.scenarioService = scenarioService;
    }

    @RequestMapping(value = "{stepCode}", method = RequestMethod.PUT)
    public StepRo updateOne(@PathVariable String projectCode, @PathVariable String scenarioPath, @PathVariable String stepCode, @RequestBody StepRo stepRo) throws IOException {
        stepRo = stepService.updateFromRo(projectCode, scenarioPath, stepCode, stepRo);
        if (stepRo != null) {
            return stepRo;
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{stepCode}/clone", method = RequestMethod.POST)
    public StepRo cloneStep(@PathVariable String projectCode, @PathVariable String scenarioPath, @PathVariable String stepCode) throws IOException {
        Step step = stepService.findOne(projectCode, scenarioPath, stepCode);
        if (step != null) {
            return stepRoMapper.stepToStepRo(scenarioService.cloneStep(projectCode, scenarioPath, step));
        }
        throw new ResourceNotFoundException();
    }
}
