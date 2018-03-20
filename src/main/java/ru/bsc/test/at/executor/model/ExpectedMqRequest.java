package ru.bsc.test.at.executor.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ExpectedMqRequest implements CodeAccessible{
    private String code;
    private String sourceQueue;
    private String requestBody;
    private String ignoredTags;
}
