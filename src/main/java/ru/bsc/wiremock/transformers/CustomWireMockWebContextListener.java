package ru.bsc.wiremock.transformers;

import com.github.tomakehurst.wiremock.common.Notifier;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.core.WireMockApp;
import com.github.tomakehurst.wiremock.extension.Extension;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.AdminRequestHandler;
import com.github.tomakehurst.wiremock.http.StubRequestHandler;
import com.github.tomakehurst.wiremock.servlet.NotImplementedContainer;
import com.github.tomakehurst.wiremock.servlet.WarConfiguration;
import com.github.tomakehurst.wiremock.servlet.WireMockWebContextListener;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.google.common.base.MoreObjects.firstNonNull;

/**
 * Created by sdoroshin on 24.07.2017.
 *
 */
public class CustomWireMockWebContextListener extends WireMockWebContextListener {

    private static final String APP_CONTEXT_KEY = "WireMockApp";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        boolean verboseLoggingEnabled = Boolean.parseBoolean(
                firstNonNull(context.getInitParameter("verboseLoggingEnabled"), "true"));

        WireMockApp wireMockApp = new WireMockApp(new CustomWarConfiguration(context), new NotImplementedContainer());

        context.setAttribute(APP_CONTEXT_KEY, wireMockApp);
        context.setAttribute(StubRequestHandler.class.getName(), wireMockApp.buildStubRequestHandler());
        context.setAttribute(AdminRequestHandler.class.getName(), wireMockApp.buildAdminRequestHandler());
        context.setAttribute(Notifier.KEY, new Slf4jNotifier(verboseLoggingEnabled));
    }

    private class CustomWarConfiguration extends WarConfiguration {

        CustomWarConfiguration(ServletContext servletContext) {
            super(servletContext);
        }

        @Override
        public <T extends Extension> Map<String, T> extensionsOfType(Class<T> extensionType) {

            if (extensionType.equals(ResponseDefinitionTransformer.class)) {
                Map<String, T> transformers = new HashMap<>();
                //transformers.put("soap-body-transformer", (T) new SoapBodyTransformer());
                //transformers.put("response-template", (T) new ResponseTemplateTransformer(true));

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
    }
}
