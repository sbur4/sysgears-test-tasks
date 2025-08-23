package com.sysgears.core.dto;

import com.sysgears.core.util.ToStringUtil;
import lombok.Value;

import java.util.Map;

@Value
public class OutputDataDto implements ToStringUtil {

    Map<String, Object> result;

    @Override
    public String toString() {
        return toJson();
    }

    @Override
    public String toJson() {
        return ToStringUtil.super.toJson();
    }
}
