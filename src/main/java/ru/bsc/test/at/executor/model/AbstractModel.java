package ru.bsc.test.at.executor.model;

/**
 * Created by sdoroshin on 31.10.2017.
 *
 */

@SuppressWarnings("WeakerAccess")
public abstract class AbstractModel {

    private String code;
    private Long sort;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getSort() {
        return sort;
    }
    public void setSort(Long sort) {
        this.sort = sort;
    }
}
