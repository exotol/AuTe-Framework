package ru.bsc.test.autotester.report.impl.allure.attach.extract;

import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;

/**
 * Created by smakarov
 * 23.03.2018 10:08
 */
public interface AttachExtractor<T> {
    Attachment extract(File resultDirectory, T result);
}
