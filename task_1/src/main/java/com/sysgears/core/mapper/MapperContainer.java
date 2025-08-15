package com.sysgears.core.mapper;

import com.sysgears.core.mapper.impl.ConverterMapperImpl;
import com.sysgears.core.mapper.impl.FactorMapperImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MapperContainer {

    private final FactorMapperImpl factorMapper;
    private final ConverterMapperImpl converterMapper;
}
