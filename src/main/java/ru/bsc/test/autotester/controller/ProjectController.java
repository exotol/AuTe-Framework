package ru.bsc.test.autotester.controller;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.bsc.test.autotester.model.Project;
import ru.bsc.test.autotester.model.Scenario;
import ru.bsc.test.autotester.model.Step;
import ru.bsc.test.autotester.scenario.parser.ExcelTestScenarioParser;
import ru.bsc.test.autotester.service.ProjectService;
import ru.bsc.test.autotester.service.ScenarioGroupService;
import ru.bsc.test.autotester.service.ScenarioService;
import ru.bsc.test.autotester.service.StepService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

    private ScenarioService scenarioService;
    private StepService stepService;
    private ProjectService projectService;
    private ScenarioGroupService scenarioGroupService;

    @Autowired
    public ProjectController(ScenarioService scenarioService, StepService stepService, ProjectService projectService, ScenarioGroupService scenarioGroupService) {
        this.scenarioService = scenarioService;
        this.stepService = stepService;
        this.projectService = projectService;
        this.scenarioGroupService = scenarioGroupService;
    }

    @RequestMapping("{projectId}")
    public ModelAndView scenarios(
            @PathVariable long projectId,
            @RequestParam(required = false) Long scenarioGroupId
    ) {
        ModelAndView model = new ModelAndView("projectDetail");
        model.addObject("scenarios", scenarioGroupId == null ? scenarioService.findAllByProjectId(projectId) : scenarioService.findAllByProjectIdAndScenarioGroupId(projectId, scenarioGroupId));
        model.addObject("project", projectService.findOne(projectId));
        model.addObject("scenarioGroups", scenarioGroupService.findAllByProjectId(projectId));
        model.addObject("scenarioGroupId", scenarioGroupId == null ? 0 : scenarioGroupId);
        return model;
    }

    @RequestMapping(value = "{projectId}/settings", method = RequestMethod.GET)
    public ModelAndView settings(
            @PathVariable long projectId
    ) {
        ModelAndView model = new ModelAndView("projectSettings");
        model.addObject("project", projectService.findOne(projectId));
        model.addObject("scenarioGroups", scenarioGroupService.findAllByProjectId(projectId));
        model.addObject("projectScenarios", scenarioService.findAllByProjectId(projectId));
        return model;
    }

    @RequestMapping(value = "{projectId}/settings", method = RequestMethod.POST)
    public String settingsPost(
            @PathVariable long projectId,
            @RequestParam String name,
            @RequestParam String serviceUrl,
            @RequestParam Long beforeScenarioId,
            @RequestParam Long afterScenarioId,
            @RequestParam String dbUrl,
            @RequestParam String dbUser,
            @RequestParam String dbPassword
    ) {
        Project project = projectService.findOne(projectId);
        project.setName(name);
        project.setServiceUrl(serviceUrl);
        project.setBeforeScenarioId(beforeScenarioId);
        project.setAfterScenarioId(afterScenarioId);
        project.setDbUrl(dbUrl);
        project.setDbUser(dbUser);
        project.setDbPassword(dbPassword);

        projectService.save(project);

        return "redirect:/project/" + projectId + "/settings";
    }

    @RequestMapping(value = "{projectId}/admin", method = RequestMethod.GET)
    public ModelAndView admin(@PathVariable long projectId) {
        ModelAndView model = new ModelAndView("projectAdmin");
        model.addObject("project", projectService.findOne(projectId));
        return model;
    }

    @RequestMapping(value = "{projectId}/admin/parse-all-scenarios-expected-service-requests-jmba", method = RequestMethod.POST)
    public String adminParseScenarios(
            @PathVariable long projectId,
            @RequestParam String expectedRequestsBaseDir
    ) {
        Project project = projectService.findOne(projectId);

        List<Scenario> projectScenarios = scenarioService.findAllByProjectId(project.getId());
        for (Scenario scenario: projectScenarios) {
            scenarioService.parseExpectedServiceRequestsJmba(expectedRequestsBaseDir, scenario);
        }

        return "redirect:/project/" + projectId + "/admin";
    }


    @RequestMapping(value = "{projectId}/execute-scenarios", method = RequestMethod.POST)
    public ModelAndView execute(
            @PathVariable long projectId,
            @RequestParam("scenarios[]") Long[] scenarios
    ) {
        List<Scenario> scenarioResultList = scenarioService.executeScenarioList(scenarios);

        ModelAndView model = new ModelAndView("projectDetail");
        model.addObject("executeResult", 1);
        model.addObject("scenarios", scenarioResultList);
        model.addObject("project", projectService.findOne(projectId));
        model.addObject("scenarioGroups", scenarioGroupService.findAllByProjectId(projectId));
        return model;
    }

    @RequestMapping(value = "{projectId}/export-to-excel", method = RequestMethod.POST)
    public void exportToExcel(
            @PathVariable long projectId,
            @RequestParam("scenarios[]") long[] scenarios,
            HttpServletResponse response
    ) throws IOException {
        String fileName = null;


        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet();
        int rowIndex = 0;
        Row row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue("SERVICE_RELATIVE_PATH");
        row.createCell(1).setCellValue("REQUEST");
        row.createCell(2).setCellValue("MOCK_RESPONSES_FOR_REQUEST");
        row.createCell(3).setCellValue("EXPECTED_RESPONSE_FROM_PORTAL");
        row.createCell(4).setCellValue("SAVING_VALUES");

        for (long scenarioId: scenarios) {

            Scenario scenario = scenarioService.findOne(scenarioId);
            if (scenario != null) {
                if (fileName == null) {
                    fileName = scenario.getName();
                }

                sheet.createRow(rowIndex++).createCell(0).setCellValue("# " + scenario.getName());

                for (Step step : stepService.findAllByScenarioId(scenario.getId())) {
                    row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(step.getRelativeUrl());
                    row.createCell(1).setCellValue(step.getRequest());
                    row.createCell(2).setCellValue(step.getResponses());
                    row.createCell(3).setCellValue(step.getExpectedResponse());
                    row.createCell(4).setCellValue(step.getSavingValues());
                }
            }
        }


        response.addHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        book.write(response.getOutputStream());
    }

    @RequestMapping(value = "{projectId}/import-from-excel", method = RequestMethod.POST)
    public String importScenarioFromExcel(
            @PathVariable long projectId,
            @RequestParam Long scenarioGroup,
            @RequestParam("excelFile") MultipartFile excelFile
    ) throws IOException {
        ExcelTestScenarioParser excelTestScenarioParser = new ExcelTestScenarioParser(excelFile.getInputStream());
        List<List<Step>> scenarioList = excelTestScenarioParser.parse();

        int scenarioIndex = 1;
        for (List<Step> scenario: scenarioList) {
            Scenario scenarioModel = new Scenario();
            scenarioModel.setProjectId(projectId);
            scenarioModel.setScenarioGroupId(scenarioGroup);
            scenarioModel.setName(excelFile.getOriginalFilename() + " " + scenarioIndex++);
            scenarioModel = scenarioService.save(scenarioModel);

            Long i = 0L;
            for (Step step: scenario) {
                step.setScenarioId(scenarioModel.getId());
                step.setSort(50 * ++i);
            }
            stepService.saveSteps(scenario);
        }

        return "redirect:/project/" + projectId;
    }

    @RequestMapping(value = "{projectId}/add-scenario", method = RequestMethod.POST)
    public String importScenarioFromExcel(
            @RequestParam String name,
            @PathVariable long projectId,
            @RequestParam Long scenarioGroupId
    ) throws IOException {
        Scenario scenario = new Scenario();
        scenario.setName(name);
        scenario.setProjectId(projectId);
        scenario.setScenarioGroupId(scenarioGroupId);
        scenario = scenarioService.save(scenario);
        return "redirect:/scenario/" + scenario.getId();
    }
}
