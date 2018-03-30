package ru.bsc.test.autotester.controller.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bsc.test.autotester.diff.Diff;
import ru.bsc.test.autotester.diff.DiffMatchPatch;
import ru.bsc.test.autotester.mapper.ExecutionResultRoMapper;
import ru.bsc.test.autotester.ro.ExecutionResultRo;
import ru.bsc.test.autotester.ro.MultipleReportsRequestRo;
import ru.bsc.test.autotester.service.ScenarioService;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.zip.ZipOutputStream;

/**
 * Created by sdoroshin on 23.01.2018.
 *
 */

@RestController
@RequestMapping("/rest/execution/")
public class RestExecutionController {

    private static Logger log = LoggerFactory.getLogger(RestExecutionController.class);

    private final ScenarioService scenarioService;
    private final ExecutionResultRoMapper executionResultRoMapper;

    @Autowired
    public RestExecutionController(ScenarioService scenarioService, ExecutionResultRoMapper executionResultRoMapper) {
        this.scenarioService = scenarioService;
        this.executionResultRoMapper = executionResultRoMapper;
    }

    @RequestMapping(value = "{executionUuid}/stop", method = RequestMethod.POST)
    public void stopExecuting(@PathVariable String executionUuid) {
        scenarioService.stopExecuting(executionUuid);
    }

    @RequestMapping(value = "{executionUuid}/status", method = RequestMethod.GET)
    public ExecutionResultRo getStatus(@PathVariable String executionUuid) {
        ExecutionResultRo map = executionResultRoMapper.map(scenarioService.getResult(executionUuid));
        return addDiffToResult(map);
    }

    private ExecutionResultRo addDiffToResult(ExecutionResultRo map) {
         map.getScenarioResultList().forEach(rl -> rl.getStepResultList().forEach( stepResultRo -> {
            if(stepResultRo.getActual() == null || stepResultRo.getExpected() == null){
                return;
            }

             DiffMatchPatch dmp = new DiffMatchPatch();
             Gson gson = new GsonBuilder().setPrettyPrinting().create();
             JsonParser parser = new JsonParser();
             JsonElement elA;
             try {
                 elA = parser.parse(stepResultRo.getActual());
                 String actualJson = gson.toJson(elA);
                 stepResultRo.setActual(actualJson);
             }catch (Exception e){}

             JsonElement elE;

             try {
                 elE = parser.parse(stepResultRo.getExpected());
                 String expectedJson = gson.toJson(elE);
                 stepResultRo.setExpected(expectedJson);
             }catch (Exception e){}

             LinkedList<Diff> diff = dmp.diff_main(stepResultRo.getExpected(), stepResultRo.getActual());
             stepResultRo.setDiff(diff);

        }));



        return map;
    }

    @RequestMapping(value = "{executionUuid}/report", method = RequestMethod.GET, produces="application/zip")
    public void getReport(@PathVariable String executionUuid, HttpServletResponse response) throws Exception {

        response.addHeader("Content-Disposition", "attachment; filename=\"report.zip\"");

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            scenarioService.getReport(executionUuid, zipOutputStream);
        }
    }

    @RequestMapping(value = "multiple-reports", method = RequestMethod.POST, produces="application/zip")
    public void getMultipleReports(@RequestBody MultipleReportsRequestRo multipleReportsRequestRo, HttpServletResponse response) throws Exception {

        response.addHeader("Content-Disposition", "attachment; filename=\"report.zip\"");

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            scenarioService.getReportList(multipleReportsRequestRo.getExecutionUuidList(), zipOutputStream);
        }
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list() {
        return new Gson().toJson(scenarioService.getExecutingList());
    }
}
