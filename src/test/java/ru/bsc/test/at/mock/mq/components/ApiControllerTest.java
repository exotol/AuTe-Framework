package ru.bsc.test.at.mock.mq.components;

import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.junit.Assert;
import org.junit.Test;
import ru.bsc.test.at.mock.mq.models.MockedRequest;

import java.lang.reflect.Field;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Iterator;

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
            mqRunnerComponent.getFifo().add(mockedRequest);
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

    @Test()
    public void getRequestListOrderTest(){
        Collection requestList = apiController.getRequestList(10);
        Assert.assertTrue(requestList.size() == 10);
        Iterator it = requestList.iterator();
        LocalDate localDate = ((MockedRequest)it.next()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year  = localDate.getYear();
        Assert.assertTrue(year == 2999);

        localDate = ((MockedRequest)it.next()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        year  = localDate.getYear();
        Assert.assertTrue(year == 2998);

        localDate = ((MockedRequest)it.next()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        year  = localDate.getYear();
        Assert.assertTrue(year == 2997);
    }

    @Test()
    public void getRequestListOrderTest10000(){
        Collection requestList = apiController.getRequestList(10000);
        Assert.assertTrue(requestList.size() == BUFF_SIZE);
        Iterator it = requestList.iterator();
        LocalDate localDate = ((MockedRequest)it.next()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year  = localDate.getYear();
        Assert.assertTrue(year == 2999);

        localDate = ((MockedRequest)it.next()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        year  = localDate.getYear();
        Assert.assertTrue(year == 2998);

        localDate = ((MockedRequest)it.next()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        year  = localDate.getYear();
        Assert.assertTrue(year == 2997);
    }

}
