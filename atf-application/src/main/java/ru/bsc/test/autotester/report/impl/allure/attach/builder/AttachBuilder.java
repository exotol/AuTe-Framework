package ru.bsc.test.autotester.report.impl.allure.attach.builder;

import ru.bsc.test.autotester.report.impl.allure.attach.extract.AttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by smakarov
 * 30.03.2018 12:17
 */
public abstract class AttachBuilder<T> {
    private final List<AttachExtractor<T>> extractors;

    public AttachBuilder(List<AttachExtractor<T>> extractors) {
        this.extractors = extractors;
    }

    public List<Attachment> build(File resultDirectory, T result) {
        return extractors.stream()
                .map(extractor -> extractor.extract(resultDirectory, result))
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
