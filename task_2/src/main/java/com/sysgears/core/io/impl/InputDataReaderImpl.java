package com.sysgears.core.io.impl;

import com.sysgears.core.exception.IoException;
import com.sysgears.core.io.Reader;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Log4j2
public class InputDataReaderImpl implements Reader<Byte[]> {

    @Override
    public Byte[] readDataByPath(@Nonnull String pathToProperties) {
        log.info("Reading input data from file at path: {}", pathToProperties);

        try {
            log.debug("Reading bytes from file: {}", pathToProperties);
            byte[] inputJsonData = Files.readAllBytes(Paths.get(pathToProperties));

            Byte[] data = ArrayUtils.toObject(inputJsonData);
            log.debug("Successfully read {} bytes from file: {}", data.length, pathToProperties);

            return data;
        } catch (IOException ex) {
            log.error("Failed to read file at path: {}", pathToProperties, ex);
            throw new IoException("Error reading file from path: " + pathToProperties, ex);
        }
    }
}
