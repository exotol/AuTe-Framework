package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.stepresult;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.AbstractAttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.stream.Collectors;

/**
 * Created by smakarov
 * 23.03.2018 11:58
 */
@Component
public class ScriptAttachExtractor extends AbstractAttachExtractor<StepResult> {

    private static final String FILE_NAME = "Script";

    @Override
    public Attachment extract(File resultDirectory, StepResult result) {
        if (StringUtils.isEmpty(result.getStep().getScript())) {
            return null;
        }

        String data = result.getStep().getScript();

        if (StringUtils.isNotEmpty(result.getStep().getJsonXPath())) {
            data += "\nJSON XPath: " + result.getStep().getJsonXPath();
        }
        if (MapUtils.isNotEmpty(result.getStep().getSavedValuesCheck())) {
            data += "\n" + result.getStep().getSavedValuesCheck().entrySet().stream()
                    .map(entry -> entry.getKey() + " = " + entry.getValue())
                    .collect(Collectors.joining("\n"));
        }
        String relativePath = writeDataToFile(resultDirectory, data, FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType(TEXT_PLAIN);
        }
        return null;
    }
}
