package com.sysgears.core.util;

import com.google.gson.GsonBuilder;

public interface ToStringUtil {

    default String toJson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(this);
    }
}
