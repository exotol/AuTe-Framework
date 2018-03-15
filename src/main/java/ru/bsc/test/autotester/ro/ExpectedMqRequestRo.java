package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpectedMqRequestRo {
    private String sourceQueue;
    private String requestBody;
    private String ignoredTags;
}
