package com.sysgears.core.mapper.impl;

import com.sysgears.app.config.AppConfig;
import com.sysgears.core.dto.converter.FactorDto;
import com.sysgears.core.dto.io.InputDataDto;
import com.sysgears.core.mapper.Mapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FactorMapperImpl implements Mapper<FactorDto> {

    private final AppConfig appConfig;

    @Override
    public FactorDto map(Object[] objects) {
        InputDataDto dto = (InputDataDto) objects[0];

        Double fromFactor = appConfig.getConfigMap()
                .getUnitsTable()
                .getUnitToMeter()
                .get(dto.getDistance()
                        .getUnit());

        Double toFactor = appConfig.getConfigMap()
                .getUnitsTable()
                .getUnitToMeter()
                .get(dto.getConvert_to());

        return  FactorDto.builder()
                .fromFactor(fromFactor)
                .toFactor(toFactor)
                .build();
    }
}
