package com.sysgears.core.mapper;

import com.sysgears.core.mapper.impl.DtoToJsonMapperImpl;
import com.sysgears.core.mapper.impl.OutputDtoMapperImpl;
import com.sysgears.core.mapper.impl.PropertiesMapperImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MapperFactory {

    private final PropertiesMapperImpl propertiesMapper;
    private final OutputDtoMapperImpl outputDtoMapper;
    private final DtoToJsonMapperImpl dtoToJsonMapper;
}
