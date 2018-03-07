package ru.bsc.test.autotester.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import ru.bsc.test.autotester.properties.EnvironmentProperties;
import ru.bsc.test.autotester.yaml.YamlUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by sdoroshin on 21.03.2017.
 */
@Configuration
@ComponentScan("ru.bsc.test.autotester")
public class SpringRootConfig {

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(50 * 1024 * 1024);
        commonsMultipartResolver.setMaxInMemorySize(1024 * 1024);
        return commonsMultipartResolver;
    }

    @Bean
    public EnvironmentProperties environmentProperties() throws IOException {
        return YamlUtils.loadAs(new File("env.yml"), EnvironmentProperties.class);
    }
}
