package ru.bsc.test.at.executor.helper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bsc.test.at.executor.model.FieldType;
import ru.bsc.test.at.executor.model.FormData;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by sdoroshin on 22/05/17.
 *
 */
public class HttpHelper {
    private final Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    private final CloseableHttpClient httpClient;
    private final HttpClientContext context;

    public HttpHelper() {
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.NETSCAPE).build();
        CookieStore cookieStore = new BasicCookieStore();
        context = HttpClientContext.create();

        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("SSL");

            // set up a TrustManager that trusts everything
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    System.out.println("checkClientTrusted =============");
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    System.out.println("checkServerTrusted =============");
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

            }}, new SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
            sslContext = null;
        }

        httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).setDefaultCookieStore(cookieStore).setSSLContext(sslContext).build();
    }

    public ResponseHelper request(String method, String url, String jsonRequestBody, String headers, String testIdHeaderName, String testId) throws URISyntaxException, IOException {
        URI uri = new URIBuilder(url).build();
        HttpRequestBase httpRequest = createRequest(method, uri, testIdHeaderName, testId);
        if (httpRequest instanceof HttpEntityEnclosingRequestBase && jsonRequestBody != null) {
            HttpEntity httpEntity = new StringEntity(jsonRequestBody, ContentType.APPLICATION_JSON);
            ((HttpEntityEnclosingRequestBase) httpRequest).setEntity(httpEntity);
        }
        return execute(httpRequest, headers);
    }

    public ResponseHelper request(String method, String projectPath, String url, Boolean multipartFormData, List<FormData> formData, String headers, String testIdHeaderName, String testId) throws URISyntaxException, IOException {
        URI uri = new URIBuilder(url).build();
        HttpRequestBase httpRequest = createRequest(method, uri, testIdHeaderName, testId);
        if (httpRequest instanceof HttpEntityEnclosingRequestBase) {
            boolean useMultipartFormData;
            if (multipartFormData == null) {
                long count = formData.stream().filter(formData1 -> FieldType.FILE.equals(formData1.getFieldType())).count();
                useMultipartFormData = count > 0;
            } else {
                useMultipartFormData = multipartFormData;
            }
            HttpEntity httpEntity;
            if (useMultipartFormData) {
                httpEntity = setEntity(formData, projectPath).build();
            } else {
                List<NameValuePair> params = formData
                        .stream()
                        .map(formData1 -> new BasicNameValuePair(formData1.getFieldName(), formData1.getValue()))
                        .collect(Collectors.toList());
                httpEntity = new UrlEncodedFormEntity(params);
            }
            ((HttpEntityEnclosingRequestBase) httpRequest).setEntity(httpEntity);
        }
        return execute(httpRequest, headers);
    }

    private HttpRequestBase createRequest(String method, URI uri, String testIdHeaderName, String testId) {
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
        if (StringUtils.isNotEmpty(testIdHeaderName)) {
            httpRequest.addHeader(testIdHeaderName, testId);
        }
        return httpRequest;
    }

    private MultipartEntityBuilder setEntity(List<FormData> formDataList, String projectPath) throws IOException {
        MultipartEntityBuilder entity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        for (FormData formData : formDataList) {
            if (formData.getFieldType() == null || FieldType.TEXT.equals(formData.getFieldType())) {
                entity.addTextBody(formData.getFieldName(), formData.getValue(), ContentType.TEXT_PLAIN);
            } else {
                File file = new File((projectPath == null ? "" : projectPath) + formData.getFilePath());
                entity.addBinaryBody(
                        formData.getFieldName(),
                        file,
                        ContentType.parse( StringUtils.isEmpty(formData.getMimeType()) ? new Tika().detect(file) : formData.getMimeType()),
                        file.getName()
                );
            }
        }
        return entity;
    }

    private ResponseHelper execute(HttpRequestBase httpRequest, String headers) throws IOException {
        setHeaders(httpRequest, headers);
        try (CloseableHttpResponse response = httpClient.execute(httpRequest, context)) {
            Map<String, List<String>> responseHeaders = new HashMap<>();
            Arrays.stream(response.getAllHeaders()).forEach(header -> {
                List<String> values = responseHeaders.get(header.getName());
                if (values != null) {
                    values.add(header.getValue());
                } else {
                    List<String> list = new LinkedList<>();
                    list.add(header.getValue());
                    responseHeaders.put(header.getName(), list);
                }
            });
            String theString = response.getEntity() == null || response.getEntity().getContent() == null ? "" : IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            return new ResponseHelper(response.getStatusLine().getStatusCode(), theString, responseHeaders);
        }
    }

    private void setHeaders(HttpRequestBase request, String headers) {
        if (headers != null && !headers.isEmpty()) {
            try (Scanner scanner = new Scanner(headers)) {
                while (scanner.hasNextLine()) {
                    String header = scanner.nextLine();
                    String[] headerDetail = header.split(":");
                    if (headerDetail.length >= 2) {
                        request.addHeader(headerDetail[0].trim(), headerDetail[1].trim());
                    }
                }
            }
        }
    }

    public void closeHttpConnection() {
        try {
            httpClient.close();
        } catch (IOException e) {
            logger.error("Error while closing http connection", e);
        }
    }
}
