package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;
import ru.bsc.test.at.executor.model.SqlResultType;

@Getter
@Setter
public class SQLDataRo implements AbstractRo {
    private static final long serialVersionUID = -4110884518055434593L;

    private String sql;
    private String sqlSavedParameter;
    private SqlResultType sqlReturnType;

}
