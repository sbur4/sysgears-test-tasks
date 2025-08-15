package com.sysgears.core.dto.config;

import lombok.Value;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Value
public class DefaultRulesDto {

    List<String> defaultRules;
    String extendedRules;
    String inputData;
    String outputData;

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"defaultRules\": " + Objects.toString(defaultRules, "[]"))
                .add("\"extendedRules\": " + extendedRules)
                .add("\"inputData\": " + inputData)
                .add("\"outputData\": " + outputData)
                .toString();
    }
}
