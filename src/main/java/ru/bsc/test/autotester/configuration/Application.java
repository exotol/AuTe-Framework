package ru.bsc.test.autotester.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by sdoroshin on 23.10.2017.
 *
 */

@SpringBootApplication
public class Application {
    public static void main(String args[]) {
        SpringApplication.run(new Class<?>[] {Application.class, SpringRootConfig.class, SpringWebConfig.class}, args);
    }
}
