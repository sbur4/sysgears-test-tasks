package com.sysgears.core.dto.converter;

import com.sysgears.core.dto.io.InputDataDto;
import lombok.Builder;
import lombok.Value;

import java.util.StringJoiner;

@Value
@Builder
public class ConverterDto {

    InputDataDto inputDataDto;
    FactorDto factorDto;

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"inputDataDto\": " + inputDataDto.toString())
                .add("\"factorDto\": " + factorDto.toString())
                .toString();
    }
}
