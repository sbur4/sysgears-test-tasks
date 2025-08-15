package com.sysgears.core.extractor.impl;

import com.sysgears.core.dto.config.UnitsTableDto;
import com.sysgears.core.extractor.Extractor;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;

import java.util.Map;
import java.util.stream.Collectors;

public class UnitsTableExtractorImpl implements Extractor<UnitsTableDto, JsonObject> {

    @Override
    public UnitsTableDto extractData(final JsonObject jsonObject) {

        Map<String, Double> extendedRules = jsonObject.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> ((JsonNumber) entry.getValue()).doubleValue()
                ));

        return new UnitsTableDto(extendedRules);
    }
}
