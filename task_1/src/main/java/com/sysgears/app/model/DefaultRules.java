package com.sysgears.app.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Getter
@RequiredArgsConstructor
public class DefaultRules {

    private final List<String> defaultRules;

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"extendedRules\": " + Objects.toString(defaultRules, "[]"))
                .toString();
    }
}
