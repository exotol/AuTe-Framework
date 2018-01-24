package ru.bsc.test.autotester.controller.rest;

import com.google.gson.Gson;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.autotester.mapper.ProjectRoMapper;
import ru.bsc.test.autotester.mapper.ScenarioRoMapper;
import ru.bsc.test.autotester.mapper.StepRoMapper;
import ru.bsc.test.autotester.ro.ScenarioResultRo;
import ru.bsc.test.autotester.service.ScenarioService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 23.01.2018.
 *
 */

@RestController
@RequestMapping("/rest/execution/")
public class ExecutionController {

    private StepRoMapper stepRoMapper = Mappers.getMapper(StepRoMapper.class);
    private ProjectRoMapper projectRoMapper = Mappers.getMapper(ProjectRoMapper.class);
    private final ScenarioService scenarioService;

    @Autowired
    public ExecutionController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @RequestMapping(value = "{executionUuid}/stop", method = RequestMethod.POST)
    public void stopExecuting(@PathVariable String executionUuid) {
        scenarioService.stopExecuting(executionUuid);
    }

    @RequestMapping(value = "{executionUuid}/result", method = RequestMethod.GET)
    public List<ScenarioResultRo> getResult(@PathVariable String executionUuid) {
        return scenarioService.getResult(executionUuid)
                .entrySet()
                .stream()
                .map(scenarioListEntry -> new ScenarioResultRo()
                        .withScenario(projectRoMapper.scenarioToScenarioRo("", scenarioListEntry.getKey()))
                        .withStepResultRo(stepRoMapper.convertStepResultListToStepResultRo(scenarioListEntry.getValue()))
                )
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list() {
        return new Gson().toJson(scenarioService.getExecutingList());
    }
}
