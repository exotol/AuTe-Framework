package ru.bsc.test.autotester.report.impl.allure.attach.builder.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.RequestData;
import ru.bsc.test.autotester.report.impl.allure.attach.builder.AttachBuilder;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.AttachExtractor;

import java.util.List;

/**
 * Created by smakarov
 * 30.03.2018 12:28
 */
@Component
public class RequestDataAttachBuilder extends AttachBuilder<RequestData> {

    @Autowired
    public RequestDataAttachBuilder(List<AttachExtractor<RequestData>> extractors) {
        super(extractors);
    }
}
