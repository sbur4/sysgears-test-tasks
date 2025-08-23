package com.sysgears.core.parser.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sysgears.core.dto.InputDataDto;
import com.sysgears.core.exception.ParserException;
import com.sysgears.core.parser.BaseParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Type;
import java.util.List;

@CommonsLog
@RequiredArgsConstructor
public class InputParserImpl implements BaseParser<List<?>, Byte[]> {

    private final Gson gson;

    @Override
    public List<InputDataDto> parse(Byte[] bytes) {
        log.info("Parsing JSON data into list of InputDataDto objects.");

        isValidBytes(bytes);

        log.debug("Converting byte array to string...");
        String jsonContent =  new String(ArrayUtils.toPrimitive(bytes));

        Type listType = new TypeToken<List<InputDataDto>>() {}.getType();

        try {
            log.debug("Parsing JSON content into a list of InputDataDto...");
            List<InputDataDto> questions = gson.fromJson(jsonContent, listType);
            log.info("Successfully parsed JSON content into InputDataDto list. Total items: "
                    + (questions == null ? 0 : questions.size()));
            return questions;
        } catch (JsonSyntaxException ex) {
            log.error("Failed to parse JSON content. Invalid JSON syntax.", ex);
            throw new ParserException("Failed to parse JSON content. Ensure the input JSON is valid.", ex);
        } catch (Exception ex) {
            log.error("Unexpected error while parsing JSON content.", ex);
            throw new ParserException("Unexpected error occurred while parsing JSON data.", ex);
        }
    }

    private static void isValidBytes(Byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            log.error("Input byte array is null or empty.");
            throw new ParserException("Input byte array cannot be null or empty.");
        }
    }
}
