package com.sysgears.core.io.impl;

import com.google.gson.Gson;
import com.sysgears.core.exception.IoWriterException;
import com.sysgears.core.io.BaseWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@CommonsLog
@RequiredArgsConstructor
public class JsonWriterImpl implements BaseWriter<Map<?, ?>> {

    private final Gson gson;

    @Override
    public void writeDataByPath(Map<?, ?> result, String pathToFile) {
        log.info("Attempting to write JSON data to file at path: " + pathToFile);

        try (FileWriter writer = new FileWriter(pathToFile)) {
            log.debug("Writing data to file...");
            gson.toJson(result, writer);
            log.info("JSON data successfully written to file: " + pathToFile);
        } catch (IOException ex) {
            log.error("An error occurred while writing to the file at path: " + pathToFile, ex);
            throw new IoWriterException("Failed to write data to file: " + pathToFile, ex);
        } catch (Exception ex) {
            log.error("Unexpected error occurred while writing JSON data to file: " + pathToFile, ex);
            throw new IoWriterException("Unexpected error occurred while writing to file.", ex);
        }
    }
}
