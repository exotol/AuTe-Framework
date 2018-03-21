package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.SqlReturnType;
import org.springframework.stereotype.Component;
import ru.bsc.test.at.executor.model.SqlData;
import ru.bsc.test.at.executor.model.SqlResultType;
import ru.bsc.test.at.executor.model.StepResult;
import ru.yandex.qatools.allure.model.Attachment;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by smakarov
 * 23.03.2018 10:33
 */
@Component
public class SqlAttachExtractor extends AbstractAttachExtractor {

    private static final String FILE_NAME = "SQL Data";

    @Override
    public Attachment extract(File resultDirectory, StepResult result) {
        List<SqlData> dataList = result.getStep().getSqlDataList();
        if (CollectionUtils.isEmpty(dataList)) {
            return null;
        }
        String sqlData = getSqlData(dataList);
        String relativePath = writeDataToFile(resultDirectory, sqlData, FILE_NAME);
        if (relativePath != null) {
            return new Attachment().withTitle(FILE_NAME).withSource(relativePath).withType("text/plain");
        }
        return null;
    }

    private String getSqlData(List<SqlData> sqlDataList) {
        return sqlDataList.stream()
                .map(data ->
                        "Query: " +
                        data.getSql() +
                        "\nParameters: " +
                        data.getSqlSavedParameter() +
                        "\nReturn type: " +
                        (data.getSqlReturnType() != null ? data.getSqlReturnType() : SqlResultType.MAP)
                ).collect(Collectors.joining("\n\n"));

    }
}
