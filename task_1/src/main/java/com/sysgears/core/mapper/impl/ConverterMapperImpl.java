package com.sysgears.core.mapper.impl;

import com.sysgears.core.dto.converter.ConverterDto;
import com.sysgears.core.dto.converter.FactorDto;
import com.sysgears.core.dto.io.InputDataDto;
import com.sysgears.core.mapper.Mapper;

public class ConverterMapperImpl implements Mapper<ConverterDto> {

    @Override
    public ConverterDto map(Object[] objects) {

        InputDataDto inputDataDto = (InputDataDto) objects[0];
        FactorDto factorDto = (FactorDto) objects[1];

        return ConverterDto.builder()
                .inputDataDto(inputDataDto)
                .factorDto(factorDto)
                .build();
    }
}
