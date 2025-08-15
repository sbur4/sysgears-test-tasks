package com.sysgears.core.io.impl;

import com.sysgears.core.builder.impl.ResultBuilderImpl;
import com.sysgears.core.dto.io.OutputDataDto;
import com.sysgears.core.exception.CustomIoException;
import com.sysgears.core.io.IoBase;
import com.sysgears.core.parser.impl.JsonParserImpl;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

@Slf4j
public class IoImpl extends IoBase<JsonObject, String, OutputDataDto> {

    private final ResultBuilderImpl resultBuilder;

    public IoImpl(ResultBuilderImpl resultBuilder) {
        super();
        this.resultBuilder = resultBuilder;
    }

    @Override
    public void doWrite(OutputDataDto value, String to) {
        Objects.requireNonNull(value, "OutputDataDto cannot be null");
        Objects.requireNonNull(to, "Target path cannot be null");

        Path path = Paths.get(to);
        log.debug("Starting to write output to: '{}'", path);

        JsonObject json = resultBuilder.build(value.getUnit(), value.getValue());

        try (OutputStream outStream = Files.newOutputStream(path, CREATE, WRITE, TRUNCATE_EXISTING);
             JsonWriter writer = Json.createWriter(outStream)) {
            writer.writeObject(json);
            log.info("Successfully wrote output to: '{}'", path);
        } catch (IOException ex) {
            String errorMsg = String.format("Failed to write output to: '%s'", path);
            log.error(errorMsg, ex);
            throw new CustomIoException(errorMsg, ex);
        }
    }

    @Override
    public JsonObject doRead(final String from) {
        Objects.requireNonNull(from, "Source path cannot be null");

        Path path = Paths.get(from);
        log.debug("Starting to read input from: '{}'", path);

        try (InputStream inputStream = Files.newInputStream(path)) {
            JsonObject result = new JsonParserImpl().parseData(inputStream);
            log.info("Successfully read input from: '{}'", path);
            return result;
        } catch (IOException ex) {
            String errorMsg = String.format("Failed to read input from: '%s'", path);
            log.error(errorMsg, ex);
            throw new CustomIoException(errorMsg, ex);
        }
    }
}
