package ru.bsc.test.autotester.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.yaml.snakeyaml.Yaml;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.autotester.service.ProjectService;

import java.io.IOException;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Controller
@RequestMapping("/")
public class MainController {

    private ProjectService projectService;

    @Autowired
    public MainController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping("/")
    public ModelAndView main() {
        ModelAndView model = new ModelAndView("main");
        model.addObject("projects", projectService.findAll());
        return model;
    }

    @RequestMapping(value = "import-project-from-yaml", method = RequestMethod.GET)
    public ModelAndView projectFromYaml() {
        return new ModelAndView("projectFromYaml");
    }

    @RequestMapping(value = "import-project-from-yaml", method = RequestMethod.POST)
    public String projectFromYamlPost(
            @RequestParam MultipartFile yamlFile
    ) throws IOException {
        Project object = (Project)new Yaml().load(yamlFile.getInputStream());
        object.getScenarios();

        // TODO сохранить проект в БД (in memory)
        projectService.save(object);

        return "redirect:/";
    }
}
