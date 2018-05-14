package ru.bsc.test.at.mock.mq.components;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.collections.Buffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bsc.test.at.mock.mq.models.MockMessage;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mq-mock/__admin")
@Api(tags = {"MqMock"})
public class ApiController {

    private final MqRunnerComponent mqRunnerComponent;

    @Autowired
    public ApiController(MqRunnerComponent mqRunnerComponent) {
        this.mqRunnerComponent = mqRunnerComponent;
    }

    @ApiOperation(value = "MQ mapping creation", notes = "Creates new mapping for MQ mock", tags = "MqMock")
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Void.class)
        }
    )
    @PostMapping("add-mapping")
    @ResponseBody
    public String addMapping(@RequestBody MockMessage mockMessage) {
        return mqRunnerComponent.addMapping(mockMessage);
    }

    @DeleteMapping("mappings/{mappingGuid}")
    public void deleteMapping(@PathVariable String mappingGuid) throws IOException, TimeoutException {
        mqRunnerComponent.deleteMapping(mappingGuid);
    }

    @ApiOperation(value = "MQ mapping list getting", notes = "Gets list of all mocks", tags = "MqMock")
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Void.class)
        }
    )
    @GetMapping("mappings")
    public List<MockMessage> getMappingList() throws IOException, TimeoutException {
        return mqRunnerComponent.getMockMappingList();
    }

    @ApiOperation(value = "MQ request list getting", notes = "Get request history", tags = "MqMock")
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Void.class)
        }
    )
    @GetMapping("request-list")
    @ResponseBody
    public Collection getRequestList(@RequestParam(required = false, defaultValue = "${mq.requestBufferSize:1000}") Integer limit) {
        try {
            Buffer fifo = mqRunnerComponent.getFifo();
            List result = new LinkedList(fifo);
            if(limit != null && result.size() > limit){
                result = result.subList(result.size() - limit, result.size());
            }
            Collections.reverse(result);
            return result;
        } catch (NumberFormatException nfe) {
            return mqRunnerComponent.getFifo();
        }
    }

    @ApiOperation(value = "MQ mapping list clear", notes = "Clear request history", tags = "MqMock")
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Void.class)
        }
    )
    @PostMapping("request-list/clear")
    @ResponseBody
    public void clearRequestList() {
        mqRunnerComponent.getFifo().clear();
    }
}
