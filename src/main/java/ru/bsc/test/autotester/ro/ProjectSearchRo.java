package ru.bsc.test.autotester.ro;

import ru.bsc.test.autotester.dto.AbstractRo;

public class ProjectSearchRo extends AbstractRo {

    private static final long serialVersionUID = 1322986388111392888L;
    
    private String relativeUrl;

    public String getRelativeUrl() {
        return relativeUrl;
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }
}
