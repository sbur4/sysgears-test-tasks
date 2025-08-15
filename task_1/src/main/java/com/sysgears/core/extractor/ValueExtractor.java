package com.sysgears.core.extractor;

import com.sysgears.core.exception.ValueExtractorException;

@FunctionalInterface
public interface ValueExtractor<T> {

    T extract(Object value) throws ValueExtractorException;

    default T extractFromArray(Object[] array, int index) throws ValueExtractorException {
        if (array == null) {
            throw new ValueExtractorException("Source array cannot be null");
        }
        if (index < 0 || index >= array.length) {
            throw new ValueExtractorException("Index out of bounds");
        }
        return extract(array[index]);
    }
}
