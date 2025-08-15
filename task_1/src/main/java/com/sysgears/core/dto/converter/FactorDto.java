package com.sysgears.core.dto.converter;

import lombok.Builder;
import lombok.Value;

import java.util.StringJoiner;

@Value
@Builder
public class FactorDto {

    double fromFactor;
    double toFactor;

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"fromFactor\": " + fromFactor)
                .add("\"toFactor\": " + toFactor)
                .toString();
    }
}
