package com.sysgears.core.dto;

import com.sysgears.core.util.ToStringUtil;
import lombok.Value;

@Value
public class ConfigDto implements ToStringUtil {

    String inputData;
    String outputData;

    @Override
    public String toString() {
        return toJson();
    }

    @Override
    public String toJson() {
        return ToStringUtil.super.toJson();
    }
}
