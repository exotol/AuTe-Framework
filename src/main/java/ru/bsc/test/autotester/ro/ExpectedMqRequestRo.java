package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpectedMqRequestRo {
    private String code;
    private String sourceQueue;
    private String requestBody;
    private String ignoredTags;
    private String count;
}
