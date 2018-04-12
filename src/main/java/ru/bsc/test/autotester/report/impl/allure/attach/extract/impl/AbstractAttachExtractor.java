package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.apache.commons.io.FileUtils;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.AttachExtractor;
import ru.bsc.test.autotester.utils.FileExtensionsUtils;

import java.io.File;
import java.io.IOException;

import static java.io.File.separator;
import static java.util.UUID.randomUUID;

/**
 * Created by smakarov
 * 23.03.2018 10:40
 */
public abstract class AbstractAttachExtractor<T> implements AttachExtractor<T> {

    protected static final String TEXT_PLAIN = "text/plain";

    protected String writeDataToFile(File resultDirectory, String data, String name) {
        return writeDataToFile(resultDirectory, data, name, FileExtensionsUtils.extensionByContent(data));
    }

    protected String writeDataToFile(File resultDirectory, String data, String name, String extension) {
        String relativePath = getAttachRelativePath(name, extension);
        File dataFile = new File(resultDirectory, relativePath);
        try {
            FileUtils.writeStringToFile(dataFile, data, "UTF-8");
            return relativePath;
        } catch (IOException e) {
            return null;
        }
    }

    protected String getAttachRelativePath(String name, String extension) {
        return "attachments" + separator + randomUUID() + "-" + name + "." + extension;
    }
}
