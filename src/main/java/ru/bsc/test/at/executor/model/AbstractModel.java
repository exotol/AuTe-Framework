package ru.bsc.test.at.executor.model;

/**
 * Created by sdoroshin on 31.10.2017.
 *
 */

@SuppressWarnings("WeakerAccess")
public abstract class AbstractModel {

    private Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
