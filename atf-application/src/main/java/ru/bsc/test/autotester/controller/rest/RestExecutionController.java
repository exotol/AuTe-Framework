package ru.bsc.test.autotester.controller.rest;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bsc.test.autotester.mapper.ExecutionResultRoMapper;
import ru.bsc.test.autotester.mapper.StepRoMapper;
import ru.bsc.test.autotester.ro.ExecutionResultRo;
import ru.bsc.test.autotester.ro.ScenarioIdentityRo;
import ru.bsc.test.autotester.ro.StepResultRo;
import ru.bsc.test.autotester.service.ScenarioService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.zip.ZipOutputStream;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by sdoroshin on 23.01.2018.
 *
 */

@RestController
@RequestMapping("/rest/execution/")
public class RestExecutionController {

    private final Gson gson = new Gson();
    private final ScenarioService scenarioService;
    private final ExecutionResultRoMapper executionResultRoMapper;
    private final StepRoMapper stepRoMapper;

    @Autowired
    public RestExecutionController(
            ScenarioService scenarioService,
            ExecutionResultRoMapper executionResultRoMapper,
            StepRoMapper stepRoMapper
    ) {
        this.scenarioService = scenarioService;
        this.executionResultRoMapper = executionResultRoMapper;
        this.stepRoMapper = stepRoMapper;
    }

    @RequestMapping(value = "{executionUuid}/stop", method = POST)
    public void stopExecuting(@PathVariable String executionUuid) {
        scenarioService.stopExecuting(executionUuid);
    }

    @RequestMapping(value = "{executionUuid}/status", method = GET)
    public ExecutionResultRo getStatus(@PathVariable String executionUuid) {
        return executionResultRoMapper.map(scenarioService.getResult(executionUuid));
    }

    @RequestMapping(value = "/results", method = POST)
    public List<StepResultRo> getResults(@RequestBody ScenarioIdentityRo identity) {
        return stepRoMapper.convertStepResultListToStepResultRoWithDiff(scenarioService.getResult(identity));
    }

    @RequestMapping(value = "/report", method = POST, produces="application/zip")
    public void getReport(@RequestBody List<ScenarioIdentityRo> identities, HttpServletResponse response) throws Exception {
        response.addHeader("Content-Disposition", "attachment; filename=\"report.zip\"");
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            scenarioService.getReport(identities, zipOutputStream);
        }
    }

    @RequestMapping(value = "list", method = GET)
    public String list() {
        return gson.toJson(scenarioService.getExecutingList());
    }
}
