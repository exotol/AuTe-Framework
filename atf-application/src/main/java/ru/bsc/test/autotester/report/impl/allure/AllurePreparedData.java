package ru.bsc.test.autotester.report.impl.allure;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.qatools.allure.model.TestSuiteResult;

import java.io.File;

/**
 * Created by smakarov
 * 21.03.2018 12:04
 */
@Getter
@Setter
class AllurePreparedData {
    private File dataFile;
    private TestSuiteResult suiteResult;

    public static AllurePreparedData of(File dataFile, TestSuiteResult testSuiteResult) {
        return new AllurePreparedData(dataFile, testSuiteResult);
    }

    private AllurePreparedData(File dataFile, TestSuiteResult suiteResult) {
        this.dataFile = dataFile;
        this.suiteResult = suiteResult;
    }
}
