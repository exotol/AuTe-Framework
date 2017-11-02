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

@RestController
@RequestMapping("/rest/steps")
public class RestStepController {

    private final StepService stepService;
    private final ScenarioService scenarioService;
    private StepRoMapper stepRoMapper = Mappers.getMapper(StepRoMapper.class);

    @Autowired
    public RestStepController(StepService stepService, ScenarioService scenarioService) {
        this.stepService = stepService;
        this.scenarioService = scenarioService;
    }

    @RequestMapping(value = "{stepId}", method = RequestMethod.PUT)
    public StepRo updateOne(@PathVariable Long stepId, @RequestBody StepRo stepRo) {
        stepRo = stepService.updateFromRo(stepId, stepRo);
        if (stepRo != null) {
            return stepRo;
        }
        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "{stepId}/clone", method = RequestMethod.POST)
    public StepRo cloneStep(@PathVariable Long stepId) {
        Step step = stepService.findOne(stepId);
        if (step != null) {
            return stepRoMapper.stepToStepRo(scenarioService.cloneStep(step));
        }
        throw new ResourceNotFoundException();
    }
}
