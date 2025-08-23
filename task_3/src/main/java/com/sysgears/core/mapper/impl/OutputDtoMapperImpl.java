package com.sysgears.core.mapper.impl;

import com.sysgears.core.mapper.BaseMapper;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputDtoMapperImpl implements BaseMapper<Map<String, Object>, List<List<Map<String, String>>>> {

    private static final String PATHS = "paths";
    private static final String NUMBER = "number";
    private static final String LIST = "list";

    @Override
    public Map<String, Object> map(List<List<Map<String, String>>> allChains) {
        if (CollectionUtils.isEmpty(allChains)) {
            return new HashMap<>();
        }

        Map<String, Object> result = new HashMap<>();
        result.put(PATHS, new HashMap<String, Object>() {{
            put(NUMBER, allChains.size());
            put(LIST, allChains);
        }});

        return result;
    }
}
