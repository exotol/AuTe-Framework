package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;
import ru.bsc.test.autotester.diff.Diff;

import java.util.List;

/**
 * Created by sdoroshin on 14.09.2017.
 */
@Getter
@Setter
public class StepResultRo {
    private String testId;
    private StepRo step;
    private String result;
    private String details;
    private String expected;
    private List<Diff> diff;
    private String actual;
    private String requestUrl;
    private String requestBody;
    private Integer pollingRetryCount;
    private String savedParameters;
    private String description;
    private boolean editable;
    private String cookies;
    private List<String> sqlQueryList;

    private List<RequestDataRo> requestDataList;
}
