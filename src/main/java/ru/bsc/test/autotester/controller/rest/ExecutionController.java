package ru.bsc.test.autotester.controller.rest;

import com.google.gson.Gson;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.autotester.mapper.ExecutionResultRoMapper;
import ru.bsc.test.autotester.ro.ExecutionResultRo;
import ru.bsc.test.autotester.service.ScenarioService;

/**
 * Created by sdoroshin on 23.01.2018.
 *
 */

@RestController
@RequestMapping("/rest/execution/")
public class ExecutionController {

    private ExecutionResultRoMapper executionResultRoMapper = Mappers.getMapper(ExecutionResultRoMapper.class);
    private final ScenarioService scenarioService;

    @Autowired
    public ExecutionController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @RequestMapping(value = "{executionUuid}/stop", method = RequestMethod.POST)
    public void stopExecuting(@PathVariable String executionUuid) {
        scenarioService.stopExecuting(executionUuid);
    }

    @RequestMapping(value = "{executionUuid}/status", method = RequestMethod.GET)
    public ExecutionResultRo getResult(@PathVariable String executionUuid) {
        return executionResultRoMapper.map(scenarioService.getResult(executionUuid));
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list() {
        return new Gson().toJson(scenarioService.getExecutingList());
    }
}
