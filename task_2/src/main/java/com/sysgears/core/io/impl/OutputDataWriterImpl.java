package com.sysgears.core.io.impl;

import com.sysgears.core.exception.IoException;
import com.sysgears.core.io.Writer;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Log4j2
public class OutputDataWriterImpl implements Writer<String> {

    @Override
    public void readDataByPath(@Nonnull String path, String prettyJson) {
        log.info("Writing JSON data to file at path: {}", path);

        try {
            log.debug("Attempting to write JSON data to file: {}", path);
            Files.write(Paths.get(path), prettyJson.getBytes(StandardCharsets.UTF_8));
            log.info("Successfully wrote JSON data to file: {}", path);
        } catch (IOException ex) {
            log.error("Failed to write JSON data to file: {}", path, ex);
            throw new IoException("Error writing JSON data to file: " + path, ex);
        }
    }
}
