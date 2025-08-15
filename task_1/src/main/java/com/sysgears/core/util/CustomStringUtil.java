package com.sysgears.core.util;

import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public final class CustomStringUtil {

    public static String trimSpaces(final String rawData) {
        return rawData.chars()
                .filter(c -> !Character.isWhitespace(c))
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }
}
