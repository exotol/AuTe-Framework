package ru.bsc.test.autotester.controller.rest;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.autotester.exception.ResourceNotFoundException;
import ru.bsc.test.autotester.mapper.StepRoMapper;
import ru.bsc.test.autotester.ro.StepRo;
import ru.bsc.test.autotester.service.ScenarioService;

import java.io.IOException;

@RestController
@RequestMapping("/rest/projects/{projectCode}/scenarios")
public class RestStepController {

    private final ScenarioService scenarioService;
    private final StepRoMapper stepRoMapper;

    @Autowired
    public RestStepController(ScenarioService scenarioService, StepRoMapper stepRoMapper) {
        this.scenarioService = scenarioService;
        this.stepRoMapper = stepRoMapper;
    }

    @RequestMapping(value = { "{scenarioGroup:.+}/{scenarioCode:.+}/steps/{stepCode:.+}", "{scenarioCode:.+}/steps/{stepCode:.+}" }, method = RequestMethod.PUT)
    public StepRo updateOne(
            @PathVariable String projectCode,
            @PathVariable(required = false) String scenarioGroup,
            @PathVariable String scenarioCode,
            @PathVariable String stepCode,
            @RequestBody StepRo stepRo) throws IOException {
        String scenarioPath = (StringUtils.isEmpty(scenarioGroup) ? "" : scenarioGroup + "/") + scenarioCode;
        StepRo updatedStep = scenarioService.updateStepFromRo(projectCode, scenarioPath, stepCode, stepRo);
        if (updatedStep == null) {
            throw new ResourceNotFoundException();
        }
        return updatedStep;
    }

    @RequestMapping(value = { "{scenarioGroup:.+}/{scenarioCode:.+}/steps/{stepCode:.+}/clone", "{scenarioCode:.+}/steps/{stepCode:.+}/clone" }, method = RequestMethod.POST)
    public StepRo cloneStep(
            @PathVariable String projectCode,
            @PathVariable(required = false) String scenarioGroup,
            @PathVariable String scenarioCode,
            @PathVariable String stepCode) throws IOException {
        String scenarioPath = (StringUtils.isEmpty(scenarioGroup) ? "" : scenarioGroup + "/") + scenarioCode;
        return stepRoMapper.stepToStepRo(scenarioService.cloneStep(projectCode, scenarioPath, stepCode));
    }
}
