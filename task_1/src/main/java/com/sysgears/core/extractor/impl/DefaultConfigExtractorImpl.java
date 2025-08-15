package com.sysgears.core.extractor.impl;

import com.sysgears.core.dto.config.DefaultRulesDto;
import com.sysgears.core.extractor.Extractor;
import com.sysgears.core.util.AppConfigConstantsUtil;
import org.apache.commons.collections4.MapUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sysgears.core.util.AppConfigConstantsUtil.EXTENDED_RULES;
import static com.sysgears.core.util.AppConfigConstantsUtil.INPUT_DATA;
import static com.sysgears.core.util.AppConfigConstantsUtil.OUTPUT_DATA;

public class DefaultConfigExtractorImpl implements Extractor<DefaultRulesDto, Map<String, Object>> {

    @Override
    public DefaultRulesDto extractData(final Map<String, Object> extractedYamlMap) {

        Map<String, Object> extractedAppMap = new HashMap<>();
        Map<String, Object> extractedConfigMap = new HashMap<>();

        if (MapUtils.isNotEmpty(extractedYamlMap)) {
            extractedAppMap = getConfigMapObject(extractedYamlMap, AppConfigConstantsUtil.APP);
        }

        if (MapUtils.isNotEmpty(extractedAppMap)) {
            extractedConfigMap = getConfigMapObject(extractedAppMap, AppConfigConstantsUtil.CONFIG);
        }

        List<String> defaultRules = null;
        String extendedRules = null;
        String inputData = null;
        String outputData = null;

        if (MapUtils.isNotEmpty(extractedConfigMap)) {
            defaultRules = (List<String>) extractedConfigMap.get(AppConfigConstantsUtil.DEFAULT_RULES);
            Map<String, String> map = (Map<String, String>) extractedConfigMap.get(AppConfigConstantsUtil.PATH_TO);
            System.out.println(map);
            extendedRules = map.get(EXTENDED_RULES);
            inputData = map.get(INPUT_DATA);
            outputData = map.get(OUTPUT_DATA);
        }

        return new DefaultRulesDto(defaultRules, extendedRules, inputData, outputData);
    }

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
