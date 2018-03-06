package ru.bsc.test.at.executor.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpectedMqRequest {
    private String sourceQueue;
    private String requestBody;
    private String ignoredTags;
}
