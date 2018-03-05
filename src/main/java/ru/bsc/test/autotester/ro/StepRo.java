package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Created by sdoroshin on 14.09.2017.
 */
@Getter
@Setter
public class StepRo implements AbstractRo {
    private static final long serialVersionUID = -4795596079038167133L;

    private String code;
    private String relativeUrl;
    private String requestMethod;
    private String request;
    private String requestFile;
    private String requestHeaders;
    private String expectedResponse;
    private String expectedResponseFile;
    private Boolean expectedResponseIgnore;
    private Integer expectedStatusCode;
    private String sql;
    private String sqlSavedParameter;
    private String jsonXPath;
    private String requestBodyType;
    private Boolean usePolling;
    private String pollingJsonXPath;
    private List<MockServiceResponseRo> mockServiceResponseList;
    private Boolean disabled;
    private String stepComment;
    private Map<String, String> savedValuesCheck;
    private List<StepParameterSetRo> stepParameterSetList;
    private List<ExpectedServiceRequestRo> expectedServiceRequestList;
    private String mqName;
    private String mqMessage;
    private String mqMessageFile;
    private String responseCompareMode;
    private List<FormDataRo> formDataList;
    private Boolean multipartFormData;
    private String jsonCompareMode;
    private String script;
    private String numberRepetitions;
    private String parseMockRequestUrl;
    private String parseMockRequestXPath;
    private String parseMockRequestScenarioVariable;
    private String timeoutMs;
}
