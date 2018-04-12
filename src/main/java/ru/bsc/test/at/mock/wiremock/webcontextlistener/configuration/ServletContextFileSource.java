package ru.bsc.test.at.mock.wiremock.webcontextlistener.configuration;

import com.github.tomakehurst.wiremock.common.AbstractFileSource;
import com.github.tomakehurst.wiremock.common.FileSource;

import java.io.File;

/**
 * Created by sdoroshin on 04.08.2017.
 *
 */
class ServletContextFileSource extends AbstractFileSource {

    private final String rootPath;

    ServletContextFileSource(String rootPath) {
        super(new File(rootPath));
        this.rootPath = rootPath;
    }

    @Override
    public FileSource child(String subDirectoryName) {
        return new ServletContextFileSource(rootPath + '/' + subDirectoryName);
    }

    @Override
    protected boolean readOnly() {
        return true;
    }
}

