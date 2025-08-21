package com.sysgears.core.mapper.impl;

import com.sysgears.core.dto.config.ConfigDto;
import com.sysgears.core.exception.CustomIllegalArgException;
import com.sysgears.core.mapper.Mapper;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Nonnull;
import java.util.Map;

@Log4j2
public class PropertiesMapperImpl implements Mapper<ConfigDto, Map<?, ?>> {

    private final String inputProperties = "inputData";
    private final String outputProperties = "outputData";

    @Override
    public ConfigDto mapData(@Nonnull final Map<?, ?> properties) {
        log.info("Starting mapping properties to ConfigDto...");
        log.debug("Received properties: {}", properties);

        try {
            String inputData = (String) properties.get(inputProperties);
            String outputData = (String) properties.get(outputProperties);

            ConfigDto configDto = ConfigDto.builder()
                    .inputData(inputData)
                    .outputData(outputData)
                    .build();

            log.debug("Successfully mapped properties to ConfigDto: {}", configDto);
            return configDto;

        } catch (Exception ex) {
            log.error("Failed to map properties to ConfigDto.", ex);
            throw new CustomIllegalArgException("Error occurred while mapping properties to ConfigDto.", ex);
        }
    }
}
