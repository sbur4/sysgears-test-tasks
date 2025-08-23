package com.sysgears.core.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

@UtilityClass
public class ServiceUtil {

    public static void updateAndMergeOptions(List<List<Map<String, String>>> allChains, String currentQuestion, String answer) {
        BinaryOperator<String> mergeFunction = (oldValue, newValue) -> oldValue + "/" + newValue;

        allChains.stream()
                .flatMap(List::stream)
                .filter(map -> map.containsKey(currentQuestion))
                .forEach(map -> map.merge(currentQuestion, answer, mergeFunction));
    }

    public static boolean isContainsQuestion(List<List<Map<String, String>>> allChains, String currentQuestion) {
        return allChains.stream()
                .flatMap(Collection::stream)
                .anyMatch(q -> q.containsKey(currentQuestion));
    }
}
