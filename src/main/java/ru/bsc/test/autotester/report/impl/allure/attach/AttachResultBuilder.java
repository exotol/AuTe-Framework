package ru.bsc.test.autotester.report.impl.allure.attach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.StepResultAttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by smakarov
 * 23.03.2018 10:06
 */
@Component
public class AttachResultBuilder {

    private final List<StepResultAttachExtractor> extractors;

    @Autowired
    public AttachResultBuilder(List<StepResultAttachExtractor> extractors) {
        this.extractors = extractors;
    }

    public List<Attachment> build(File resultDirectory, StepResult stepResult) {
        return extractors.stream()
                .map(extractor -> extractor.extract(resultDirectory, stepResult))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
