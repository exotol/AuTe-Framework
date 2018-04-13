package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.stepresult;

import org.apache.commons.lang3.StringUtils;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.component.JsonDiffCalculator;
import ru.bsc.test.autotester.diff.Diff;
import ru.bsc.test.autotester.diff.Operation;
import ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.AbstractAttachExtractor;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by smakarov
 * 05.04.2018 13:10
 */
@Component
public class DiffAttachExtractor extends AbstractAttachExtractor<StepResult> {

    private static final String TEMPLATE_PATH = "template/diff-template.twig";
    private static final String FILE_NAME = "Diff results";
    private static final String EXTENSION = "html";
    private static final String TYPE = "text/html";

    private final JtwigTemplate template = JtwigTemplate.classpathTemplate(TEMPLATE_PATH);
    private final JsonDiffCalculator diffCalculator;

    @Autowired
    public DiffAttachExtractor(JsonDiffCalculator diffCalculator) {
        this.diffCalculator = diffCalculator;
    }

    @Override
    public List<Attachment> extract(File resultDirectory, StepResult result) {
        if (StringUtils.isEmpty(result.getActual()) || StringUtils.isEmpty(result.getExpected())) {
            return null;
        }
        List<Diff> diffs = diffCalculator.calculate(result.getActual(), result.getExpected());
        JtwigModel model = JtwigModel.newModel().with("diffs", diffs);
        String data = template.render(model);
        String relativePath = writeDataToFile(resultDirectory, data, FILE_NAME, EXTENSION);
        if (relativePath != null) {
            return Collections.singletonList(new Attachment().withTitle(FILE_NAME)
                    .withSource(relativePath)
                    .withType(TYPE));
        }
        return null;
    }
}
