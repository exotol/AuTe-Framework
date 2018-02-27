package ru.bsc.wiremock.webcontextlistener.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import com.github.tomakehurst.wiremock.core.MappingsSaver;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.google.common.io.Files;
import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.bsc.wiremock.Constants.VELOCITY_PROPERTIES;

/**
 * Created by sdoroshin on 04.08.2017.
 *
 */
class BscMappingSaver implements MappingsSaver {
    private final Logger log = LoggerFactory.getLogger(BscMappingSaver.class);

    private final String mappingPath;
    private final SimpleDateFormat dateFormat;

    BscMappingSaver() {
        Properties properties = new Properties();
        try (final InputStream stream = this.getClass().getResourceAsStream(VELOCITY_PROPERTIES.getValue())) {
            properties.load(stream);
        } catch (IOException e) {
            log.error("Error while loading properties", e);
        }
        mappingPath = properties.getProperty("wiremock.mapping.path");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS", Locale.ENGLISH);
    }

    @Override
    public void save(List<StubMapping> stubMappings) {
        File mappingActual = new File(mappingPath + "/mappings/");
        File newName = new File(mappingPath + "/mappings_" + dateFormat.format(Calendar.getInstance().getTime()) + "/");
        boolean isRenamed = mappingActual.renameTo(newName);
        if (isRenamed) {
            log.info("File {} is renamed", mappingActual);
        } else {
            log.warn("File {} not renamed", mappingActual);
        }

        try {
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
            log.error("Error while save subMapping list", e);
        }
        log.info("Save list: {}", stubMappings.toString());
    }

    @Override
    public void save(StubMapping stubMapping) {
        log.info("Save one: {}", stubMapping.toString());
    }

    @Override
    public void remove(StubMapping stubMapping) {
        log.info("Remove: {}", stubMapping.toString());
    }

    @Override
    public void removeAll() {
        log.info("Remove all");
    }
}
