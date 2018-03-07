package ru.bsc.test.autotester.configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import ru.bsc.test.autotester.launcher.TestLauncher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    public static void main(String args[]) throws Exception {
        if (Arrays.asList(args).contains("execute")) {

            Set<String> loggers = new HashSet<>(Arrays.asList(
                    "org.apache.http",
                    "org.apache.commons.beanutils.converters"
            ));

            for (String log : loggers) {
                Logger logger = (Logger) LoggerFactory.getLogger(log);
                logger.setLevel(Level.WARN);
                logger.setAdditive(false);
            }

            TestLauncher testLauncher = new TestLauncher();
            testLauncher.launch();
            return;
        }
        SpringApplication.run(Application.class, args);
    }
}
