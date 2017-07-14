package ru.bsc.test.at.executor.helper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 22/05/17.
 *
 */
public class HttpHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHelper.class);
    private final CloseableHttpClient httpClient;
    private HttpClientContext context;

    public HttpHelper() {
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).build();
        CookieStore cookieStore = new BasicCookieStore();
        context = HttpClientContext.create();
        context.setCookieStore(cookieStore);
        httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).setDefaultCookieStore(cookieStore).build();
    }

    public ResponseHelper request(String method, String url, String jsonRequestBody, Map<String, String> formDataPostParameters, String headers, String testIdHeaderName, String testId) throws IOException, URISyntaxException {
        URI uri = new URIBuilder(url).build();

        HttpRequestBase httpRequest;
        switch (method == null ? "POST" : method) {
            case "GET":
                httpRequest = new HttpGet(uri);
                break;
            case "DELETE":
                httpRequest = new HttpDelete(uri);
                break;
            case "PUT":
                httpRequest = new HttpPut(uri);
                break;
            case "PATCH":
                httpRequest = new HttpPatch(uri);
                break;
            case "POST":
            default:
                httpRequest = new HttpPost(uri);
                break;
        }
        if (httpRequest instanceof HttpEntityEnclosingRequestBase) {
            HttpEntity httpEntity = null;
            if (jsonRequestBody != null) {
                httpEntity = new StringEntity(jsonRequestBody, ContentType.APPLICATION_JSON);
            } else if (formDataPostParameters != null) {
                List<NameValuePair> pairList = formDataPostParameters.entrySet()
                        .stream()
                        .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList());
                httpEntity = new UrlEncodedFormEntity(pairList, Consts.UTF_8);
            }

            if (httpEntity != null) {
                ((HttpEntityEnclosingRequestBase) httpRequest).setEntity(httpEntity);
            }
        }

        if (StringUtils.isNotEmpty(testIdHeaderName)) {
            httpRequest.addHeader(testIdHeaderName, testId);
        }
        setHeaders(httpRequest, headers);
        CloseableHttpResponse response = httpClient.execute(httpRequest, context);
        String theString = response.getEntity() == null || response.getEntity().getContent() == null ? "" : IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        return new ResponseHelper(response.getStatusLine().getStatusCode(), theString);
    }

    private void setHeaders(HttpRequestBase request, String headers) {
        if (headers != null && !headers.isEmpty()) {
            Scanner scanner = new Scanner(headers);
            while (scanner.hasNextLine()) {
                String header = scanner.nextLine();
                String[] headerDetail = header.split(":");
                if (headerDetail.length >= 2) {
                    request.addHeader(headerDetail[0].trim(), headerDetail[1].trim());
                }
            }
        }
    }

    public void closeHttpConnection() {
        try {
            httpClient.close();
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
