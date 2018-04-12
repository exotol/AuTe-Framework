package ru.bsc.test.at.mock.wiremock;

/**
 * Created by smakarov
 * 13.02.2018 11:15
 */
public enum Constants {
    VELOCITY_PROPERTIES("velocity.properties");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
