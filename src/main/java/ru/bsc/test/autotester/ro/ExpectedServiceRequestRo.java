package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sdoroshin on 28.09.2017.
 */
@Getter
@Setter
public class ExpectedServiceRequestRo implements AbstractRo {
    private static final long serialVersionUID = -5748544019274406969L;

    private String code;
    private String serviceName;
    private String expectedServiceRequest;
    private String expectedServiceRequestFile;
    private String ignoredTags;
}
