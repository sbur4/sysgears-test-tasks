package com.sysgears.core.dto.config;

import lombok.Value;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Value
public class AdditionalRulesDto {

    List<String> extendedRules;

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"extendedRules\": " + Objects.toString(extendedRules, "[]"))
                .toString();
    }
}
