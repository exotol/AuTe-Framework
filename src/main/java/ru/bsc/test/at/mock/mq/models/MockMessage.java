package ru.bsc.test.at.mock.mq.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel(description = "Mock MQ message.")
public class MockMessage {
    @ApiModelProperty("GUID of mock")
    private String guid;
    @ApiModelProperty("Name of mocked queue")
    private String sourceQueueName;
    @ApiModelProperty("Test identifier where mock is used")
    private String testId;
    @ApiModelProperty("The mock response body")
    private String responseBody;
    @ApiModelProperty("HTTP url for request response-body, if responseBody is empty")
    private String httpUrl;
    @ApiModelProperty("Name of destination queue, where mock response will be sent")
    private String destinationQueueName;
    @ApiModelProperty("XPath expression to evaluate with message")
    private String xpath;
}
