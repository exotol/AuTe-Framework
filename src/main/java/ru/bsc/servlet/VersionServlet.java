package ru.bsc.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bsc.servlet.data.Version;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by smakarov
 * 26.02.2018 15:18
 */
public class VersionServlet extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(VersionServlet.class);

    private static final String CONTENT_TYPE = "application/json";
    private static final String VERSION_PROPERTIES = "version.properties";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Version version;

    @Override
    public void init() throws ServletException {
        super.init();
        Properties properties = new Properties();
        try (InputStream resourceAsStream =
                     Thread.currentThread().getContextClassLoader().getResourceAsStream(VERSION_PROPERTIES)) {
            properties.load(resourceAsStream);
            version = new Version(properties.getProperty("buildnumber"), properties.getProperty("builddate"));
            logger.info("Version loaded from file: {}", version);
        } catch (IOException e) {
            version = Version.unknown();
            logger.error("Error while loading version properties", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE);
        objectMapper.writeValue(response.getOutputStream(), version);
    }
}
