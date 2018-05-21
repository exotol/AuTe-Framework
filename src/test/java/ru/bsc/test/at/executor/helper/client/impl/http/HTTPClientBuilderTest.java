package ru.bsc.test.at.executor.helper.client.impl.http;

import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Pavel Golovkin
 */

public class HTTPClientBuilderTest {

  @Test
  public void testEmptyBuild() {
    CloseableHttpClient closeableHttpClient = new HTTPClientBuilder().build();
    assertNotNull(closeableHttpClient);
  }

  @Test
  public void testWithCookiesStoreBuild() {
    CloseableHttpClient closeableHttpClient = new HTTPClientBuilder().withCookiesStore().build();
    assertNotNull(closeableHttpClient);
  }

  @Test
  public void testWithGlobalConfigBuild() {
    CloseableHttpClient closeableHttpClient = new HTTPClientBuilder().withGlobalConfig().build();
    assertNotNull(closeableHttpClient);
  }

  @Test
  public void testWithSllContextBuild() {
    CloseableHttpClient closeableHttpClient = new HTTPClientBuilder().withSllContext().build();
    assertNotNull(closeableHttpClient);
  }

  @Test
  public void testWithAllBuild() {
    CloseableHttpClient closeableHttpClient = new HTTPClientBuilder().withCookiesStore().withGlobalConfig().withSllContext().build();
    assertNotNull(closeableHttpClient);
  }
}