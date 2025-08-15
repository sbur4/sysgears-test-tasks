package com.sysgears.core.io.impl;

import com.sysgears.core.exception.CustomIoException;
import com.sysgears.core.io.IoBase;
import com.sysgears.core.parser.impl.JsonParserImpl;
import com.sysgears.core.util.IoUtil;
import jakarta.json.JsonObject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
@NoArgsConstructor
public class ConfigJsonIoReaderImpl extends IoBase<JsonObject, String, Object> {

    @Deprecated
    @Override
    protected void doWrite(Object value, String to) {
    }

    @Override
    protected JsonObject doRead(final String from) {
        log.debug("Starting to read JSON configuration from resource: '{}'", from);

        URL resource = ConfigJsonIoReaderImpl.class
                .getClassLoader()
                .getResource(from);

        Optional<Path> p = IoUtil.getPathFromResourceUrl(resource);
        Path path = p.orElseThrow(CustomIoException::new);
        log.debug("Found JSON configuration resource at URL: {}", resource);

        try (InputStream inputStream = Files.newInputStream(path)) {
            JsonObject config = new JsonParserImpl().parseData(inputStream);
            log.debug("Successfully parsed JSON configuration from: '{}'", from);
            return config;
        } catch (IOException ex) {
            String errorMsg = String.format("Failed to read JSON configuration from: '%s'", from);
            log.error(errorMsg, ex);
            throw new CustomIoException(errorMsg, ex);
        }
    }
}
