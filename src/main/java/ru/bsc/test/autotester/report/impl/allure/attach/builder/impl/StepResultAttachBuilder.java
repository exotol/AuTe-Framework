package ru.bsc.test.autotester.report.impl.allure.attach.builder.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.report.impl.allure.attach.builder.AttachBuilder;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.AttachExtractor;

import java.util.List;

/**
 * Created by smakarov
 * 23.03.2018 10:06
 */
@Component
public class StepResultAttachBuilder extends AttachBuilder<StepResult> {
    @Autowired
    public StepResultAttachBuilder(List<AttachExtractor<StepResult>> extractors) {
        super(extractors);
    }
}
