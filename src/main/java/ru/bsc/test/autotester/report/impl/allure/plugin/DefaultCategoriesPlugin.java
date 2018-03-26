package ru.bsc.test.autotester.report.impl.allure.plugin;

import io.qameta.allure.Aggregator;
import io.qameta.allure.category.Category;
import io.qameta.allure.core.Configuration;
import io.qameta.allure.core.LaunchResults;
import io.qameta.allure.entity.Status;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smakarov
 * 26.03.2018 17:02
 */
public class DefaultCategoriesPlugin implements Aggregator {

    private static final Category SUCCESSFUL_TESTS = new Category().setName("Successful tests");
    private static final Category FAILED_TESTS = new Category().setName("Failed tests");
    private static final String CATEGORIES = "categories";

    @Override
    public void aggregate(Configuration configuration, List<LaunchResults> launchesResults, Path outputDirectory) {
        launchesResults.forEach(launch -> launch.getResults().forEach(result -> {
            if (Status.PASSED.equals(result.getStatus())) {
                result.getExtraBlock(CATEGORIES, new ArrayList<Category>()).add(SUCCESSFUL_TESTS);
            }
            if (Status.FAILED.equals(result.getStatus())) {
                result.getExtraBlock(CATEGORIES, new ArrayList<Category>()).add(FAILED_TESTS);
            }
        }));
    }
}
