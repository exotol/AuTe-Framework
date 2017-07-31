package ru.bsc.test.at.executor.wiremock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import ru.bsc.test.at.executor.wiremock.mockdefinition.MockDefinition;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 27.07.2017.
 *
 */
@SuppressWarnings("unused")
public class WireMockAdmin implements Closeable {

    private String wireMockAdminUrl;
    private List<String> mockIdList = new LinkedList<>();
    public WireMockAdmin(String wireMockAdminUrl) {
        this.wireMockAdminUrl = wireMockAdminUrl;
    }

    @SuppressWarnings("WeakerAccess")
    public void clearSavedMappings() throws IOException {
        mockIdList.forEach(s -> sendDelete("/mappings/" + s));
    }

    public void addMapping(MockDefinition mockDefinition) throws IOException {
        String result = sendPost("/mappings", new ObjectMapper().writeValueAsString(mockDefinition));
        MockDefinition mockDefinitionResponse = new ObjectMapper().readValue(result, MockDefinition.class);
        mockIdList.add(mockDefinitionResponse.getUuid());
    }

    private String sendPost(String url, String jsonRequestBody) throws IOException {
        HttpPost httpPostAddMapping = new HttpPost(wireMockAdminUrl + url);
        httpPostAddMapping.setEntity(new StringEntity(jsonRequestBody, ContentType.APPLICATION_JSON));

        try (CloseableHttpResponse response = HttpClientBuilder.create().build().execute(httpPostAddMapping)) {
            return response.getEntity() == null || response.getEntity().getContent() == null ? "" : IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        }
    }

    private String sendDelete(String url) {
        try (CloseableHttpResponse response = HttpClientBuilder.create().build().execute(new HttpDelete(wireMockAdminUrl + url))) {
            return response.getEntity() == null || response.getEntity().getContent() == null ? "" : IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() throws IOException {
        clearSavedMappings();
    }
}
