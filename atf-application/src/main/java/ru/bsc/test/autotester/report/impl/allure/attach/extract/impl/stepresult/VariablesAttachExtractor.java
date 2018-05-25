package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.stepresult;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.AbstractAttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by smakarov
 * 28.03.2018 13:33
 */
@Component
public class VariablesAttachExtractor extends AbstractAttachExtractor<StepResult> {

    private static final String FILE_NAME = "Variables";

    @Override
    public List<Attachment> extract(File resultDirectory, StepResult result) {
        if (MapUtils.isEmpty(result.getScenarioVariables())) {
            return null;
        }

        String data = getVariablesData(result.getScenarioVariables());
        String relativePath = writeDataToFile(resultDirectory, data, FILE_NAME);
        if (relativePath != null) {
            return Collections.singletonList(new Attachment()
                    .withTitle(FILE_NAME)
                    .withSource(relativePath)
                    .withType("text/plain"));
        }
        return null;
    }

    private String getVariablesData(Map<String, Object> variables) {
        return variables.entrySet().stream()
                .filter(stringObjectEntry -> stringObjectEntry.getValue() != null)
                .map(variable -> variable.getKey() + " = " + variable.getValue())
                .collect(Collectors.joining("\n"));
    }
}