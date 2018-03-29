package ru.bsc.test.autotester.report.impl.allure.attach.extract;

import ru.bsc.test.at.executor.model.StepResult;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;

/**
 * Created by smakarov
 * 23.03.2018 10:08
 */
public interface StepResultAttachExtractor {
    Attachment extract(File resultDirectory, StepResult result);
}
