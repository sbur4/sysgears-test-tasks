package com.sysgears.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.StringJoiner;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class AppConfigModel {

    private final DefaultRules baseRules;
    private final UnitsTable unitsTable;
    private final PathsToFiles pathToFile;
    @Setter
    private AdditionalRules additionalRules;

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"baseRules\": " + baseRules.toString() + "\"")
                .add("\"unitsTable\": " + unitsTable.toString() + "\"")
                .add("\"pathToFile\": " + pathToFile.toString() + "\"")
                .add("\"additionalRules\": " + additionalRules.toString() + "\"")
                .toString();
    }
}
