package com.sysgears.app.config;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PathConfig {

    private final String pathToProperties;

    @Setter
    private String pathToInputDto;
    @Setter
    private String pathToOutputDto;

    public PathConfig() {
        this.pathToProperties = "application.properties";
    }
}
