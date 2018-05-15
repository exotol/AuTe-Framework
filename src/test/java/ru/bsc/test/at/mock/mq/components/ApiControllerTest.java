package ru.bsc.test.at.mock.mq.components;

import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.junit.Assert;
import org.junit.Test;
import ru.bsc.test.at.mock.mq.models.MockedRequest;

import java.lang.reflect.Field;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;

public class ApiControllerTest {

    public static final int BUFF_SIZE = 1000;
    private ApiController apiController;

    public ApiControllerTest() throws NoSuchFieldException, IllegalAccessException {

        MqRunnerComponent mqRunnerComponent = new MqRunnerComponent();
        Field fifo =  MqRunnerComponent.class.getDeclaredField("fifo");
        fifo.setAccessible(true);
        fifo.set(mqRunnerComponent, BufferUtils.synchronizedBuffer(new CircularFifoBuffer(BUFF_SIZE)));



        for(int i = 0; i < BUFF_SIZE; i++) {
            MockedRequest mockedRequest = new MockedRequest();
            mockedRequest.setDate(Date.from(LocalDateTime.of(2000+i,01,01,6,30).atZone(ZoneId.systemDefault()).toInstant()));
            CircularFifoBuffer b = (CircularFifoBuffer) mqRunnerComponent.getFifo();
            b.add(mockedRequest);
        }

        apiController = new ApiController(mqRunnerComponent);
    }


    @Test()
    public void getRequestListSizeTest(){
        Collection requestList = apiController.getRequestList(null);
        Assert.assertTrue(requestList.size() == BUFF_SIZE);
        requestList = apiController.getRequestList(BUFF_SIZE);
        Assert.assertTrue(requestList.size() == BUFF_SIZE);
        requestList = apiController.getRequestList(5);
        Assert.assertTrue(requestList.size() == 5);

    }
}
