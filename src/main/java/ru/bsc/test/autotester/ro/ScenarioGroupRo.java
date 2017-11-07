package ru.bsc.test.autotester.ro;

import ru.bsc.test.autotester.dto.AbstractRo;

/**
 * Created by sdoroshin on 14.09.2017.
 *
 */
public class ScenarioGroupRo extends AbstractRo {

    private static final long serialVersionUID = 4101737950860012431L;

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
