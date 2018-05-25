package ru.bsc.test.at.mock.mq.http;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.Closeable;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//TODO использовать HTTP Client из at-executor, предварительно создав общий пакет
public class HttpClient implements Closeable {

    private CloseableHttpClient closeableHttpClient;

    public HttpClient() {
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.NETSCAPE).build();
        CookieStore cookieStore = new BasicCookieStore();
        closeableHttpClient = HttpClients.custom().setDefaultRequestConfig(config).setDefaultCookieStore(cookieStore).build();
    }

    public String sendPost(String url, String body, String headerName, String headerValue) throws IOException {
        HttpPost post = new HttpPost(url);
        post.addHeader(headerName, headerValue);
        post.setEntity(new StringEntity(body));
        try (CloseableHttpResponse response = closeableHttpClient.execute(post)) {
            return response.getEntity() == null || response.getEntity().getContent() == null ? "" : IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        }
    }

    @Override
    public void close() throws IOException {
        try {
            closeableHttpClient.close();
        } catch (Exception e) {
            log.error("Error closing http connection", e);
            throw new IOException(e);
        }
    }
}