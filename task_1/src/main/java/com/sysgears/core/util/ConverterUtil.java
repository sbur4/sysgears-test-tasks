package com.sysgears.core.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@UtilityClass
public class ConverterUtil {

    private static final int HUNDREDTHS_SCALE = 2;
    private static final double ZERO_THRESHOLD = 1e-10;

    public static double roundToHundredths(final double convertedValue) {
        if (isEffectivelyZero(convertedValue)) {
            log.debug("Value is effectively zero: '{}'", convertedValue);
            return 0.0;
        }

        if (!Double.isFinite(convertedValue)) {
            log.warn("Cannot round non-finite value: '{}'", convertedValue);
            return 0.0;
        }

        try {
            BigDecimal valueToBigDecimal = BigDecimal.valueOf(convertedValue)
                    .setScale(HUNDREDTHS_SCALE, RoundingMode.HALF_UP);

            log.debug("Value '{}' rounded to '{}'", convertedValue, valueToBigDecimal);
            return valueToBigDecimal.doubleValue();

        } catch (NumberFormatException nfe) {
            log.warn("Rounding failed for value: '{}'. Error: {}", convertedValue, nfe.getMessage());
            return 0.0;
        }
    }

    private static boolean isEffectivelyZero(double value) {
        return Math.abs(value) < ZERO_THRESHOLD;
    }
}