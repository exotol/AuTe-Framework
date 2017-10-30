package ru.bsc.test.autotester.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import ru.bsc.test.autotester.component.ProjectsSource;
import ru.bsc.test.autotester.repository.ProjectRepository;
import ru.bsc.test.autotester.repository.ScenarioRepository;
import ru.bsc.test.autotester.repository.StepRepository;
import ru.bsc.test.autotester.repository.yaml.YamlProjectRepositoryImpl;
import ru.bsc.test.autotester.repository.yaml.YamlScenarioRepositoryImpl;
import ru.bsc.test.autotester.repository.yaml.YamlStepRepositoryImpl;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Configuration
@ComponentScan("ru.bsc.test.autotester")
@PropertySource("file:environment.properties")
public class SpringRootConfig {

    private final ApplicationContext applicationContext;

    @Autowired
    public SpringRootConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    //To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(50 * 1024 * 1024);
        commonsMultipartResolver.setMaxInMemorySize(1024 * 1024);
        return commonsMultipartResolver;
    }

    @Bean
    public ProjectsSource projectsSource(@Value("${projects.directory.path:.}") String projectsDirectoryPath) {
        ProjectsSource projectsSource = new ProjectsSource();
        projectsSource.setDirectoryPath(projectsDirectoryPath);
        return projectsSource;
    }

    @Bean
    public ProjectRepository projectRepository() {
        return new YamlProjectRepositoryImpl(applicationContext.getBean(ProjectsSource.class));
    }

    @Bean
    public ScenarioRepository scenarioRepository() {
        return new YamlScenarioRepositoryImpl(applicationContext.getBean(ProjectsSource.class));
    }

    @Bean
    public StepRepository stepRepository() {
        return new YamlStepRepositoryImpl(applicationContext.getBean(ProjectsSource.class));
    }
}
