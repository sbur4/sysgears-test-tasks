package com.sysgears.core.parser.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysgears.core.exception.ParseException;
import com.sysgears.core.model.Root;
import com.sysgears.core.parser.Parser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class InputJsonParserImpl implements Parser<Root, Byte[]> {

    private final ObjectMapper objectMapper;

    @Override
    public Root parseData(@Nonnull Byte[] inputJsonData) {
        log.info("Parsing JSON data into Root object...");

        try {
            byte[] bytes = ArrayUtils.toPrimitive(inputJsonData);

            Root root = objectMapper.readValue(bytes, Root.class);
            log.debug("Successfully parsed JSON data into Root object: {}", root);
            log.info("JSON parsing completed successfully.");

            return root;
        } catch (IOException ex) {
            log.error("Failed to parse JSON data into Root object.", ex);
            throw new ParseException("Error parsing JSON data into Root object.", ex);
        }
    }
}
