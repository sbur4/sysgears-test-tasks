package com.sysgears.core.parser.impl;

import com.sysgears.core.exception.ResourceNotFoundException;
import com.sysgears.core.parser.DataParser;
import com.sysgears.core.util.AppConfigConstantsUtil;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Objects;

@Slf4j
public class JsonParserImpl implements DataParser<JsonObject, InputStream> {

    @Override
    public JsonObject parseData(final InputStream inputStream) {
        log.debug("Attempting to parse JSON data from input stream");

        if (Objects.isNull(inputStream)) {
            String errorMessage = String.format("Resource not found: %s", AppConfigConstantsUtil.RESOURCE_FILE_NAME);
            log.error(errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        }

        try (JsonReader reader = Json.createReader(inputStream)) {
            JsonObject jsonObject = reader.readObject();
            log.debug("Successfully parsed JSON data");

            return jsonObject;
        } catch (JsonException ex) {
            String errorMessage = String.format("Failed to parse JSON data from resource: %s. Error: %s",
                    AppConfigConstantsUtil.RESOURCE_FILE_NAME, ex.getMessage());
            log.error(errorMessage, ex);
            throw ex;
        } catch (Exception ex) {
            String errorMessage = String.format("Unexpected error while parsing JSON from resource: %s",
                    AppConfigConstantsUtil.RESOURCE_FILE_NAME);
            log.error(errorMessage, ex);
            throw new JsonException(errorMessage, ex);
        }
    }
}
