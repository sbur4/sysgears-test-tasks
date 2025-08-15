package com.sysgears.core.extractor.impl;

import com.sysgears.core.dto.config.AdditionalRulesDto;
import com.sysgears.core.extractor.Extractor;
import com.sysgears.core.util.CustomStringUtil;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sysgears.core.util.AppConfigConstantsUtil.EXTENDED_RULES;

public class AdditionalConfigExtractorImpl implements Extractor<AdditionalRulesDto, JsonObject> {

    @Override
    public AdditionalRulesDto extractData(final JsonObject jsonObject) {

        String keyName = jsonObject.keySet()
                .stream()
                .findFirst()
                .orElse(EXTENDED_RULES);

        JsonArray extendedRules = jsonObject.getJsonArray(keyName);
        List<String> list = IntStream.range(0, extendedRules.size())
                .mapToObj(extendedRules::getString)
                .filter(StringUtils::isNoneBlank)
                .map(CustomStringUtil::trimSpaces)
                .distinct()
                .collect(Collectors.toList());

        return new AdditionalRulesDto(list);
    }

    @Deprecated
    private String getConfigValue(String stringData) {
        return StringUtils.isNotBlank(stringData) ? stringData : StringUtils.EMPTY;
    }

    @Deprecated
    private Map<String, Object> getConfigMapObject(Map<String, Object> objectMap, String keyName) {

        if (objectMap == null || keyName == null) {
            return Collections.emptyMap();
        }

        Object value = objectMap.get(keyName);

        if (value instanceof Map<?, ?>) {
            return (Map<String, Object>) value;
        }
        return Collections.emptyMap();
    }
}
