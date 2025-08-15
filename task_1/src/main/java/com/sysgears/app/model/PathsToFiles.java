package com.sysgears.app.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.StringJoiner;

@Getter
@RequiredArgsConstructor
public class PathsToFiles {

    private final String inputData;
    private final String outputData;

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"inputData\": " + inputData + "\"")
                .add("\"outputData\": " + outputData + "\"")
                .toString();
    }
}
