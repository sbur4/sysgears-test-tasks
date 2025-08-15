package com.sysgears.app.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.StringJoiner;

@Getter
@RequiredArgsConstructor
public class UnitsTable {

    private final Map<String, Double> unitToMeter;

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"unitToMeter\": " + unitToMeter + "\"")
                .toString();
    }
}
