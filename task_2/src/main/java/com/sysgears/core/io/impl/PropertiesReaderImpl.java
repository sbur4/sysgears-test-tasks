package com.sysgears.core.io.impl;

import com.sysgears.app.config.PathConfig;
import com.sysgears.core.exception.IoException;
import com.sysgears.core.io.Reader;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@Log4j2
public class PropertiesReaderImpl implements Reader<Byte[]> {

    @Override
    public Byte[] readDataByPath(@Nonnull String pathToProperties) {
        log.info("Reading data from properties file at path: {}", pathToProperties);

        try {
            URI path = Objects.requireNonNull(PathConfig.class.getClassLoader().getResource(pathToProperties))
                    .toURI();
            byte[] inputData = Files.readAllBytes(Paths.get(path));
            Byte[] data = ArrayUtils.toObject(inputData);
            log.debug("Successfully read {} bytes from the file.", data.length);

            return data;
        } catch (URISyntaxException ex) {
            log.error("Invalid URI syntax for properties file: {}", pathToProperties, ex);
            throw new IoException("Failed to resolve file URI for properties file: " + pathToProperties, ex);
        } catch (IOException ex) {
            log.error("I/O error occurred while reading properties file: {}", pathToProperties, ex);
            throw new IoException("Failed to read properties file: " + pathToProperties, ex);
        } catch (Exception ex) {
            log.error("Unexpected error occurred while reading properties file: {}", pathToProperties, ex);
            throw new IoException("Unexpected error while reading properties file: " + pathToProperties, ex);
        }
    }
}
