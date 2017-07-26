package ru.bsc.wiremock;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by sdoroshin on 24.07.2017.
 *
 */
public class WireMockTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(7089); // No-args constructor defaults to port 8080


    @Test
    public void exampleTest() {
/*
        stubFor(get(urlEqualTo("/my/resource"))
                .withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody("<response>Some content</response>")));

        // Result result = myHttpServiceCallingObject.doSomething();
        // assertTrue(result.wasSuccessful());

        verify(postRequestedFor(urlMatching("/my/resource"))
                //.withRequestBody(matching(".*<message>1234</message>.*"))
                //.withHeader("Content-Type", notMatching("application/json"))
        );*/
    }

}
