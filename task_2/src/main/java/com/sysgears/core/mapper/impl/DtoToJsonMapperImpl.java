package com.sysgears.core.mapper.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysgears.core.dto.io.OutputDataDto;
import com.sysgears.core.exception.JsonException;
import com.sysgears.core.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Nonnull;

@Log4j2
@RequiredArgsConstructor
public class DtoToJsonMapperImpl implements Mapper<String, OutputDataDto> {

    private final ObjectMapper objectMapper;

    @Override
    public String mapData(@Nonnull final OutputDataDto outputDataDto) {
        log.info("Converting OutputDataDto to JSON...");
        log.debug("Input OutputDataDto: {}", outputDataDto);

        try {
            String outputJson = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(outputDataDto);

            log.debug("Successfully converted OutputDataDto to JSON: {}", outputJson);
            log.info("Conversion to JSON completed successfully.");
            return outputJson;
        } catch (JsonProcessingException e) {
            log.error("Failed to convert OutputDataDto to JSON: {}", e.getMessage(), e);
            throw new JsonException("Error while converting OutputDataDto to JSON", e);
        }
    }
}
