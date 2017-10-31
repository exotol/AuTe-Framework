package ru.bsc.test.at.executor.model;

/**
 * Created by sdoroshin on 31.10.2017.
 *
 */

@SuppressWarnings("WeakerAccess")
public abstract class AbstractModel {

    private Long id;
    private Long sort;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSort() {
        return sort;
    }
    public void setSort(Long sort) {
        this.sort = sort;
    }
}
