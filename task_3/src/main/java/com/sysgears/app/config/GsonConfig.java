package com.sysgears.app.config;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GsonConfig {

    private final Gson gson;
}
