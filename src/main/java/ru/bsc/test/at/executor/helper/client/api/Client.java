package ru.bsc.test.at.executor.helper.client.api;

import java.io.Closeable;

/**
 * @author Pavel Golovkin
 */
public interface Client<REQ extends ClientRequest, RES extends ClientResponse> extends Closeable {

  RES request(REQ request) throws Exception;

}
