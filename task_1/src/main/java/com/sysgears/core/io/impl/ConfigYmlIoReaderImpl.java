package com.sysgears.core.io.impl;

import com.sysgears.core.exception.CustomIoException;
import com.sysgears.core.io.IoBase;
import com.sysgears.core.parser.impl.YmlParserImpl;
import com.sysgears.core.util.IoUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

@Slf4j
@NoArgsConstructor
public class ConfigYmlIoReaderImpl extends IoBase<Map<String, Object>, String, Object> {

    @Deprecated
    @Override
    protected void doWrite(Object value, String to) {
    }

    @Override
    protected Map<String, Object> doRead(final String from) {
        log.debug("Attempting to read configuration from resource: {}", from);

        URL resource = ConfigYmlIoReaderImpl.class
                .getClassLoader()
                .getResource(from);

        Optional<Path> p = IoUtil.getPathFromResourceUrl(resource);
        Path path = p.orElseThrow(CustomIoException::new);
        log.debug("Reading configuration from path: {}", path);

        try (InputStream inputStream = Files.newInputStream(path)) {
            Map<String, Object> config = new YmlParserImpl().parseData(inputStream);
            log.debug("Successfully read and parsed configuration from: {}", from);
            return config;
        } catch (IOException ex) {
            String errorMsg = String.format("Failed to read configuration from: %s", from);
            log.error(errorMsg, ex);
            throw new CustomIoException(errorMsg, ex);
        }
    }
}
