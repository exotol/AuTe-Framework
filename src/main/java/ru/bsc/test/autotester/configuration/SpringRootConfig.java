package ru.bsc.test.autotester.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import ru.bsc.test.autotester.component.ProjectsSource;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.repository.ProjectRepository;
import ru.bsc.test.autotester.repository.ScenarioRepository;
import ru.bsc.test.autotester.repository.yaml.YamlProjectRepositoryImpl;
import ru.bsc.test.autotester.repository.yaml.YamlScenarioRepositoryImpl;
import ru.bsc.test.autotester.yaml.YamlUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by sdoroshin on 21.03.2017.
 *
 */
@Configuration
@ComponentScan("ru.bsc.test.autotester")
public class SpringRootConfig {

    private final ApplicationContext applicationContext;

    @Autowired
    public SpringRootConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(50 * 1024 * 1024);
        commonsMultipartResolver.setMaxInMemorySize(1024 * 1024);
        return commonsMultipartResolver;
    }

    @Bean
    public ProjectsSource projectsSource() throws IOException {
        EnvironmentProperties environmentProperties = YamlUtils.loadAs(new File("env.yml"), EnvironmentProperties.class);

        ProjectsSource projectsSource = new ProjectsSource();
        projectsSource.setEnvironmentProperties(environmentProperties);
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
}
