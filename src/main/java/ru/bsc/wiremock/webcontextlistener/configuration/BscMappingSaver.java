package ru.bsc.wiremock.webcontextlistener.configuration;

import com.github.tomakehurst.wiremock.core.MappingsSaver;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.google.common.io.Files;
import org.apache.commons.codec.Charsets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by sdoroshin on 04.08.2017.
 *
 */
class BscMappingSaver implements MappingsSaver {

    private String mappingPath = null;
    private String velocityPath = null;

    BscMappingSaver() {
        Properties properties = new Properties();
        try (final InputStream stream = this.getClass().getResourceAsStream("/velocity.properties")) {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mappingPath = properties.getProperty("wiremock.mapping.path");
        velocityPath = properties.getProperty("file.resource.loader.path");
    }

    private static Logger logger = Logger.getLogger(BscMappingSaver.class.getName());

    @Override
    public void save(List<StubMapping> stubMappings) {
        try {
            File mappingActual = new File(mappingPath + "/mappings/");

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");
            mappingActual.renameTo(new File(mappingPath + "/mappings_" + format.format(Calendar.getInstance().getTime()) + "/"));
            for (StubMapping stubMapping: stubMappings) {
                String fileName = "";
                if (stubMapping.getRequest().getUrl() != null) {
                    fileName = stubMapping.getRequest().getUrl().replaceAll("[^\\\\/a-zA-Z0-9.-]", "_");
                } else if (stubMapping.getRequest().getUrlPattern() != null) {
                    fileName = stubMapping.getRequest().getUrlPattern().replaceAll("[^\\\\/a-zA-Z0-9.-]", "_");
                }
                // Replace to: "/mappings/"
                File file = new File(mappingActual, fileName);
                //noinspection ResultOfMethodCallIgnored
                file.mkdirs();
                Files.write(stubMapping.toString(), new File(file, stubMapping.getUuid() + ".json"), Charsets.UTF_8);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Save list: " + stubMappings.toString());
    }

    @Override
    public void save(StubMapping stubMapping) {
        logger.info("Save one: " + stubMapping.toString());
    }

    @Override
    public void remove(StubMapping stubMapping) {
        logger.info("Remove: " + stubMapping.toString());
    }

    @Override
    public void removeAll() {
        logger.info("Remove all");
    }
}
