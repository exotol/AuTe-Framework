package ru.bsc.test.at.executor.helper.client.impl.http;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Pavel Golovkin
 */
@Slf4j
class HTTPClientBuilder {

  private SSLContext sslContext;
  private CookieStore cookieStore;
  RequestConfig globalConfig;

  HTTPClientBuilder withSllContext() {
    try {
      sslContext = SSLContext.getInstance("SSL");

      // set up a TrustManager that trusts everything
      sslContext.init(null, new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
          log.info("checkClientTrusted =============");
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
          log.info("checkServerTrusted =============");
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
          return null;
        }

      }}, new SecureRandom());
    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      log.error("Error while init SSL context", e);
      sslContext = null;
    }

    return this;
  }

  HTTPClientBuilder withCookiesStore() {
    cookieStore = new BasicCookieStore();
    return this;
  }

  HTTPClientBuilder withGlobalConfig() {
    globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.NETSCAPE).build();
    return this;
  }

  CloseableHttpClient build() {
    org.apache.http.impl.client.HttpClientBuilder clientBuilder = HttpClients.custom();
    if (cookieStore != null) {
      clientBuilder.setDefaultCookieStore(cookieStore);
    }
    if (sslContext != null) {
      clientBuilder.setSSLContext(sslContext);
    }
    if (globalConfig != null) {
      clientBuilder.setDefaultRequestConfig(globalConfig);
    }

    return clientBuilder.build();
  }
}
