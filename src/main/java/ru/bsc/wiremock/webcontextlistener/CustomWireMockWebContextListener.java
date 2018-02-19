package ru.bsc.wiremock.webcontextlistener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.github.tomakehurst.wiremock.common.Notifier;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.core.WireMockApp;
import com.github.tomakehurst.wiremock.http.AdminRequestHandler;
import com.github.tomakehurst.wiremock.http.StubRequestHandler;
import com.github.tomakehurst.wiremock.servlet.NotImplementedContainer;
import com.github.tomakehurst.wiremock.servlet.WireMockWebContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bsc.wiremock.webcontextlistener.configuration.CustomWarConfiguration;

import static com.google.common.base.MoreObjects.firstNonNull;
import static ru.bsc.wiremock.Constants.VELOCITY_PROPERTIES;

/**
 * Created by sdoroshin on 24.07.2017.
 *
 */
public class CustomWireMockWebContextListener extends WireMockWebContextListener {
    private final Logger logger = LoggerFactory.getLogger(CustomWireMockWebContextListener.class);

    private static final String APP_CONTEXT_KEY = "WireMockApp";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        boolean verboseLoggingEnabled = Boolean.parseBoolean(
                firstNonNull(context.getInitParameter("verboseLoggingEnabled"), "true"));

        Properties properties = new Properties();
        String wireMockMappingPath = "";
        try (final InputStream stream = this.getClass().getResourceAsStream(VELOCITY_PROPERTIES.getValue())) {
            properties.load(stream);
            wireMockMappingPath = properties.getProperty("wiremock.mapping.path");
        } catch (IOException e) {
            logger.error("Error while loading properties", e);
        }

        WireMockApp wireMockApp = new WireMockApp(new CustomWarConfiguration(context, wireMockMappingPath), new NotImplementedContainer());

        context.setAttribute(APP_CONTEXT_KEY, wireMockApp);
        context.setAttribute(StubRequestHandler.class.getName(), wireMockApp.buildStubRequestHandler());
        context.setAttribute(AdminRequestHandler.class.getName(), wireMockApp.buildAdminRequestHandler());
        context.setAttribute(Notifier.KEY, new Slf4jNotifier(verboseLoggingEnabled));
    }
}

