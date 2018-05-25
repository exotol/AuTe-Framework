package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.stepresult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.AbstractAttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by smakarov
 * 23.03.2018 14:11
 */
@Component
public class ParseMockRequestsAttachExtractor extends AbstractAttachExtractor<StepResult> {

    private static final String FILE_NAME = "Parse mock requests";

    @Override
    public List<Attachment> extract(File resultDirectory, StepResult result) {
        String url = result.getStep().getParseMockRequestUrl();
        String xPath = result.getStep().getParseMockRequestXPath();
        String variable = result.getStep().getParseMockRequestScenarioVariable();
        if (StringUtils.isEmpty(url) && StringUtils.isEmpty(xPath) && StringUtils.isEmpty(variable)) {
            return null;
        }
        String data = "";
        if (StringUtils.isEmpty(url)) {
            data += "URL: " + url + "\n";
        }
        if (StringUtils.isNotEmpty(xPath)) {
            data += "XPath: " + xPath + "\n";
        }
        if (StringUtils.isNotEmpty(variable)) {
            data += "Variable: " + variable + "\n";
        }
        String relativePath = writeDataToFile(resultDirectory, data, FILE_NAME);
        if (relativePath != null) {
            return Collections.singletonList(new Attachment()
                    .withTitle(FILE_NAME)
                    .withSource(relativePath)
                    .withType(TEXT_PLAIN));
        }
        return null;
    }
}