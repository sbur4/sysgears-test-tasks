package com.sysgears.core.dto.io;

import com.sysgears.core.model.Distance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.StringJoiner;

@Builder
@Getter
@AllArgsConstructor
public class InputDataDto {

    private Distance distance;
    private String convert_to;

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"distance\": " + distance.toString())
                .add("\"convert_to\": " + convert_to)
                .toString();
    }
}
