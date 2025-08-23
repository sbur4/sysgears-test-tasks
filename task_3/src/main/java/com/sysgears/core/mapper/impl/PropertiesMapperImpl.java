package com.sysgears.core.mapper.impl;

import com.sysgears.core.dto.ConfigDto;
import com.sysgears.core.mapper.BaseMapper;

import java.util.Properties;

public class PropertiesMapperImpl implements BaseMapper<ConfigDto, Properties> {

    private static final String INPUT_DATA = "inputData";
    private static final String OUTPUT_DATA = "outputData";

    @Override
    public ConfigDto map(Properties properties) {
        String inputData = properties.getProperty(INPUT_DATA);
        String outputData = properties.getProperty(OUTPUT_DATA);

        return new ConfigDto(inputData, outputData);
    }
}
