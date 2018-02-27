package ru.bsc.test.autotester.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("WeakerAccess")
public class DataBaseProperties {
    private String url;
    private String user;
    private String password;
}
