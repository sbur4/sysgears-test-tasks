package com.sysgears.core.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.StringJoiner;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Distance {

    String unit;
    Double value;

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"unit\": " + unit)
                .add("\"value\": " + value)
                .toString();
    }
}