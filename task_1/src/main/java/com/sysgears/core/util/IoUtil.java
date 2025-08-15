package com.sysgears.core.util;

import com.sysgears.core.exception.ResourceConversionException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@UtilityClass
public final class IoUtil {

    public static Optional<Path> getPathFromResourceUrl(URL resourceUrl) {
        if (Objects.isNull(resourceUrl)) {
            log.debug("Received null resource URL, returning empty Optional");
            return Optional.empty();
        }

        try {
            Path path = Paths.get(resourceUrl.toURI());
            log.debug("Successfully converted resource URL to path: {}", path);
            return Optional.of(path);
        } catch (URISyntaxException ex) {
            String errorMsg = String.format("Failed to convert resource URL '%s' to Path due to invalid URI syntax",
                    resourceUrl);
            log.error(errorMsg, ex);
            throw new ResourceConversionException(errorMsg, ex);
        }
    }
}
