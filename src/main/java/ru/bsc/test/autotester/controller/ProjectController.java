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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.ScenarioGroup;
import ru.bsc.test.at.executor.model.Stand;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.autotester.scenario.parser.ExcelTestScenarioParser;
import ru.bsc.test.autotester.service.ProjectService;
import ru.bsc.test.autotester.service.ScenarioGroupService;
import ru.bsc.test.autotester.service.ScenarioService;
import ru.bsc.test.autotester.service.StandService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

    private ScenarioService scenarioService;
    private ProjectService projectService;
    private final ScenarioGroupService scenarioGroupService;
    private final StandService standService;

    @Autowired
    public ProjectController(ScenarioService scenarioService, ProjectService projectService, ScenarioGroupService scenarioGroupService, StandService standService) {
        this.scenarioService = scenarioService;
        this.projectService = projectService;
        this.scenarioGroupService = scenarioGroupService;
        this.standService = standService;
    }

    @RequestMapping("{projectId}")
    public ModelAndView scenarios(
            @PathVariable long projectId,
            @RequestParam(required = false) Long scenarioGroupId
    ) {
        Project project = projectService.findOne(projectId);
        ModelAndView model = new ModelAndView("projectDetail");
        model.addObject("scenarios", scenarioGroupId == null ? project.getScenarios() : scenarioService.findAllByProjectIdAndScenarioGroupId(projectId, scenarioGroupId));
        model.addObject("project", project);
        model.addObject("scenarioGroups", project.getScenarioGroups());
        model.addObject("scenarioGroupId", scenarioGroupId == null ? 0 : scenarioGroupId);
        return model;
    }

    @RequestMapping(value = "{projectId}/groups", method = RequestMethod.GET)
    public ModelAndView groups(
            @PathVariable long projectId
    ) {
        Project project = projectService.findOne(projectId);
        ModelAndView model = new ModelAndView("projectGroups");
        model.addObject("project", project);
        return model;
    }

    @RequestMapping(value = "{projectId}/stands", method = RequestMethod.GET)
    public ModelAndView stands(
            @PathVariable long projectId
    ) {
        Project project = projectService.findOne(projectId);
        ModelAndView model = new ModelAndView("projectStands");
        model.addObject("project", project);
        return model;
    }

    @RequestMapping(value = "{projectId}/settings", method = RequestMethod.GET)
    public ModelAndView settings(
            @PathVariable long projectId
    ) {
        Project project = projectService.findOne(projectId);
        ModelAndView model = new ModelAndView("projectSettings");
        model.addObject("project", project);
        return model;
    }

    @RequestMapping(value = "{projectId}/settings", method = RequestMethod.POST)
    public String settingsPost(
            @PathVariable long projectId,
            @RequestParam String name,
            @RequestParam String serviceUrl,
            @RequestParam Long beforeScenarioId,
            @RequestParam Long afterScenarioId,
            @RequestParam Long standId,
            @RequestParam(required = false) Boolean useRandomTestId,
            @RequestParam String testIdHeaderName
    ) {
        Project project = projectService.findOne(projectId);
        project.setName(name);
        project.setServiceUrl(serviceUrl);
        project.setBeforeScenario(beforeScenarioId == null ? null : scenarioService.findOne(beforeScenarioId));
        project.setAfterScenario(afterScenarioId == null ? null : scenarioService.findOne(afterScenarioId));
        project.setStand(standId == null ? null : standService.findOne(standId));
        project.setUseRandomTestId(Boolean.TRUE.equals(useRandomTestId));
        project.setTestIdHeaderName(testIdHeaderName);

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

        for (Scenario scenario: project.getScenarios()) {
            scenarioService.parseExpectedServiceRequestsJmba(expectedRequestsBaseDir, scenario);
        }

        return "redirect:/project/" + projectId + "/admin";
    }


    @RequestMapping(value = "{projectId}/execute-scenarios", method = RequestMethod.POST)
    public ModelAndView execute(
            @PathVariable long projectId,
            @RequestParam("scenarios[]") Long[] scenarios
    ) {
        Project project = projectService.findOne(projectId);
        List<Scenario> scenarioExecuteList = scenarioService.findAll(Arrays.asList(scenarios));
        List<Scenario> scenarioResultList = scenarioService.executeScenarioList(project, scenarioExecuteList);

        ModelAndView model = new ModelAndView("projectDetail");
        model.addObject("executeResult", 1);
        model.addObject("scenarios", scenarioResultList);
        model.addObject("project", project);
        model.addObject("scenarioGroups", project.getScenarioGroups());
        return model;
    }

    @RequestMapping(value = "{projectId}/export-to-excel", method = RequestMethod.POST)
    public void exportToExcel(
            @PathVariable long projectId,
            @RequestParam("scenarios[]") Long[] scenarios,
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

        for (Scenario scenario: scenarioService.findAll(Arrays.asList(scenarios))) {
            if (fileName == null) {
                fileName = scenario.getName();
            }

            sheet.createRow(rowIndex++).createCell(0).setCellValue("# " + scenario.getName());

            for (Step step : scenario.getSteps()) {
                row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(step.getRelativeUrl());
                row.createCell(1).setCellValue(step.getRequest());
                row.createCell(2).setCellValue(step.getResponses());
                row.createCell(3).setCellValue(step.getExpectedResponse());
                row.createCell(4).setCellValue(step.getSavingValues());
            }
        }

        response.addHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        book.write(response.getOutputStream());
    }

    @RequestMapping(value = "{projectId}/import-from-excel", method = RequestMethod.POST)
    public String importScenarioFromExcel(
            @PathVariable long projectId,
            @RequestParam Long scenarioGroupId,
            @RequestParam("excelFile") MultipartFile[] excelFile
    ) throws IOException {
        Project project = projectService.findOne(projectId);
        ScenarioGroup scenarioGroup = scenarioGroupId == null ? null : scenarioGroupService.findOne(scenarioGroupId);
        for (MultipartFile multipartFile: excelFile) {
            if (!multipartFile.isEmpty()) {
                ExcelTestScenarioParser excelTestScenarioParser = new ExcelTestScenarioParser(multipartFile.getInputStream());
                List<List<Step>> scenarioList = excelTestScenarioParser.parse();

                int scenarioIndex = 1;
                for (List<Step> scenario : scenarioList) {
                    Scenario scenarioModel = new Scenario();
                    project.getScenarios().add(scenarioModel);
                    scenarioModel.setScenarioGroup(scenarioGroup);
                    scenarioModel.setName(multipartFile.getOriginalFilename() + " " + scenarioIndex++);
                    scenarioModel.setProject(project);

                    Long i = 0L;
                    for (Step step : scenario) {
                        scenarioModel.getSteps().add(step);
                        step.setSort(50 * ++i);
                    }
                }
            }
        }
        project = projectService.save(project);
        return "redirect:/project/" + project.getId();
    }

    @RequestMapping(value = "{projectId}/add-scenario", method = RequestMethod.POST)
    public String importScenarioFromExcel(
            @RequestParam String name,
            @PathVariable long projectId,
            @RequestParam Long scenarioGroupId
    ) throws IOException {
        Project project = projectService.findOne(projectId);
        if (project != null) {
            Scenario scenario = new Scenario();
            project.getScenarios().add(scenario);
            scenario.setName(name);
            scenario.setProject(project);
            scenario = scenarioService.save(scenario);

            if (scenarioGroupId != null) {
                scenario.setScenarioGroup(scenarioGroupService.findOne(scenarioGroupId));
                scenario = scenarioService.save(scenario);
            }

            return "redirect:/scenario/" + scenario.getId();
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "{projectId}/add-group", method = RequestMethod.POST)
    public String addScenarioGroup(
            @RequestParam String name,
            @PathVariable long projectId
    ) throws IOException {
        Project project = projectService.findOne(projectId);

        ScenarioGroup scenarioGroup = new ScenarioGroup();
        scenarioGroup.setName(name);
        scenarioGroup.setProject(project);

        scenarioGroupService.save(scenarioGroup);
        return "redirect:/project/" + project.getId() + "/groups";
    }

    @RequestMapping(value = "{projectId}/add-stand", method = RequestMethod.POST)
    public String addStand(
            @RequestParam String serviceUrl,
            @PathVariable long projectId
    ) throws IOException {
        Project project = projectService.findOne(projectId);

        Stand stand = new Stand();
        stand.setServiceUrl(serviceUrl);
        stand.setProject(project);

        standService.save(stand);
        return "redirect:/project/" + project.getId() + "/stands";
    }

    @RequestMapping(value = "{projectId}/get-yaml", method = RequestMethod.GET, produces = "application/x-yaml; charset=utf-8")
    @ResponseBody
    public String getYaml(
            @PathVariable long projectId,
            HttpServletResponse response
    ) throws IOException {
        Project project = projectService.findOne(projectId);
        response.setHeader("Content-Disposition", "inline; filename=\"project-" + project.getProjectCode() +".yml\"");
        return projectService.findOneAsYaml(projectId);
    }
}
