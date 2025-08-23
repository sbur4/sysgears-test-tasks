package com.sysgears.core.io.impl;

import com.sysgears.core.exception.IoReaderException;
import com.sysgears.core.io.BaseReader;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CommonsLog
public class JsonReaderImpl implements BaseReader<Byte[]> {

    @Override
    public Byte[] readDataByPath(String pathToProperties) {
        log.info("Attempting to read JSON data from path: " + pathToProperties);

        String trimmed = StringUtils.trim(pathToProperties);
        isValidPath(trimmed);

        Path path = Paths.get(trimmed);

        isValidFile(path, trimmed);

        try {
            log.debug("Reading file contents...");
            byte[] fileBytes = Files.readAllBytes(path);
            log.info("File successfully read. Size: " + fileBytes.length + " bytes.");
            return ArrayUtils.toObject(fileBytes);
        } catch (IOException ex) {
            log.error("An error occurred while reading the file at path: " + trimmed, ex);
            throw new IoReaderException("Failed to read file at path: " + trimmed, ex);
        } catch (Exception ex) {
            log.error("Unexpected error while processing file at path: " + trimmed, ex);
            throw new IoReaderException("Unexpected error occurred while reading or processing the file.", ex);
        }
    }

    private static void isValidFile(Path path, String trimmed) {
        if (!Files.exists(path)) {
            log.error("File does not exist at path: " + trimmed);
            throw new IoReaderException("File not found at specified path: " + trimmed);
        }

        if (!Files.isReadable(path)) {
            log.error("File is not readable at path: " + trimmed);
            throw new IoReaderException("File is not readable at specified path: " + trimmed);
        }
    }

    private static void isValidPath(String trimmed) {
        if (StringUtils.isBlank(trimmed)) {
            log.error("The file path provided is null or empty.");
            throw new IoReaderException("The file path cannot be null or empty.");
        }
    }
}
