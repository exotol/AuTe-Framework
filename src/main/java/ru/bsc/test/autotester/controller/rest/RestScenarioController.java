package ru.bsc.test.autotester.controller.rest;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.autotester.mapper.StepRoMapper;
import ru.bsc.test.autotester.ro.StepRo;
import ru.bsc.test.autotester.service.ScenarioService;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */

@RestController
@RequestMapping("/rest/scenarios")
public class RestScenarioController {

    private StepRoMapper stepRoMapper = Mappers.getMapper(StepRoMapper.class);
    private final ScenarioService scenarioService;

    @Autowired
    public RestScenarioController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @RequestMapping(value = "{scenarioId}/steps", method = RequestMethod.GET)
    public List<StepRo> findOne(@PathVariable Long scenarioId) {
        Scenario scenario = scenarioService.findOne(scenarioId);
        if (scenario != null) {
            return stepRoMapper.convertStepRoListToStepList(scenario.getSteps());
        }
        // TODO: return 404 error
        return null;
    }
}
