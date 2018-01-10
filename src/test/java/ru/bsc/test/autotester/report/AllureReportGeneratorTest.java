package ru.bsc.test.autotester.report;

import org.testng.annotations.Test;

import java.io.File;

public class AllureReportGeneratorTest {

    @Test
    public void generatorTest() throws Exception {
        AllureReportGenerator allureReportGenerator = new AllureReportGenerator();

        allureReportGenerator.generate(new File("c:/tmp/allure/"));

    }
}
