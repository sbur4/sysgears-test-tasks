package com.sysgears.app.config;

import com.sysgears.core.dto.config.ConfigDto;
import com.sysgears.core.exception.ConfigurationException;
import com.sysgears.core.io.impl.PropertiesReaderImpl;
import com.sysgears.core.mapper.impl.PropertiesMapperImpl;
import com.sysgears.core.parser.impl.PropertiesParserImpl;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
@UtilityClass
public class PathConfig {

    private final String pathToProperties = "application.properties";

    public static ConfigDto initProperties(PropertiesReaderImpl propertiesReader,
                                           PropertiesParserImpl propertiesParser,
                                           PropertiesMapperImpl propertiesMapper) {

        log.info("Initializing properties from file: {}", pathToProperties);

        try {
            Byte[] readData = propertiesReader.readDataByPath(pathToProperties);
            log.debug("Successfully read data from file: {}", pathToProperties);

            Map<?, ?> properties = propertiesParser.parseData(readData);
            log.debug("Successfully parsed data into key-value pairs: {}", properties);

            ConfigDto configDto = propertiesMapper.mapData(properties);
            log.debug("Successfully mapped properties to ConfigDto: {}", configDto);

            log.debug("Successfully loaded configuration: {}", configDto);
            return configDto;
        } catch (Exception ex) {
            log.error("Failed to read configuration from file: {}", pathToProperties, ex);
            throw new ConfigurationException("Failed to load configuration from: " + pathToProperties);
        }
    }
}
