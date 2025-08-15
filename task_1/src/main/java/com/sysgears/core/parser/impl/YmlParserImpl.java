package com.sysgears.core.parser.impl;

import com.sysgears.core.exception.ResourceNotFoundException;
import com.sysgears.core.parser.DataParser;
import com.sysgears.core.util.AppConfigConstantsUtil;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class YmlParserImpl implements DataParser<Map<String, Object>, InputStream> {

    @Override
    public Map<String, Object> parseData(final InputStream inputStream) {
        log.debug("Attempting to parse YAML data from input stream");

        if (Objects.isNull(inputStream)) {
            String errorMessage = String.format("Resource not found: %s", AppConfigConstantsUtil.RESOURCE_FILE_NAME);
            log.error(errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        }


        try {
            Yaml yaml = new Yaml();
            Map<String, Object> parsedData = yaml.load(inputStream);
            log.debug("Successfully parsed YAML data");

            return parsedData;
        } catch (YAMLException ex) {
            String errorMessage = String.format("Failed to parse YAML data from resource: %s",
                    AppConfigConstantsUtil.RESOURCE_FILE_NAME);
            log.error(errorMessage, ex);
            throw ex;
        }
    }
}
