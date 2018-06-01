package ru.bsc.test.at.executor.helper.client.impl.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.*;

import java.net.URI;

/**
 * @author Pavel Golovkin
 */
class HttpRequestCreator {

  static HttpRequestBase createRequest(HTTPMethod method, URI uri, String testIdHeaderName, String testId) {
    HttpRequestBase httpRequest;
    switch (method == null ? HTTPMethod.POST : method) {
      case GET:
        httpRequest = new HttpGet(uri);
        break;
      case DELETE:
        httpRequest = new HttpDelete(uri);
        break;
      case PUT:
        httpRequest = new HttpPut(uri);
        break;
      case PATCH:
        httpRequest = new HttpPatch(uri);
        break;
      case POST:
      default:
        httpRequest = new HttpPost(uri);
        break;
    }
    if (StringUtils.isNotEmpty(testIdHeaderName)) {
      httpRequest.addHeader(testIdHeaderName, testId);
    }
    return httpRequest;
  }
}
