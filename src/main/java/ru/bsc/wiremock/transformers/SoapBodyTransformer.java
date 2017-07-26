package ru.bsc.wiremock.transformers;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

import java.net.HttpURLConnection;

/**
 * Created by sdoroshin on 24.07.2017.
 *
 */
public class SoapBodyTransformer extends ResponseDefinitionTransformer {
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource fileSource, Parameters parameters) {
        if (request.getHeader("testIdHeader") != null) {
            // TODO: Запросить нужный ответ для автотеста и вернуть его
            return new ResponseDefinition(HttpURLConnection.HTTP_OK, "AT custom response. Header: " + request.getHeader("testIdHeader"));
        }

        return responseDefinition;
    }

    public String getName() {
        return "soap-body-transformer";
    }
}
