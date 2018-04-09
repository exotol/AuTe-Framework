package ru.bsc.test.autotester.ro;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
    private String jsonXPath;
    private String requestBodyType;
    private Boolean usePolling;
    private String pollingJsonXPath;
    private List<MockServiceResponseRo> mockServiceResponseList = new ArrayList<>();
    private Boolean disabled;
    private String stepComment;
    private Map<String, String> savedValuesCheck;
    private List<StepParameterSetRo> stepParameterSetList = new ArrayList<>();
    private List<ExpectedServiceRequestRo> expectedServiceRequestList = new ArrayList<>();
    private String mqName;
    private String mqMessage;
    private String mqMessageFile;
    private String responseCompareMode;
    private List<FormDataRo> formDataList;
    private List<SQLDataRo> sqlDataList;
    private Boolean multipartFormData;
    private String jsonCompareMode;
    private String script;
    private String numberRepetitions;
    private String parseMockRequestUrl;
    private String parseMockRequestXPath;
    private String parseMockRequestScenarioVariable;
    private String timeoutMs;
    private List<MqMockResponseRo> mqMockResponseList;
    private List<ExpectedMqRequestRo> expectedMqRequestList;
    @Deprecated
    private String sql;
    @Deprecated
    private String sqlSavedParameter;
    private List<ScenarioVariableFromMqRequestRo> scenarioVariableFromMqRequestList;
    private String stepMode;

    private String mqOutputQueueName;
    private String mqOutputQueueBody;
    private String mqInputQueueName;
    private String mqInputQueueExpectedBody;
    private String mqTimeoutMs;
}
