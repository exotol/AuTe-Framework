package ru.bsc.test.autotester.report.impl.allure.plugin;

import io.qameta.allure.Reader;
import io.qameta.allure.core.Configuration;
import io.qameta.allure.core.ResultsVisitor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

/**
 * Created by smakarov
 * 10.04.2018 11:53
 */
@Slf4j
public class HistoryRestorePlugin implements Reader {

    public static final String HISTORY_DIRECTORY = "history";
    public static final String HISTORY_JSON = "history.json";
    public static final String HISTORY_TREND_JSON = "history-trend.json";
    public static final String DURATION_TREND_JSON = "duration-trend.json";
    private static final List<String> SAVING_FILES = Arrays.asList(
            HISTORY_JSON,
            HISTORY_TREND_JSON,
            DURATION_TREND_JSON
    );

    @Override
    public void readResults(Configuration configuration, ResultsVisitor visitor, Path directory) {
        Path targetDirectory = directory.resolve(HISTORY_DIRECTORY);
        if (!targetDirectory.toFile().mkdir()) {
            return;
        }
        SAVING_FILES.forEach(name -> {
            Path sourceFile = Paths.get(HISTORY_DIRECTORY, name);
            Path targetFile = targetDirectory.resolve(name);
            if (Files.exists(sourceFile)) {
                try {
                    Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    log.error("Error while copy file {}", name, e);
                }
            }
        });
    }
}
