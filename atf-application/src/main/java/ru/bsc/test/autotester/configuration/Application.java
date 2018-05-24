package ru.bsc.test.autotester.configuration;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import ru.bsc.test.autotester.launcher.impl.TestRunnerExitCodeContext;

import java.util.Arrays;

/**
 * Created by sdoroshin on 23.10.2017.
 */

@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@SpringBootApplication
public class Application {

    public static void main(String args[]) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        if (Arrays.asList(args).contains("execute")) {
            int exitCode = SpringApplication.exit(
                    context,
                    (ExitCodeGenerator) () -> TestRunnerExitCodeContext.getInstance().getExitCode()
            );
            System.exit(exitCode);
        }
    }
}
