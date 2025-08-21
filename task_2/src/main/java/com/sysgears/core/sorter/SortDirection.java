package com.sysgears.core.sorter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Comparator;

@Getter
@RequiredArgsConstructor
public enum  SortDirection {
    ASC("asc"),
    DESC("desc");

    private final String value;

    public static SortDirection fromString(String value) {
        return Arrays.stream(values())
                .filter(direction -> direction.value.equalsIgnoreCase(value))
                .findFirst()
                .orElse(ASC); // default
    }

    public <T extends Comparable<T>> Comparator<T> getComparator() {
        return this == ASC ? Comparator.naturalOrder() : Comparator.reverseOrder();
    }
}
