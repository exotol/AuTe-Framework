/*
 * Copyright 2018 BSC Msc, LLC
 *
 * This file is part of the ATF project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl.stepresult;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.StepResult;
import ru.bsc.test.autotester.component.JsonDiffCalculator;
import ru.bsc.test.autotester.diff.Diff;
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
