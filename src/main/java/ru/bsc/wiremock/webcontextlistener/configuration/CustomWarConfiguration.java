package ru.bsc.wiremock.webcontextlistener.configuration;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.core.MappingsSaver;
import com.github.tomakehurst.wiremock.extension.Extension;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.servlet.WarConfiguration;
import org.apache.velocity.app.Velocity;
import ru.bsc.wiremock.transformers.CustomVelocityResponseTransformer;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by sdoroshin on 04.08.2017.
 *
 */
public class CustomWarConfiguration extends WarConfiguration {

    private String fileSourceRoot;

    public CustomWarConfiguration(ServletContext servletContext, String fileSourceRoot) {
        super(servletContext);
        this.fileSourceRoot = fileSourceRoot;
    }

    @Override
    public <T extends Extension> Map<String, T> extensionsOfType(Class<T> extensionType) {

        if (extensionType.equals(ResponseDefinitionTransformer.class)) {
            Map<String, T> transformers = new HashMap<>();

            // VelocityResponseTransformer configuration
            Properties properties = new Properties();
            try (final InputStream stream = this.getClass().getResourceAsStream("/velocity.properties")) {
                properties.load(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Velocity.init(properties);

            transformers.put("velocity-response-transformer", (T) new CustomVelocityResponseTransformer());
            return transformers;
        }
        return Collections.emptyMap();
    }

    @Override
    public FileSource filesRoot() {
        return new ServletContextFileSource(fileSourceRoot);
    }

    @Override
    public MappingsSaver mappingsSaver() {
        return new BscMappingSaver();
    }
}