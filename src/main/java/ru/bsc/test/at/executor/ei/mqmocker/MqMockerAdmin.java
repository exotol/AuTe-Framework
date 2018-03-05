package ru.bsc.test.at.executor.ei.mqmocker;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import ru.bsc.test.at.executor.ei.mqmocker.model.MockMessage;
import ru.bsc.test.at.executor.ei.mqmocker.model.MockedRequest;

import java.io.IOException;
import java.util.List;

public class MqMockerAdmin {

    private final String mqMockerAdminUrl;

    public MqMockerAdmin(String mqMockerAdminUrl) {
        this.mqMockerAdminUrl = mqMockerAdminUrl;
    }

    public List<MockedRequest> getRequestList() {

    }

    public String addMock(MockMessage mockMessage) {

    }

    // TODO Такой же метод есть в WireMockAdmin. Нужно их вынести в отдельный класс.
    private String sendPost(String url, String jsonRequestBody) throws IOException {
        HttpPost httpPostAddMapping = new HttpPost(mqMockerAdminUrl + url);
        httpPostAddMapping.setEntity(new StringEntity(jsonRequestBody, ContentType.APPLICATION_JSON));

        try (CloseableHttpResponse response = HttpClientBuilder.create().build().execute(httpPostAddMapping)) {
            return response.getEntity() == null || response.getEntity().getContent() == null ? "" : IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        }
    }
}
