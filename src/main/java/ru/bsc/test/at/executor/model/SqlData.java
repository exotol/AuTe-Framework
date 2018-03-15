package ru.bsc.test.at.executor.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SqlData implements Serializable, AbstractModel {
    private static final long serialVersionUID = -5297373310164570345L;

    private String sql;
    private String sqlSavedParameter;

    public SqlData copy() {
        SqlData sqlData = new SqlData();
        sqlData.setSql(getSql());
        sqlData.setSqlSavedParameter(getSqlSavedParameter());
        return sqlData;
    }
}
