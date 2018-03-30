package ru.bsc.test.autotester.launcher;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by smakarov
 * 23.03.2018 10:24
 */
@Component
@Slf4j
public class TestRunner implements CommandLineRunner {

    private final TestLauncher launcher;

    public TestRunner(TestLauncher launcher) {
        this.launcher = launcher;
    }

    @Override
    public void run(String... args) {
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

            log.info("Running tests");
            try {
                launcher.launch();
            } catch (Exception e) {
                log.error("Error while running tests", e);
            }
        }
    }
}