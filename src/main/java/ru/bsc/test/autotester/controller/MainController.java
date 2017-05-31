package ru.bsc.test.autotester.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.bsc.test.autotester.service.ProjectService;

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
}
