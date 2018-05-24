package ru.bsc.test.at.mock.mq.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel(description = "Mock MQ message.")
public class MockMessage {
    @ApiModelProperty("GUID of mock")
    private String guid;
    @ApiModelProperty("Name of mocked queue")
    private String sourceQueueName;
    @ApiModelProperty("Test identifier where mock is used")
    private String testId;
    @ApiModelProperty("MQ responses")
    private List<MockMessageResponse> responses;
    @ApiModelProperty("HTTP url for request response-body, if responseBody is empty")
    private String httpUrl;
    @ApiModelProperty("XPath expression to evaluate with message")
    private String xpath;
}
