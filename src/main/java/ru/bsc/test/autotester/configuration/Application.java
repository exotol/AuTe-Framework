package ru.bsc.test.autotester.configuration;

import ch.qos.logback.classic.Level;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import ch.qos.logback.classic.Logger;
import ru.bsc.test.autotester.launcher.TestLauncher;

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

            TestLauncher testLauncher = new TestLauncher();
            testLauncher.launch();
            return;
        }
        SpringApplication.run(new Class<?>[] {Application.class, SpringRootConfig.class, SpringWebConfig.class}, args);
    }
}
