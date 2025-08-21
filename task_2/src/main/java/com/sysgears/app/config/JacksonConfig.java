package com.sysgears.app.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class JacksonConfig {

    private final ObjectMapper objectMapper;

    public JacksonConfig() {
        log.debug("Initializing Jackson ObjectMapper...");
        this.objectMapper = new ObjectMapper();

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        log.debug("Pretty-print enabled: {}", SerializationFeature.INDENT_OUTPUT);
        
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        log.debug("Unknown properties will be ignored during deserialization: {}",
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
