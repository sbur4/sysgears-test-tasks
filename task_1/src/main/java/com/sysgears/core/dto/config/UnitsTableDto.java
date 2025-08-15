package com.sysgears.core.dto.config;

import lombok.Value;

import java.util.Map;
import java.util.StringJoiner;

@Value
public class UnitsTableDto {

    Map<String, Double> unitToMeter;

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"unitToMeter\": " + unitToMeter)
                .toString();
    }
}
