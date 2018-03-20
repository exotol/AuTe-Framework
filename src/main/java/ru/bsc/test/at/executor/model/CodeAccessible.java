package ru.bsc.test.at.executor.model;

import java.util.UUID;

/**
 * Created by smakarov
 * 20.03.2018 10:38
 */
public interface CodeAccessible {
    String getCode();

    void setCode(String code);

    default void generateCode() {
        setCode(UUID.randomUUID().toString());
    }
}
