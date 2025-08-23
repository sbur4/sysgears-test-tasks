package com.sysgears.core.mapper;

import com.sysgears.core.mapper.impl.OutputDtoMapperImpl;
import com.sysgears.core.mapper.impl.PropertiesMapperImpl;
import com.sysgears.core.mapper.impl.QuestionMapperImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MapperFactory {

    private final PropertiesMapperImpl propertiesMapper;
    private final QuestionMapperImpl questionMapper;
    private final OutputDtoMapperImpl outputDtoMapper;
}
