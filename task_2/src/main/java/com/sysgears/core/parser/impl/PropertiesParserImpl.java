package com.sysgears.core.parser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.sysgears.core.exception.ParseException;
import com.sysgears.core.parser.Parser;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;
import java.util.Map;

@Log4j2
public class PropertiesParserImpl implements Parser<Map<?, ?>, Byte[]> {

    @Override
    public Map<?, ?> parseData(@Nonnull Byte[] value) {
        try {
            JavaPropsMapper mapper = new JavaPropsMapper();

            String content = new String(ArrayUtils.toPrimitive(value));
            Map<?, ?> properties = mapper.readValue(content, Map.class);

            log.debug("Successfully parsed properties: {}", properties);
            log.info("Properties parsing completed successfully.");
            return properties;
        } catch (JsonProcessingException ex) {
            log.error("Failed to parse properties content into a Map.", ex);
            throw new ParseException("Error parsing properties content into a Map.", ex);
        }
    }
}
