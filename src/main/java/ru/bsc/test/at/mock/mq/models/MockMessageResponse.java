package ru.bsc.test.at.mock.mq.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by smakarov
 * 22.05.2018 11:24
 */
@Getter
@Setter
@ApiModel(description = "Mock MQ response")
public class MockMessageResponse {
    @ApiModelProperty("The mock response body")
    private String responseBody;
    @ApiModelProperty("Name of destination queue, where mock response will be sent")
    private String destinationQueueName;
}
