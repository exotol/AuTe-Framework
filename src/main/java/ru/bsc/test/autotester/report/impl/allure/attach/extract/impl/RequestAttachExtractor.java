package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.FieldType;
import ru.bsc.test.at.executor.model.RequestBodyType;
import ru.bsc.test.at.executor.model.Step;
import ru.bsc.test.at.executor.model.StepResult;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.stream.Collectors;

/**
 * Created by smakarov
 * 23.03.2018 10:10
 */
@Component
public class RequestAttachExtractor extends AbstractAttachExtractor {

    private static final String FILE_NAME = "Request data";

    @Override
    public Attachment extract(File resultDirectory, StepResult result) {
        if (StringUtils.isEmpty(result.getStep().getRelativeUrl())) {
            return null;
        }
        String requestData = getRequestData(result);
        String relativePath = writeDataToFile(resultDirectory, requestData, FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType(TEXT_PLAIN);
        }
        return null;
    }

    private String getRequestData(StepResult result) {
        Step step = result.getStep();
        String data = "URL: " + step.getRelativeUrl();
        data += "\nMethod: " + step.getRequestMethod();
        data += "\nIgnore response: " + (step.getExpectedResponseIgnore() ? "yes" : "no");
        if (StringUtils.isNotEmpty(step.getTimeoutMs())) {
            data += "\nTimeout, ms: " + step.getTimeoutMs();
        }
        if (StringUtils.isNotEmpty(step.getNumberRepetitions())) {
            data += "\nNumber of repetitions: " + step.getNumberRepetitions();
        }
        if (step.getExpectedStatusCode() != null) {
            data += "\nExpected status: " + step.getExpectedStatusCode();
        }
        if (step.getRequestBodyType() == null || step.getRequestBodyType() == RequestBodyType.JSON) {
            data += "\nRequest body [json data]:\n";
            data += step.getRequest();
        } else {
            data += "\nRequest body " + (step.getMultipartFormData() ? "[multipart/form-data]" : "[form-data]") + "\n";
            data += step.getFormDataList().stream()
                    .map(formData ->
                            formData.getFieldName() +
                            (formData.getFieldType() == FieldType.TEXT ?
                             " = " + formData.getValue() :
                             "path: " + formData.getFilePath() + ", mime type: " + formData.getMimeType()))
                    .collect(Collectors.joining("\n"));
        }
        return data;
    }
}
