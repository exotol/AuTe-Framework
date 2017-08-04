package ru.bsc.wiremock.webcontextlistener.configuration;

import com.github.tomakehurst.wiremock.core.MappingsSaver;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sdoroshin on 04.08.2017.
 *
 */
class BscMappingSaver implements MappingsSaver {

    private static Logger logger = Logger.getLogger(BscMappingSaver.class.getName());

    @Override
    public void save(List<StubMapping> stubMappings) {
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
