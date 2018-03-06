package ru.bsc.test.at.executor.ei.mqmocker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import ru.bsc.test.at.executor.ei.mqmocker.model.MqMockDefinition;
import ru.bsc.test.at.executor.ei.mqmocker.model.MockedRequest;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MqMockerAdmin implements Closeable {

    private final String mqMockerAdminUrl;
    private final List<String> mockGuidList = new LinkedList<>();

    public MqMockerAdmin(String mqMockerAdminUrl) {
        this.mqMockerAdminUrl = mqMockerAdminUrl;
    }

    private List<MockedRequest> getRequestList() throws IOException {
        try (CloseableHttpResponse response = HttpClientBuilder.create().build().execute(new HttpGet(mqMockerAdminUrl + "/request-list"))) {
            String responseBody = response.getEntity() == null || response.getEntity().getContent() == null ? "" : IOUtils.toString(response.getEntity().getContent(), "UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(responseBody, new TypeReference<List<MockedRequest>>(){});
        }
    }

    public List<MockedRequest> getRequestListByTestId(String testId) throws IOException {
        return getRequestList()
                .stream()
                .filter(mockedRequest -> Objects.equals(mockedRequest.getTestId(), testId))
                .collect(Collectors.toList());
    }

    public void addMock(MqMockDefinition mockMessageDefinition) throws IOException {
        HttpPost httpPostAddMapping = new HttpPost(mqMockerAdminUrl + "/add-mapping");
        httpPostAddMapping.setEntity(new StringEntity(new ObjectMapper().writeValueAsString(mockMessageDefinition), ContentType.APPLICATION_JSON));
        try (CloseableHttpResponse response = HttpClientBuilder.create().build().execute(httpPostAddMapping)) {
            String mockGuid = response.getEntity() == null || response.getEntity().getContent() == null ? "" : IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            mockGuidList.add(mockGuid);
        }
    }

    private void clearMockList() {
        mockGuidList.forEach(guid -> sendDelete("/mappings/" + guid));
    }

    private String sendDelete(String url) {
        try (CloseableHttpResponse response = HttpClientBuilder.create().build().execute(new HttpDelete(mqMockerAdminUrl + url))) {
            return response.getEntity() == null || response.getEntity().getContent() == null ? "" : IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() throws IOException {
        clearMockList();
    }
}
