package com.sysgears.core.extractor.impl;

import com.sysgears.core.dto.io.InputDataDto;
import com.sysgears.core.extractor.Extractor;
import com.sysgears.core.model.Distance;
import jakarta.json.JsonObject;

import static com.sysgears.core.util.AppConfigConstantsUtil.CONVERT_TO;
import static com.sysgears.core.util.AppConfigConstantsUtil.DISTANCE;
import static com.sysgears.core.util.AppConfigConstantsUtil.UNIT;
import static com.sysgears.core.util.AppConfigConstantsUtil.VALUE;

public class TaskExtractorImpl implements Extractor<InputDataDto, JsonObject> {

    @Override
    public InputDataDto extractData(final JsonObject jsonObject) {

        JsonObject distanceObject = jsonObject.getJsonObject(DISTANCE);
        String unit = distanceObject.getString(UNIT);
        double value = distanceObject.getJsonNumber(VALUE).doubleValue();
        String convertToUnit = jsonObject.getString(CONVERT_TO);

        return new InputDataDto(new Distance(unit, value), convertToUnit);
    }
}
