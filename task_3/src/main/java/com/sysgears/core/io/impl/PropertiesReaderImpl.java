package com.sysgears.core.io.impl;

import com.sysgears.app.Application;
import com.sysgears.core.exception.PropertiesException;
import com.sysgears.core.io.BaseReader;
import lombok.extern.apachecommons.CommonsLog;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@CommonsLog
public class PropertiesReaderImpl implements BaseReader<Properties> {

    @Override
    public Properties readDataByPath(String pathToProperties) {
        log.info("Attempting to read properties file from path: " + pathToProperties);

        URI propertiesPath;
        try {
            log.debug("Resolving properties file URI using the class loader...");
            propertiesPath = Application.class.getClassLoader().getResource(pathToProperties).toURI();
        } catch (URISyntaxException | NullPointerException ex) {
            log.error("Error resolving properties file URI for path: " + pathToProperties, ex);
            throw new PropertiesException("Failed to resolve properties file URI for path: " + pathToProperties, ex);
        }

        Properties appProperties = new Properties();
        try (InputStream fis = Files.newInputStream(Paths.get(propertiesPath))) {
            log.debug("Loading properties file...");
            appProperties.load(fis);
            log.info("Properties file successfully loaded: " + pathToProperties);
        } catch (IOException ex) {
            log.error("Error reading properties file at path: " + propertiesPath, ex);
            throw new PropertiesException("Failed to read properties file at path: " + propertiesPath, ex);
        }

        log.debug("Returning loaded properties...");
        return appProperties;
    }
}
