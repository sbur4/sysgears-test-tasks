package com.sysgears.core.dto.config;

import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Builder
@Value
public class ConfigDto {

    String inputData;
    String outputData;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
