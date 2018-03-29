package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.apache.commons.io.FileUtils;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.StepResultAttachExtractor;
import ru.bsc.test.autotester.utils.FileExtensionsUtils;

import java.io.File;
import java.io.IOException;

import static java.io.File.separator;
import static java.util.UUID.randomUUID;

/**
 * Created by smakarov
 * 23.03.2018 10:40
 */
public abstract class AbstractAttachExtractor implements StepResultAttachExtractor {

    static final String TEXT_PLAIN = "text/plain";

    protected String writeDataToFile(File resultDirectory, String data, String name) {
        String extension = FileExtensionsUtils.extensionByContent(data);
        String relativePath = getAttachRelativePath(name, extension);
        File dataFile = new File(resultDirectory, relativePath);
        try {
            FileUtils.writeStringToFile(dataFile, data, "UTF-8");
            return relativePath;
        } catch (IOException e) {
            return null;
        }
    }

    private String getAttachRelativePath(String name, String extension) {
        return "attachments" + separator + randomUUID() + "-" + name.toLowerCase() + "." + extension;
    }
}
