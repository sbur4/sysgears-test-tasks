package com.sysgears.app.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.StringJoiner;

@Getter
@RequiredArgsConstructor
public class AdditionalRules {

    private final List<String> extendedRules;

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"extendedRules\": [" + String.join(",", extendedRules) + "]")
                .toString();
    }
}
