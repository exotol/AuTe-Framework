package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by smakarov
 * 28.03.2018 13:33
 */
@Component
public class VariablesAttachExtractor extends AbstractAttachExtractor {

    private static final String FILE_NAME = "Variables";

    @Override
    public Attachment extract(File resultDirectory, StepResult result) {
        if (MapUtils.isEmpty(result.getVariables())) {
            return null;
        }

        String data = getVariablesData(result.getVariables());
        String relativePath = writeDataToFile(resultDirectory, data, FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType("text/plain");
        }
        return null;
    }

    private String getVariablesData(Map<String, Object> variables) {
        return variables.entrySet().stream()
                .map(variable -> variable.getKey() + " = " + variable.getValue().toString())
                .collect(Collectors.joining("\n"));
    }
}
