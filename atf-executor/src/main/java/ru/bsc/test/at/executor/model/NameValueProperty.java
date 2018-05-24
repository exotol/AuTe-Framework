package ru.bsc.test.at.executor.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NameValueProperty {
    private String name;
    private String value;

    public NameValueProperty copy() {
        NameValueProperty property = new NameValueProperty();
        property.setName(getName());
        property.setValue(getValue());
        return property;
    }
}
