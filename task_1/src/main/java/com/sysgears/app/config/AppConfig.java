package com.sysgears.app.config;

import com.sysgears.app.model.AdditionalRules;
import com.sysgears.app.model.AppConfigModel;
import com.sysgears.app.model.DefaultRules;
import com.sysgears.app.model.PathsToFiles;
import com.sysgears.app.model.UnitsTable;
import com.sysgears.core.dto.config.AdditionalRulesDto;
import com.sysgears.core.dto.config.DefaultRulesDto;
import com.sysgears.core.dto.config.UnitsTableDto;
import com.sysgears.core.extractor.impl.AdditionalConfigExtractorImpl;
import com.sysgears.core.extractor.impl.DefaultConfigExtractorImpl;
import com.sysgears.core.extractor.impl.UnitsTableExtractorImpl;
import com.sysgears.core.io.impl.ConfigJsonIoReaderImpl;
import com.sysgears.core.io.impl.ConfigYmlIoReaderImpl;
import jakarta.json.JsonObject;
import lombok.SneakyThrows;
import lombok.Value;

import java.util.Map;

import static com.sysgears.core.util.AppConfigConstantsUtil.ADDITIONAL_RULES_FILE_NAME;
import static com.sysgears.core.util.AppConfigConstantsUtil.RESOURCE_FILE_NAME;
import static com.sysgears.core.util.AppConfigConstantsUtil.UNITS_FILE_NAME;

@Value
public class AppConfig {

    static AppConfig INSTANCE;
    transient AppConfigModel configMap;

    private AppConfig() {
        this.configMap = loadProperties();
    }

    public static AppConfig getInstance() {
        return INSTANCE == null ? new AppConfig() : INSTANCE;
    }

    @SneakyThrows
    private AppConfigModel loadProperties() {
        Map<String, Object> map = new ConfigYmlIoReaderImpl()
                .readFrom(RESOURCE_FILE_NAME)
                .join();
        DefaultRulesDto defaultRulesDto = new DefaultConfigExtractorImpl().extractData(map);

        JsonObject jsonObjectAdditionalRules = new ConfigJsonIoReaderImpl()
                .readFrom(ADDITIONAL_RULES_FILE_NAME)
                .join();
        AdditionalRulesDto additionalRulesDto = new AdditionalConfigExtractorImpl().extractData(jsonObjectAdditionalRules);

        JsonObject jsonObjectUnits = new ConfigJsonIoReaderImpl()
                .readFrom(UNITS_FILE_NAME)
                .join();
        UnitsTableDto unitsTableDto = new UnitsTableExtractorImpl().extractData(jsonObjectUnits);

        DefaultRules defaultRules = new DefaultRules(defaultRulesDto.getDefaultRules());
        UnitsTable unitsTable = new UnitsTable(unitsTableDto.getUnitToMeter());
        PathsToFiles toFiles = new PathsToFiles(defaultRulesDto.getInputData(), defaultRulesDto.getOutputData());
        AdditionalRules additionalRules = new AdditionalRules(additionalRulesDto.getExtendedRules());
        return new AppConfigModel(defaultRules, unitsTable, toFiles, additionalRules);
    }
}
