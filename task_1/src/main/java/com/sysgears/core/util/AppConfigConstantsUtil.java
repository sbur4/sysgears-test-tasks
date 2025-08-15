package com.sysgears.core.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface AppConfigConstantsUtil {

    String RESOURCE_FILE_NAME = "application.yml";
    String ADDITIONAL_RULES_FILE_NAME = "additional_rules.json";
    String UNITS_FILE_NAME = "units.json";
    Path RESOURCE_FILE_PATH = Paths.get("/resources/" + RESOURCE_FILE_NAME);
    String APP = "app";
    String CONFIG = "config";
    String DEFAULT_RULES = "defaultRules";
    String PATH_TO = "pathTo";
    String EXTENDED_RULES = "extendedRules";
    String INPUT_DATA = "inputData";
    String OUTPUT_DATA = "outputData";

    String DISTANCE = "distance";
    String UNIT = "unit";
    String VALUE = "value";
    String CONVERT_TO = "convert_to";
}
