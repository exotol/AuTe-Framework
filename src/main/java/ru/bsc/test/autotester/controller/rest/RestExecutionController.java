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

import javax.servlet.http.HttpServletResponse;
import java.util.zip.ZipOutputStream;

/**
 * Created by sdoroshin on 23.01.2018.
 *
 */

@RestController
@RequestMapping("/rest/execution/")
public class RestExecutionController {

    private ExecutionResultRoMapper executionResultRoMapper = Mappers.getMapper(ExecutionResultRoMapper.class);
    private final ScenarioService scenarioService;

    @Autowired
    public RestExecutionController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @RequestMapping(value = "{executionUuid}/stop", method = RequestMethod.POST)
    public void stopExecuting(@PathVariable String executionUuid) {
        scenarioService.stopExecuting(executionUuid);
    }

    @RequestMapping(value = "{executionUuid}/status", method = RequestMethod.GET)
    public ExecutionResultRo getStatus(@PathVariable String executionUuid) {
        return executionResultRoMapper.map(scenarioService.getResult(executionUuid));
    }

    @RequestMapping(value = "{executionUuid}/report", method = RequestMethod.GET, produces="application/zip")
    public void getReport(@PathVariable String executionUuid, HttpServletResponse response) throws Exception {

        response.addHeader("Content-Disposition", "attachment; filename=\"test.zip\"");

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            scenarioService.getReport(executionUuid, zipOutputStream);
        }
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list() {
        return new Gson().toJson(scenarioService.getExecutingList());
    }
}
