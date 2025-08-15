package com.sysgears.core.dto.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.StringJoiner;

@Builder
@Getter
@AllArgsConstructor
public class OutputDataDto {

    private String unit;
    private double value;


    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"unit\": " + unit)
                .add("\"value\": " + value)
                .toString();
    }
}
