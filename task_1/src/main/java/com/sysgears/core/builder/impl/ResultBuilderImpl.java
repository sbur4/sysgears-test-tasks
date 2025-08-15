package com.sysgears.core.builder.impl;

import com.sysgears.core.builder.Builder;
import com.sysgears.core.util.AppConfigConstantsUtil;
import com.sysgears.core.util.BuilderUtil;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static com.sysgears.core.util.AppConfigConstantsUtil.UNIT;
import static com.sysgears.core.util.AppConfigConstantsUtil.VALUE;

@Slf4j
public class ResultBuilderImpl implements Builder<JsonObject> {

    @Override
    public JsonObject build(Object... objects) {
        log.debug("Starting to build OutputDataDto with args: {}", Arrays.toString(objects));

        String extractedUnit = BuilderUtil.extractString(objects, 0);
        Double extractedValue = BuilderUtil.extractDouble(objects, 1);

        return Json.createObjectBuilder()
                .add(UNIT, extractedUnit)
                .add(VALUE, extractedValue)
                .build();
    }
}
