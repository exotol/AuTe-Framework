package ru.bsc.wiremock.transformers;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.ToolManager;

import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sdoroshin on 26.07.2017.
 *
 */
public class CustomVelocityResponseTransformer extends ResponseDefinitionTransformer {
    /**
     * The Velocity context that will hold our request header
     * data.
     */
    private Context context;

    @Override
    public ResponseDefinition transform(final Request request,
                                        final ResponseDefinition responseDefinition, final FileSource files,
                                        final Parameters parameters) {
        if (responseDefinition.specifiesBodyFile() && templateDeclared(responseDefinition)) {
            final VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.init();
            final ToolManager toolManager = new ToolManager();
            toolManager.setVelocityEngine(velocityEngine);
            context = toolManager.createContext();
            addBodyToContext(request.getBodyAsString());
            addHeadersToContext(request.getHeaders());
            context.put("requestAbsoluteUrl", request.getAbsoluteUrl());
            context.put("requestUrl", request.getUrl());
            context.put("requestMethod", request.getMethod());
            final String body = getRenderedBody(responseDefinition);
            return ResponseDefinitionBuilder.like(responseDefinition).but()
                    .withBody(body)
                    .build();
        } else {
            return responseDefinition;
        }
    }

    private Boolean templateDeclared(final ResponseDefinition response) {
        Pattern extension = Pattern.compile(".vm$");
        Matcher matcher = extension.matcher(response.getBodyFileName());
        return matcher.find();
    }

    private void addHeadersToContext(final HttpHeaders headers) {
        for (HttpHeader header : headers.all()) {
            final String rawKey = header.key();
            final String transformedKey = rawKey.replaceAll("-", "");
            context.put("requestHeader".concat(transformedKey), header.values()
                    .toString());
        }
    }

    private void addBodyToContext(final String body) {
        if (body != null && !body.isEmpty()) {
            context.put("requestBody", body);
        }
    }

    private String getRenderedBody(final ResponseDefinition response) {
        final Template template = Velocity.getTemplate(response.getBodyFileName());
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return String.valueOf(writer.getBuffer());
    }

    @Override
    public String getName() {
        return "bsc-velocity-transformer";
    }

}
