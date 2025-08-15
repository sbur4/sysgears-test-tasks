package com.sysgears.core.util;

import com.sysgears.core.exception.ValueExtractorException;
import com.sysgears.core.extractor.ValueExtractor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BuilderUtil {

    private final ValueExtractor<String> STRING_EXTRACTOR =
            value -> value != null ? value.toString() : null;

    private final ValueExtractor<Double> DOUBLE_EXTRACTOR =
            value -> {
                if (value instanceof Number) {
                    return ((Number) value).doubleValue();
                }
                throw new ValueExtractorException("Value must be a double");
            };

    private <T> T extract(Object[] objects, int index, ValueExtractor<T> extractor) {
        return extractor.extractFromArray(objects, index);
    }

    public static String extractString(Object[] objects, int index) {
        return extract(objects, index, STRING_EXTRACTOR);
    }

    public static Double extractDouble(Object[] objects, int index) {
        return extract(objects, index, DOUBLE_EXTRACTOR);
    }
}