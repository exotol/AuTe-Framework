package ru.bsc.test.autotester.configuration;

import ch.qos.logback.classic.Level;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import ru.bsc.test.at.executor.model.Project;
import ru.bsc.test.at.executor.model.Scenario;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.at.executor.service.AtExecutor;
import ru.bsc.test.autotester.component.ProjectsSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import ch.qos.logback.classic.Logger;
/**
 * Created by sdoroshin on 23.10.2017.
 *
 */

@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@SpringBootApplication
public class Application {

    private final static String PROPERTIES_FILE_NAME = "environment.properties";
    private final static String PROJECTS_PATH_PROPERTY = "projects.directory.path";

    public static void main(String args[]) {
        if (Arrays.asList(args).contains("execute")) {

            Set<String> loggers = new HashSet<>(Arrays.asList(
                    "org.apache.http",
                    "ru.bsc.test.autotester.component.ProjectsSource"
            ));

            for(String log: loggers) {
                Logger logger = (Logger)LoggerFactory.getLogger(log);
                logger.setLevel(Level.WARN);
                logger.setAdditive(false);
            }

            Properties prop = new Properties();
            try {
                prop.load(new FileInputStream(PROPERTIES_FILE_NAME));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }

            ProjectsSource projectsSource = new ProjectsSource();
            projectsSource.setDirectoryPath(prop.getProperty(PROJECTS_PATH_PROPERTY));
            List<Project> projectList = projectsSource.getProjectList();

            long failedSummary = projectList.stream().mapToLong(project -> {
                System.out.println("Project: [" + project.getCode() + "]" + project.getName());
                AtExecutor atExecutor = new AtExecutor();
                atExecutor.setProjectPath(prop.getProperty(PROJECTS_PATH_PROPERTY) + "/" + project.getCode() + "/");
                Map<Scenario, List<StepResult>> result = atExecutor.executeScenarioList(project, project.getScenarioList());

                return result.entrySet().stream().map(scenarioListEntry -> {
                    Scenario scenario = scenarioListEntry.getKey();
                    List<StepResult> stepResultList = scenarioListEntry.getValue();

                    long count = stepResultList.stream().filter(stepResult -> "Fail".equals(stepResult.getResult())).count();
                    if (count > 0) {
                        System.out.println("Failed: Scenario [" + scenario.getCode() + "]" + scenario.getName());
                        stepResultList.forEach(stepResult -> System.out.println("\t" + stepResult.getResult() + ": Step [" + stepResult.getStep().getCode() + "] " + stepResult.getRequestUrl()));
                        return 1;
                    } else {
                        System.out.println("Success: Scenario [" + scenario.getCode() + "]" + scenario.getName());
                        return 0;
                    }
                }).mapToInt(value -> value).sum();
            }).sum();

            System.exit(failedSummary > 0 ? 1 : 0);
            return;
        }
        SpringApplication.run(new Class<?>[] {Application.class, SpringRootConfig.class, SpringWebConfig.class}, args);
    }
}
